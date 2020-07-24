package com.example.jpa.utils;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

public class MapsUtill {

    public static <K, V> HashMap newHashMap() {
        return Maps.newHashMap();
    }

    public static <T> Map<String, T> newHashMap(T... var0) {
        Map var1 = newHashMap();

        for (int var2 = 0; var2 < var0.length; var2 += 2) {
            var1.put(var0[var2], var0[var2 + 1]);
        }

        return var1;
    }

}
