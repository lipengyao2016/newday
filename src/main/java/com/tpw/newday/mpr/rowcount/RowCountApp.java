package com.tpw.newday.mpr.rowcount;

import com.tpw.newday.config.HBaseConfig;
import com.tpw.newday.mpr.mingxi.MingXiMoneySumApp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.springframework.stereotype.Component;

@Component
public class RowCountApp {

    private static  final Log log = LogFactory.getLog(RowCountApp.class);

    public  int countHbaseTable(String sourceTable,String targetTable) throws Exception {

        log.info(" sourceTable:" + sourceTable + " targetTable:" + targetTable);

        Job job = Job.getInstance(HBaseConfig.getConf(), "RowCount");

        log.info(" init job ok." );

        job.setJarByClass(RowCountApp.class);
        job.setMapperClass(RowCountMapper.class);
        job.setReducerClass(RowCountReducer.class);
//        job.setCombinerClass(RowCountCombin.class);
        job.setNumReduceTasks(1);

        log.info(" set mapreduce class ok." );

        Scan scan = new Scan();
        scan.setCaching(50000);
        scan.setCacheBlocks(false);

        TableMapReduceUtil.initTableMapperJob(sourceTable, scan, RowCountMapper.class, Text.class,LongWritable.class, job);
        log.info(" initTableMapperJob ok." );

        TableMapReduceUtil.initTableReducerJob(targetTable, RowCountReducer.class, job);
        log.info(" initTableReducerJob ok." );

        job.waitForCompletion(true);
        log.info(" finished ok." );
        return  1;
    }
}
