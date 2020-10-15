package com.tpw.newday.hdfs_dao;

import com.tpw.newday.bean.UserMingxi;

import java.util.List;

public interface UserMingXiNoSqlDao {
    public boolean batchInsert(List<UserMingxi> userMingxiList);
}
