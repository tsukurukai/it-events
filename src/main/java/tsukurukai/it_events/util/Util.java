package tsukurukai.it_events.util;

import com.google.common.base.Optional;

import java.util.Map;

public class Util {
    public static <T> Optional<T> getAsOpt(Map<String, Object> map, String key, Class<T> clazz) {
        return map.get(key) == null ? Optional.<T>absent() : Optional.of( (T)map.get(key) );
    }
}
