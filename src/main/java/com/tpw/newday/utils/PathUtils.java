package com.tpw.newday.utils;

import java.io.File;

public class PathUtils {
    public static  String getCurPath()
    {
        String realPath = PathUtils.class.getClassLoader().getResource("").getFile();
        File file = new File(realPath);
        String classPath = file.getParentFile().getAbsolutePath();
        return  classPath;
    }
}
