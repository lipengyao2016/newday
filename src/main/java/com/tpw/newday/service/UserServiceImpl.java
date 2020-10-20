package com.tpw.newday.service;

import com.tpw.newday.bean.PhoniexUserMingxi;
import com.tpw.newday.bean.User;
import com.tpw.newday.bean.UserMingxi;
import com.tpw.newday.common.MyConstants;
import com.tpw.newday.dao.UserMingXiDao;
import com.tpw.newday.dao.UserMingXiOriginalDao;
import com.tpw.newday.hdfs_dao.UserMingXiNoSqlDao;
import com.tpw.newday.local_bean.JpaUser;
import com.tpw.newday.local_dao.UserJpaDao;
import com.tpw.newday.mapper.localdb.UserMapper;
import com.tpw.newday.phoniex_dao.UserMingXiPhoniexMapper;
import com.tpw.newday.utils.MyDateUtil;
import com.tpw.newday.utils.RedisUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service("UserService")
public class UserServiceImpl implements IUserService {

    private static final Log logger = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    UserMingXiDao userMingXiDao;

    @Resource
    UserMapper userMapper;

    @Resource
    UserJpaDao userJpaDao;

//    @Resource
//    UserMingXiPhoniexMapper userMingXiPhoniexMapper;

    @Resource
    UserMingXiOriginalDao userMingXiOriginalDao;

    @Resource
    UserMingXiNoSqlDao userMingXiNoSqlDao;

    @Override
    public List<UserMingxi> getUserMingxiByRelate_id(int relate_id) {
      //  return userMingXiDao.getUserMingxiByRelate_id(relate_id);
        return userMingXiOriginalDao.getUserMingxiByRelate_id(relate_id);
    }

    @Override
    public User getUserById(String userId)  {
//        return userMapper.selectByPrimaryKey(userId);
        User user = new User();
        JpaUser u1 = userJpaDao.getUserByUser_id(userId);
        System.out.println(u1);
        try {
            ConvertUtils.register(new DateConverter(null), java.util.Date.class);
            BeanUtils.copyProperties(user,u1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<PhoniexUserMingxi> getUserMingxiByUid(int uid, int offset, int limit) {
//        return userMingXiPhoniexMapper.selectAll(uid,offset,limit);
        return  null;
    }

    @Override
    public boolean syncMysqlDataToHdfs() {
        logger.info(" begin" );

        int offset =0,pageSize = 5000;
        int totalCnt = 0;
        long lCur = System.currentTimeMillis();
        int loopCnt = 0;


        String lastCreateTime = "";
        String cacheKey = "Hdfs_MingXi:lastCreateTime";

        int lastId = 0;
        String lastIdCacheKey = "Hdfs_MingXi:lastId";

        RedisUtil redisUtil = new RedisUtil(MyConstants.redis_host_ip,MyConstants.redis_port,MyConstants.redis_password);
        String val = (String) redisUtil.getByStrKey(cacheKey);
        if (val != null)
        {
            lastCreateTime = val;
        }else{
            lastCreateTime = "2020-07-05 16:33:01";
        }

        String lastIdCacheStr =  redisUtil.getByStrKey(lastIdCacheKey);
        logger.info(" begin lastIdCacheStr:" + lastIdCacheStr);
        if (lastIdCacheStr != null)
        {
            lastId = Integer.parseInt(lastIdCacheStr);
        }

        logger.info(" begin lastId:" + lastId);

        while (true)
        {
//            List<UserMingxi>  userMingxis =  userMingXiDao.selectAll(0,pageSize,lastCreateTime);
            List<UserMingxi>  userMingxis =  userMingXiDao.selectAllById(0,pageSize,lastId);

            if (userMingxis.size() <=0 /*pageSize*/)
            {
                logger.info(" has no data.."  );
                break;
            }

            totalCnt += userMingxis.size();
            offset += pageSize;

            userMingXiNoSqlDao.batchInsert(userMingxis);


            if (userMingxis.size() >0 )
            {
                lastCreateTime = MyDateUtil.formatDate(userMingxis.get(userMingxis.size()-1).getCreate_time(),null);
                lastId = userMingxis.get(userMingxis.size()-1).getId();
            }

            logger.info(" cnt:" + userMingxis.size() + " totalCnt:" + totalCnt
                    +" lastCreateTime:"+lastCreateTime + " lastId:" + lastId);

            redisUtil.set(cacheKey,lastCreateTime,-1);
            redisUtil.set(lastIdCacheKey,String.valueOf(lastId),-1);

            loopCnt++;
             if (loopCnt >= 2)
             {
                 break;
             }

            if (loopCnt%10 ==0)
            {
                logger.info(" per 10 ci.. totalCnt:" + totalCnt + " loopCnt:" + loopCnt + " tm:" + (System.currentTimeMillis()-lCur) );
                lCur = System.currentTimeMillis();
            }

        }

        logger.info(" end.. totalCnt:" + totalCnt  + " loopCnt:" + loopCnt + " tm:" + (System.currentTimeMillis()-lCur) );
        return  true;
    }
}
