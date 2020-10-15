package com.tpw.newday.utils;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @ClassName: RedisUtil 
 * @Description: RedisUtil.java
SerializableUtil.java
ShiroUtil.java
StringUtils.java
 * @author tianpengw 
 * @date 2017年4月26日 上午10:09:05 
 *
 */
public class RedisUtil {

	private  Logger logger = LogManager.getLogger(RedisUtil.class);

	/** 默认缓存时间 */
	private  final int DEFAULT_CACHE_SECONDS = 60 * 60 * 1;// 单位秒 设置成一个钟

	/** 连接池 **/
	private  JedisPool jedisPool;
	
	 {
	
		
		
	}

    public RedisUtil(String host,int port,String password) {
        if (jedisPool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；  
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。  
            config.setMaxIdle(8);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。  
            config.setMaxTotal(8);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；  
            config.setMaxWaitMillis(1000 * 100);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
            config.setTestOnBorrow(true);
            config.setMinEvictableIdleTimeMillis(60000);
            config.setTimeBetweenEvictionRunsMillis(30000);
            config.setNumTestsPerEvictionRun(-1);
            config.setMinIdle(0);
            jedisPool = new JedisPool(config, host, port,2000,password);
        }
    }

    /**
	 * 释放redis资源
	 * 
	 * @param jedis
	 */
	private  void releaseResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 删除Redis中的所有key
	 * 
	 * @param jedis
	 * @throws Exception
	 */
	public  void flushAll() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.flushAll();
		} catch (Exception e) {
			logger.error("Cache清空失败：" + e);
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 保存一个对象到Redis中(缓存过期时间:使用此工具类中的默认时间) . <br/>
	 * 
	 * @param key
	 *            键 . <br/>
	 * @param object
	 *            缓存对象 . <br/>
	 * @return true or false . <br/>
	 * @throws Exception
	 */
	public  Boolean save(Object key, Object object) {
		return save(key, object, DEFAULT_CACHE_SECONDS);
	}

	/**
	 * 保存一个对象到redis中并指定过期时间
	 * 
	 * @param key
	 *            键 . <br/>
	 * @param object
	 *            缓存对象 . <br/>
	 * @param seconds
	 *            过期时间（单位为秒）.<br/>
	 * @return true or false .
	 */
	public  Boolean save(Object key, Object object, int seconds) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(SerializableUtil.serialize(key), SerializableUtil.serialize(object));
			jedis.expire(SerializableUtil.serialize(key), seconds);
			return true;
		} catch (Exception e) {
			logger.error("Cache保存失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}

	public  Boolean set(String key, String val, int seconds) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, val);
			if (seconds > 0)
			{
				jedis.expire(key, seconds);
			}

			return true;
		} catch (Exception e) {
			logger.error("Cache保存失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 根据缓存键获取Redis缓存中的值.<br/>
	 * 
	 * @param key
	 *            键.<br/>
	 * @return Object .<br/>
	 * @throws Exception
	 */
	public  Object get(Object key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] obj = jedis.get(SerializableUtil.serialize(key));
			return obj == null ? null : SerializableUtil.unSerialize(obj);
		} catch (Exception e) {
			logger.error("Cache获取失败：" + e);
			return null;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 根据缓存键清除Redis缓存中的值.<br/>
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public  Boolean del(Object key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(SerializableUtil.serialize(key));
			return true;
		} catch (Exception e) {
			logger.error("Cache删除失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 根据缓存键清除Redis缓存中的值.<br/>
	 * 
	 * @param keys
	 * @return
	 * @throws Exception
	 */
	public  Boolean del(Object... keys) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(SerializableUtil.serialize(keys));
			return true;
		} catch (Exception e) {
			logger.error("Cache删除失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 
	 * @param key
	 * @param seconds
	 *            超时时间（单位为秒）
	 * @return
	 */
	public  Boolean expire(Object key, int seconds) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.expire(SerializableUtil.serialize(key), seconds);
			return true;
		} catch (Exception e) {
			logger.error("Cache设置超时时间失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 添加一个内容到指定key的hash中
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public  Boolean addHash(String key, Object field, Object value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hset(SerializableUtil.serialize(key), SerializableUtil.serialize(field), SerializableUtil.serialize(value));
			return true;
		} catch (Exception e) {
			logger.error("Cache保存失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 从指定hash中拿一个对象
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public  Object getHash(Object key, Object field) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] obj = jedis.hget(SerializableUtil.serialize(key), SerializableUtil.serialize(field));
			return SerializableUtil.unSerialize(obj);
		} catch (Exception e) {
			logger.error("Cache读取失败：" + e);
			return null;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 从hash中删除指定filed的值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public  Boolean delHash(Object key, Object field) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			long result = jedis.hdel(SerializableUtil.serialize(key), SerializableUtil.serialize(field));
			return result == 1 ? true : false;
		} catch (Exception e) {
			logger.error("Cache删除失败：" + e);
			return null;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 拿到缓存中所有符合pattern的key
	 * 
	 * @param pattern
	 * @return
	 */
	public  Set<byte[]> keys(String pattern) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Set<byte[]> allKey = jedis.keys(("*" + pattern + "*").getBytes());
			return allKey;
		} catch (Exception e) {
			logger.error("Cache获取失败：" + e);
			return new HashSet<byte[]>();
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 获得hash中的所有key value
	 * 
	 * @param key
	 * @return
	 */
	public  Map<byte[], byte[]> getAllHash(Object key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Map<byte[], byte[]> map = jedis.hgetAll(SerializableUtil.serialize(key));
			return map;
		} catch (Exception e) {
			logger.error("Cache获取失败：" + e);
			return null;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 判断一个key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public  Boolean exists(Object key) {
		Jedis jedis = null;
		Boolean result = false;
		try {
			jedis = jedisPool.getResource();
			result = jedis.exists(SerializableUtil.serialize(key));
			return result;
		} catch (Exception e) {
			logger.error("Cache获取失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}
	
	/**
	 * 
	 * @Description: 获得剩余过期时间（单位s)
	 * @author tianpengw 
	 * @param key
	 * @return
	 */
	public  Long getTLL(Object key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if (jedis.exists(SerializableUtil.serialize(key))) {
				return jedis.ttl(SerializableUtil.serialize(key));
			}
				
		} catch (Exception e) {
			logger.error("Cache获取失败：" + e);
		} finally {
			releaseResource(jedis);
		}
		return null;
	}
	


	public  JedisPool getjedisPool() {
        return jedisPool;  
	}
	public  void main(String[] args) {
		save("msg", "100211",60);
		System.out.println(exists("msg"));
		System.out.println(get("msg"));
		System.out.println(jedisPool.getResource().ttl(SerializableUtil.serialize("msg")));
	}
}