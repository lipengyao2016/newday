package com.tpw.newday.mpr.rowcount;

import com.tpw.newday.mpr.mingxi.MingXiMoneySumMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class RowCountReducer extends TableReducer<Text, LongWritable, NullWritable> {
    public static byte[] FAMILY = "f".getBytes();
    public static byte[] COL_COUNT= "count".getBytes();

    private static  final Log log = LogFactory.getLog(RowCountReducer.class);

    @Override
    protected void reduce(
            Text key,
            Iterable<LongWritable> values,
            Context context) throws IOException, InterruptedException {
        long count = 0;
        for (LongWritable intw: values){
            count += intw.get();
        }

        Integer t = new Integer(1);

        log.info(" key:" + key.toString() + " count:"+count);

        Put put = new Put(Bytes.toBytes("RowCount"));
        ObjectMapper mapper = new ObjectMapper();
        String value  = mapper.writeValueAsString( count);
        log.info(" value:" + value);
        put.addColumn(FAMILY, COL_COUNT, Bytes.toBytes(value));
        context.write(NullWritable.get(), put);
    }
}
