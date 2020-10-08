package com.tpw.newday.service;

import com.tpw.newday.bean.UserMingxi;

import java.util.List;

public interface IUserService {
    public List<UserMingxi> getUserMingxiByRelate_id(int relate_id);
}
