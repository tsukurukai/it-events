package tsukurukai.it_events.model;

import lombok.Getter;
import org.joda.time.DateTime;
import tsukurukai.it_events.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class Event {

    public static List<Event> findAll() {
        List<Event> results= new ArrayList<>();
        List<Event> es1 = Site.doorKeeper().events();
        List<Event> es2 = Site.connpass().events();
        results.addAll(es1);
        results.addAll(es2);
        return results;
    }

    private final String title;
    private final String url;
    private final DateTime start;
    private final DateTime end;
    private final Optional<String> address;

    public Event(String title, String url, String start, String end, Optional<String> address) {
        this.title = title;
        this.url = url;
        this.start = new DateTime(start);
        this.end = new DateTime(end);
        this.address = address;
    }

    public static List<Event> getPagenationList(List<Event> list, int offset, int limit) {
        List<Event> resultList = null;

        try {
            resultList = Util.getPagenationList(Optional.of(list), offset, limit);
        } catch (Throwable t) {

        }

        return resultList;
    }
}
