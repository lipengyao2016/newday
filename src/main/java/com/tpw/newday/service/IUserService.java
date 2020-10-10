package com.tpw.newday.service;

import com.tpw.newday.bean.PhoniexUserMingxi;
import com.tpw.newday.bean.User;
import com.tpw.newday.bean.UserMingxi;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IUserService {
    public List<UserMingxi> getUserMingxiByRelate_id(int relate_id);

    public User getUserById(String userId) ;

    public List<PhoniexUserMingxi> getUserMingxiByUid( int uid,  int offset, int limit);
}
