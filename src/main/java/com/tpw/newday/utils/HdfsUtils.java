package com.tpw.newday.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HdfsUtils {
    public static  boolean saveToHdfsFile(String hdfsUrl,String user,String filePath,String fileData)
    {
        FileSystem fileSystem = null;
        FSDataOutputStream fsDataOutputStream = null;
        boolean bWriteOk = false;
        try {
            Configuration conf = new Configuration();
            conf.set("dfs.client.use.datanode.hostname", "true");
            conf.setBoolean("dfs.support.append", true);
            conf.set("dfs.client.block.write.replace-datanode-on-failure.policy" ,"NEVER" );
            conf.set("dfs.client.block.write.replace-datanode-on-failure.enable" ,"true" );

            fileSystem = FileSystem.get(new URI(hdfsUrl),conf,user);
            org.apache.hadoop.fs.Path path = new org.apache.hadoop.fs.Path(filePath);
            System.out.println(" init file system ok.");
            if (fileSystem.exists(path))
            {
                System.out.println(" file exist ,will append");
               // fileSystem.delete(path,true);
                fsDataOutputStream =  fileSystem.append(path);
            }
            else
            {
                System.out.println(" file not exist ,will create");
                fsDataOutputStream = fileSystem.create(path,false);
            }
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
}
