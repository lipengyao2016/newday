package com.tpw.newday.dao;

import com.tpw.newday.bean.UserMingxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMingXiDao extends JpaRepository<UserMingxi,Integer> {

//    @Query("select a from UserMingxi a where a.relate_id=?1 ")
    @Query(value = "select a.* from fanli_mingxi_new a where a.relate_id=?1 ",nativeQuery = true)
    public List<UserMingxi> getUserMingxiByRelate_id(int relate_id);

}
