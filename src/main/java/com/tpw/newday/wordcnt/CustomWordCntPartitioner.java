package com.tpw.newday.wordcnt;

import com.tpw.newday.utils.WordGenerateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class CustomWordCntPartitioner extends Partitioner<Text, IntWritable> {

    private static  final Log log = LogFactory.getLog(CustomWordCntPartitioner.class);

    @Override
    public int getPartition(Text text, IntWritable intWritable, int i) {
        int partionsIndex =  WordGenerateUtils.wordArray.indexOf(text.toString())%i;
        log.info(" text:" + text.toString() + " partionsIndex:" + partionsIndex);
        return  partionsIndex;
    }
}
