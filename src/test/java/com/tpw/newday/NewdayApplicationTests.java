package com.tpw.newday;

import com.tpw.newday.bean.UserMingxi;
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

	@Test
	void contextLoads() {
	}

    @Test
    void testGetUser() {
        List<UserMingxi> userMingxi = userService.getUserMingxiByRelate_id(198707343);
	    System.out.println("end");
    }
}