package com.tpw.newday.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Component
public class JpaPrimaryUtils {

    @Autowired
    @Qualifier("entityManagerFactorySec")
    private EntityManagerFactory factory;

    private  ThreadLocal<EntityManager> tl=new ThreadLocal<EntityManager>();

    /**
     * 获取EntityManager对象
     * @return
     */
    public  EntityManager createEntityManager(){
        //从当前线程上获取EntityManager对象
        EntityManager em=tl.get();
        if(em==null){
            em=factory.createEntityManager();
            tl.set(em);
        }
        return tl.get();
    }
}
