package com.moesif.android.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * MoesifAndroid
 *
 *
 */
public class CollectionUtils {

    public static <K, V> Map<K, V> flattenMultiMap(Map<K, List<V>> multiMap) {
        Map<K, V> result = new HashMap<>();
        for (Map.Entry<K, List<V>> entry: multiMap.entrySet()) {
            for (V value : entry.getValue()) {
                result.put(entry.getKey(), value);
            }
        }
        return result;
    }
}
