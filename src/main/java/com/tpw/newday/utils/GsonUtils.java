package com.tpw.newday.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    public static Gson getGson()
    {
        return gson;
    }

    public static String toJson(Object obj,Class cls)
    {
        return  gson.toJson(obj,cls);
    }
}
