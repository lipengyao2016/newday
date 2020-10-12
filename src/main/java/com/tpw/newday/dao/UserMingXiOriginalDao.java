package com.tpw.newday.dao;

import com.tpw.newday.bean.UserMingxi;
import com.tpw.newday.utils.JpaPrimaryUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

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

    public List<Map<String,Object>> getUserMingxiAndMobile(int relate_id)
    {
        EntityManager em = jpaPrimaryUtils.createEntityManager();
        Query query = em.createNativeQuery(
                "select a.relate_id,a.uid,a.shijian,a.money,a.trade_uid,a.trade_id_former,b.ddusername,b.mobile,b.regtime,b.lastlogintime,b.relations,b.role_level from fanli_mingxi_new a,fanli_user b  where a.uid = b.id and a.relate_id ="+relate_id);
       query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List mingXiList = query.getResultList();
        for (Object obj : mingXiList) {
            Map row = (Map) obj;
            System.out.println("relate_id = " + row.get("relate_id"));
            System.out.println("money = " + row.get("money"));
            System.out.println("ddusername = " + row.get("ddusername"));
            System.out.println("mobile = " + row.get("mobile"));
        }
        return  mingXiList;

    }


}
