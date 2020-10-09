package com.tpw.newday.mapper.localdb;


import com.tpw.newday.bean.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
	public User selectByPrimaryKey(String userId);
}