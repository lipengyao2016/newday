package com.tpw.newday.wordcnt;

import com.tpw.newday.utils.GsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private static  final Log log = LogFactory.getLog(WordCountMapper.class);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException,
            InterruptedException {
        log.info("xx map value:" + value.toString());
        String[] words = value.toString().split(" ");
        log.info(" words:" + GsonUtils.toJson(words,words.getClass()));
        for (String word : words) {
            context.write(new Text(word), new IntWritable(1));
        }
    }
}
