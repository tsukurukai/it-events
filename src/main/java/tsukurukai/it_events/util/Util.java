package tsukurukai.it_events.util;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Util {
    public static <T> Optional<T> getAsOpt(Map<String, Object> map, String key, Class<T> clazz) {
        return map.get(key) == null ? Optional.<T>empty() : Optional.of( (T)map.get(key) );
    }

    public static <T> List<T> getPagenationList(Optional<List<T>> optionalList, int offset, int limit) throws Throwable {
        int startIndex = offset * limit;
        int endIndex = startIndex + limit;

        return getRangeList(optionalList, startIndex, endIndex);
    }

    public static <T> List<T> getRangeList(Optional<List<T>> optionalList, int startIndex, int endIndex) throws Throwable {
        optionalList.orElseThrow(() -> new RuntimeException("value empty!"));
        List<T> list = optionalList.get();
        return list.subList(startIndex, endIndex);
    }
}
