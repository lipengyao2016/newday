package com.tpw.newday.dao;

import com.tpw.newday.bean.UserMingxi;
import com.tpw.newday.utils.JpaPrimaryUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserMingXiOriginalDao {

    private static final Log log = LogFactory.getLog(UserMingXiOriginalDao.class);
    @Autowired
    private JpaPrimaryUtils jpaPrimaryUtils;

    public List<UserMingxi> getUserMingxiByRelate_id(int relate_id)
    {
        EntityManager em = jpaPrimaryUtils.createEntityManager();
        Query query = em.createNativeQuery(
                "select a.* from fanli_mingxi_new a where a.relate_id="+relate_id,UserMingxi.class);
        List mingXiList = query.getResultList();
//        for (Object o:mingXiList) {
//
//        }
        return  mingXiList;

    }

    public List<UserMingxi> getUserMingxiAndMobile(int relate_id)
    {
        EntityManager em = jpaPrimaryUtils.createEntityManager();
        Query query = em.createNativeQuery(
                "select a.* from fanli_mingxi_new a where a.relate_id="+relate_id,UserMingxi.class);
        List mingXiList = query.getResultList();
//        for (Object o:mingXiList) {
//
//        }
        return  mingXiList;

    }
}
