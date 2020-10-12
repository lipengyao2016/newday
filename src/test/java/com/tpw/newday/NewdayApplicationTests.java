package com.tpw.newday;

import com.tpw.newday.bean.PhoniexUserMingxi;
import com.tpw.newday.bean.UserMingxi;
import com.tpw.newday.dao.UserMingXiOriginalDao;
import com.tpw.newday.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class NewdayApplicationTests {

    @Resource
    private UserServiceImpl userService;

    @Resource
    UserMingXiOriginalDao userMingXiOriginalDao;

	@Test
	void contextLoads() {
	}

    @Test
    void testGetUser() {
        List<UserMingxi> userMingxi = userService.getUserMingxiByRelate_id(198707343);
        System.out.println("end");
    }

    @Test
    void testGetUserMingxiByUid() {
        List<PhoniexUserMingxi> userMingxi = userService.getUserMingxiByUid(10408531,0,5);
        System.out.println("end");
    }

    @Test
    void testgetUserMingxiAndMobile() {
        userMingXiOriginalDao.getUserMingxiAndMobile(198707343);
        System.out.println("end");
    }
}
