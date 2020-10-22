package com.tpw.newday.mpr.rowcount;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.hbase.mapreduce.TableMapper;

import java.io.IOException;

public class RowCountMapper extends TableMapper<Text, LongWritable>{

    private static  final Log log = LogFactory.getLog(RowCountMapper.class);

    public static byte[] COL_NAME = "count".getBytes();
    Text t = new Text();
    LongWritable iWrite = new LongWritable(1);

    @Override
    protected void map(
            ImmutableBytesWritable key,
            Result value,
            Context context) throws IOException, InterruptedException {
        log.info(" uid:"
                + new String(value.getValue(Bytes.toBytes("userInfo"),Bytes.toBytes("uid")))
                + " ,trade_id_former:"
                + new String(value.getValue(Bytes.toBytes("orderInfo"),Bytes.toBytes("trade_id_former")))
        );
        t.set(COL_NAME);
        context.write(t, iWrite);
    }

}
