package com.tpw.newday.wordcnt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 *
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private static  final Log log = LogFactory.getLog(WordCountReducer.class);


    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        for (IntWritable value : values) {
            count += value.get();
        }
        log.info(" key:" + key.toString() + " count:" + count);
        context.write(key, new IntWritable(count));
    }
}
