package com.tpw.newday.controller;

import com.tpw.newday.bean.PhoniexUserMingxi;
import com.tpw.newday.bean.User;
import com.tpw.newday.bean.UserMingxi;
import com.tpw.newday.mpr.mingxi.MingXiMoneySumApp;
import com.tpw.newday.mpr.rowcount.RowCountApp;
import com.tpw.newday.service.IUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;




@RestController
public class UserMingXiController {

    private static final Log log = LogFactory.getLog(UserMingXiController.class);
    @Resource
    private IUserService userService;

    @Resource
    EntityManagerFactoryBuilder builder;

    @Resource
    MingXiMoneySumApp mingXiMoneySumApp;

    @Resource
    RowCountApp rowCountApp;

    /**
     * 通过ID查询用户
     * @param
     * @return
     */
    @RequestMapping("/getUserMingxiByRelate_id")
    public List<UserMingxi> getUserMingxiByRelate_id(int relate_id){
        log.info(" start");
        return userService.getUserMingxiByRelate_id(relate_id);
    }

    @RequestMapping("/getUserById")
    public User getUserById(String userId) {
        return userService.getUserById(userId);
    }

    @RequestMapping("/getUserMingXiByUid")
    public List<PhoniexUserMingxi> getUserMingXi(int uid) {
        return userService.getUserMingxiByUid(uid,0,10);
    }

    @RequestMapping("/runSumApp")
    public boolean runSumApp(String inputPath,String outPath) throws Exception {
        return mingXiMoneySumApp.runSumApp(inputPath,outPath);
    }

    @RequestMapping("/rowCount")
    public int rowCount(String inputTable,String outTable) throws Exception {
        return rowCountApp.countHbaseTable(inputTable,outTable);
    }
}
