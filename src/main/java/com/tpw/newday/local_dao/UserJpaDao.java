package com.tpw.newday.local_dao;

import com.tpw.newday.bean.UserMingxi;
import com.tpw.newday.local_bean.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJpaDao extends JpaRepository<JpaUser,Integer> {

//    @Query("select a from UserMingxi a where a.relate_id=?1 ")
    @Query(value = "select a.* from sys_user a where a.user_id=?1 ",nativeQuery = true)
    public JpaUser getUserByUser_id(String user_id);

}
