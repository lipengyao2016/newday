package com.tpw.newday.utils;

import com.tpw.newday.NewdayApplication;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.springframework.boot.SpringApplication;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WordGenerateUtils {
    public static final String[] wordList = new String[]{"Spark", "Hadoop", "HBase", "Storm", "Flink", "Hive"};
    private static  final Log log = LogFactory.getLog(WordGenerateUtils.class);

    public static List<String> wordArray = Arrays.asList(wordList);

    public String generateWordStrs(int count)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i < count;i++)
        {
            Random random = new Random();
            int randWordIndex = random.nextInt(wordList.length);
            sb.append(wordList[randWordIndex] + " ");
            if (i%10 == 0 && i != 0)
            {
                sb.append(" \n");
            }
        }
        return  sb.toString();
    }

    public boolean saveToLocalFile(String filePath,String fileData)
    {
        boolean bRet = false;
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path))
            {
                Files.delete(path);
            }
            Files.write(path,fileData.getBytes(), StandardOpenOption.CREATE);
            bRet = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bRet;
    }

    public boolean saveToHdfsFile(String hdfsUrl,String user,String filePath,String fileData)
    {
        FileSystem fileSystem = null;
        FSDataOutputStream fsDataOutputStream = null;
        boolean bWriteOk = false;
        try {
            Configuration conf = new Configuration();
            conf.set("dfs.client.use.datanode.hostname", "true");
            fileSystem = FileSystem.get(new URI(hdfsUrl),conf,user);
            org.apache.hadoop.fs.Path path = new org.apache.hadoop.fs.Path(filePath);
            if (fileSystem.exists(path))
            {
                fileSystem.delete(path,true);
            }
            System.out.println(" init file system ok.");
             fsDataOutputStream = fileSystem.create(path,true);
            fsDataOutputStream.write(fileData.getBytes());
            System.out.println(" write ok.");
            fsDataOutputStream.flush();
            System.out.println(" flush ok.");
            bWriteOk = true;


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }finally {
            try {
                if (fsDataOutputStream != null)
                {
                    fsDataOutputStream.close();
                    System.out.println(" close datastream ok.");
                }
                if (fileSystem != null)
                {
                    fileSystem.close();
                    System.out.println(" close fileSystem ok.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(" end ." + bWriteOk);
        return  bWriteOk;
    }

    public static void main(String[] args) {
       WordGenerateUtils wordGenerateUtils = new WordGenerateUtils();
       int count = 100000;
//       wordGenerateUtils.saveToLocalFile(PathUtils.getCurPath() + "\\word.txt",wordGenerateUtils.generateWordStrs(count));
//        wordGenerateUtils.saveToHdfsFile("hdfs://hadoop001:8020","root","/data/wordCount/word.txt",wordGenerateUtils.generateWordStrs(count));

        wordGenerateUtils.saveToHdfsFile("hdfs://localhost:9000","Administrator","/data/wordCount/word.txt",wordGenerateUtils.generateWordStrs(count));

//        String value = "Hive Hive Hive Flink Flink HBase Hive Hadoop Spark Spark Flink  ";
//        String[] words = value.toString().split(" ");
//        log.info(" words:" + GsonUtils.toJson(words,words.getClass()));

    }

}
