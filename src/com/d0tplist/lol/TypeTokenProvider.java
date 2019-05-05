package com.d0tplist.lol;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TypeTokenProvider {

    public static <T> Type of(Class<T> clazz) {
        return new TypeToken<List<T>>() {
        }.getType();
    }

}
