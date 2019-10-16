package com.tao.utilslib.list;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Tao on 2018/11/27.
 */

public class MapUtil {

    public enum IteratorType {
        next, ret
    }

    public interface IteratorCall<K, V> {
        IteratorType onIterator(K key, V value);
    }

    public static void main(String[] args) {

        try {
            HashMap<String ,String> map = new HashMap();
            map.put("1" , "1");
            map.put("2" , "2");
            map.put("3" , "3");
            iteratorMap(map, new IteratorCall() {
                @Override
                public IteratorType onIterator(Object key, Object value) {
                    System.err.println( "key "+ key +" value "+ value);
                    return IteratorType.next;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static <K, V> V iteratorMap(Map map, IteratorCall iteratorCall) throws Exception {
        if (map == null)
            return null;
        Iterator <  Map.Entry>iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry next = iterator.next();
            K key = (K) next.getKey();
            V value = (V) next.getValue();

            IteratorType iteratorType = iteratorCall.onIterator(key, value);
            if (null ==iteratorType )
                continue;
            switch (iteratorType) {
                case ret:
                    return value;
                case next:
                    continue;
            }
        }
        return null;
    }

}
