package com.tpw.newday.hdfs_dao;

import com.tpw.newday.bean.UserMingxi;
import com.tpw.newday.common.MyConstants;
import com.tpw.newday.service.UserServiceImpl;
import com.tpw.newday.utils.HdfsUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

@Repository
public class UserMingXiHdfsDao implements UserMingXiNoSqlDao {

    public static final String userMingxiPath = "/data/userMingxi/mingxi.txt";
    private static final Log logger = LogFactory.getLog(UserMingXiHdfsDao.class);

    public String generateWordStrs(List<UserMingxi> userMingxiList)
    {
        StringBuffer sb = new StringBuffer();
        for (UserMingxi userMingxi: userMingxiList) {
                sb.append(userMingxi.toString());
                sb.append(" \n");
                logger.info(userMingxi.toString());
        }

        return  sb.toString();
    }


    @Override
    public boolean batchInsert(List<UserMingxi> userMingxiList) {
        String data = "";
        HdfsUtils.saveToHdfsFile(MyConstants.hdfs_url,MyConstants.hdfs_user,userMingxiPath ,this.generateWordStrs(userMingxiList));
        return false;
    }
}
