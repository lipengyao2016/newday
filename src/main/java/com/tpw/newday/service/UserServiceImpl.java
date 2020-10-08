package com.tpw.newday.service;

import com.tpw.newday.bean.UserMingxi;
import com.tpw.newday.dao.UserMingXiDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMingXiDao userMingXiDao;

    @Override
    public List<UserMingxi> getUserMingxiByRelate_id(int relate_id) {
        return userMingXiDao.getUserMingxiByRelate_id(relate_id);
    }
}
