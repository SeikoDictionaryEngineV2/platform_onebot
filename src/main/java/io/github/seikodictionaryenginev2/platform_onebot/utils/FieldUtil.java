package io.github.seikodictionaryenginev2.platform_onebot.utils;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/27 上午10:19
 */
public class FieldUtil {
    public static void assertFieldRightType(Object object,String name, String... expert) {
        Class<?> event = object.getClass();

        do {
            Field f;
            try {
                f = event.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                continue;
            }

            f.setAccessible(true);
            Object rtn;
            try {
                rtn = f.get(object);
                if (Arrays.binarySearch(expert,rtn) != -1) {
                    return;
                }
                throw new IllegalStateException(event.getName() + "must required " + name + ":" + Arrays.toString(expert) + ", but this event is:" + rtn);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } while ((event = event.getSuperclass()) != Object.class);
    }
}
