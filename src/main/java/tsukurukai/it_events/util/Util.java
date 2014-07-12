package tsukurukai.it_events.util;

import java.util.Map;
import java.util.Optional;

public class Util {
    public static <T> Optional<T> getAsOpt(Map<String, Object> map, String key, Class<T> clazz) {
        return map.get(key) == null ? Optional.<T>empty() : Optional.of( (T)map.get(key) );
    }
}
