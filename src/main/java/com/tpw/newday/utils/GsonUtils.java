package com.tpw.newday.utils;

import com.google.gson.Gson;

public class GsonUtils {
    private static final Gson gson = new Gson();

    public static Gson getGson()
    {
        return gson;
    }

    public static String toJson(Object obj,Class cls)
    {
        return  gson.toJson(obj,cls);
    }
}
