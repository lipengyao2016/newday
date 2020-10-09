package com.tpw.newday.service;

import com.tpw.newday.bean.User;
import com.tpw.newday.bean.UserMingxi;
import com.tpw.newday.dao.UserMingXiDao;
import com.tpw.newday.local_bean.JpaUser;
import com.tpw.newday.local_dao.UserJpaDao;
import com.tpw.newday.mapper.localdb.UserMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service("UserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMingXiDao userMingXiDao;

    @Resource
    UserMapper userMapper;

    @Resource
    UserJpaDao userJpaDao;

    @Override
    public List<UserMingxi> getUserMingxiByRelate_id(int relate_id) {
        return userMingXiDao.getUserMingxiByRelate_id(relate_id);
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
}
