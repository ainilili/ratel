package org.nico.ratel.landlords.utils;

import com.google.gson.Gson;

public class JsonUtils {

    private static final Gson GSON = new Gson();

    public static String toJson(Object o){
        return GSON.toJson(o);
    }

    public static <T> T fromJson(String json, Class<T> clazz){
        return GSON.fromJson(json, clazz);
    }

}
