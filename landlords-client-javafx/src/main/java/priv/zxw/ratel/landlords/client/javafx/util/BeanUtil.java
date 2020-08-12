package priv.zxw.ratel.landlords.client.javafx.util;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanUtil {
    private static final Map<String, Object> CACHE_MAP = new ConcurrentHashMap<>();

    public static void addBean(String name, Object object) {
        CACHE_MAP.put(name, object);
    }

    public static <T> T getBean(String name) {
        return (T) CACHE_MAP.get(name);
    }
}
