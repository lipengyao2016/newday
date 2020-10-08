package com.tpw.newday.controller;

import com.tpw.newday.bean.UserMingxi;
import com.tpw.newday.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserMingXiController {


    @Resource
    private IUserService userService;

    /**
     * 通过ID查询用户
     * @param
     * @return
     */
    @RequestMapping("/getUserMingxiByRelate_id")
    public List<UserMingxi> getUserMingxiByRelate_id(int relate_id){
        return userService.getUserMingxiByRelate_id(relate_id);
    }
}
