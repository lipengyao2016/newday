package com.tpw.newday.mpr.mingxi;

import com.tpw.newday.bean.UserMingxi;
import com.tpw.newday.utils.GsonUtils;
import com.tpw.newday.wordcnt.WordCountMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MingXiMoneySumMapper  extends Mapper<LongWritable, Text, Text, IntWritable> {

    private static  final Log log = LogFactory.getLog(MingXiMoneySumMapper.class);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException,
            InterruptedException {
        log.info("xx map value:" + value.toString());
        String jsonVal = value.toString();
        if (StringUtils.isNotEmpty(jsonVal))
        {
            UserMingxi userMingxi = GsonUtils.getGson().fromJson(value.toString(),UserMingxi.class);
            context.write(new Text(userMingxi.getUid()+""), new IntWritable(userMingxi.getMoney()));
        }

    }
}