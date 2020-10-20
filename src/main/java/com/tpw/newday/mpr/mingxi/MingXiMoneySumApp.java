package com.tpw.newday.mpr.mingxi;

import com.tpw.newday.common.MyConstants;
import com.tpw.newday.utils.GsonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class MingXiMoneySumApp {

    // 这里为了直观显示参数 使用了硬编码，实际开发中可以通过外部传参
//    private static final String HDFS_URL = "hdfs://hadoop001:8020";
//    private static final String HADOOP_USER_NAME = "root";


//    private static final String HDFS_URL = "hdfs://localhost:9000";
//    private static final String HADOOP_USER_NAME = "Administrator";

    private static  final Log log = LogFactory.getLog(MingXiMoneySumApp.class);

//    public static void main(String[] args) throws Exception {
      public  boolean runSumApp(String inputPath,String outPath) throws Exception {
        //  文件输入路径和输出路径由外部传参指定
        if (StringUtils.isEmpty(inputPath) || StringUtils.isEmpty(outPath)) {
            System.out.println("Input and output paths are necessary!");
            return false;
        }

        log.info(" inputPath:" + inputPath + " outPath:" + outPath);

        // 需要指明 hadoop 用户名，否则在 HDFS 上创建目录时可能会抛出权限不足的异常
        System.setProperty("HADOOP_USER_NAME", MyConstants.hdfs_user);

        Configuration configuration = new Configuration();
        // 指明 HDFS 的地址
        configuration.set("fs.defaultFS", MyConstants.hdfs_url);

        configuration.set("dfs.client.use.datanode.hostname", "true");

        log.info(" init config url:" + MyConstants.hdfs_url + " user:" + MyConstants.hdfs_user);

        // 创建一个 Job
        Job job = Job.getInstance(configuration);

        // 设置运行的主类
        job.setJarByClass(MingXiMoneySumApp.class);
//        job.setJar("/data/work/hadoop/newday-0.0.1-SNAPSHOT.jar");

        log.info(" init job ok 22:" );

        // 设置 Mapper 和 Reducer
        job.setMapperClass(MingXiMoneySumMapper.class);
        job.setReducerClass(MingXiMoneySumReducer.class);

        job.setCombinerClass(MingXiMoneySumReducer.class);
//        job.setPartitionerClass(CustomWordCntPartitioner.class);
//        job.setNumReduceTasks(WordGenerateUtils.wordArray.size());
//        job.setNumReduceTasks(6);

        // 设置 Mapper 输出 key 和 value 的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 设置 Reducer 输出 key 和 value 的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        log.info(" set job ok:" );

        // 如果输出目录已经存在，则必须先删除，否则重复运行程序时会抛出异常
        FileSystem fileSystem = FileSystem.get(new URI(MyConstants.hdfs_url), configuration, MyConstants.hdfs_user);
        // 设置作业输入文件和输出文件的路径
        FileInputFormat.setInputPaths(job, new Path(inputPath));

        log.info(" set file input path ok:" );

        Path outputPath = new Path(outPath);
        if (fileSystem.exists(outputPath)) {
            fileSystem.delete(outputPath, true);
        }
        FileOutputFormat.setOutputPath(job, outputPath);
        log.info(" set file output path ok:" );


        // 将作业提交到群集并等待它完成，参数设置为 true 代表打印显示对应的进度
        boolean result = job.waitForCompletion(true);

        log.info(" finish job result:" + result );

        // 关闭之前创建的 fileSystem
        fileSystem.close();

        log.info(" end"  );

        // 根据作业结果,终止当前运行的 Java 虚拟机,退出程序
//        System.exit(result ? 0 : -1);
          return true;

    }

}