package tsukurukai.it_events.util;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Util {
    public static <T> Optional<T> getAsOpt(Map<String, Object> map, String key, Class<T> clazz) {
        return map.get(key) == null ? Optional.<T>empty() : Optional.of( (T)map.get(key) );
    }

    public static <T> Optional<T> getPagenationList(List<Object> list, int offset, int limit) {
        int startIndex = offset * limit;
        int endIndex = startIndex + limit;
        return getRangeListAsOpt(list, startIndex, endIndex);
    }

    public static <T> Optional<T> getRangeListAsOpt(List<Object> list, int startIndex, int endIndex) {
        return list == null ? Optional.<T>empty() : Optional.of( (T)list.subList(startIndex, endIndex) );
    }
}
