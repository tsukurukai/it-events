package tsukurukai.it_events.model;

import com.google.common.base.Optional;
import lombok.Getter;
import lombok.Value;
import org.joda.time.DateTime;

@Getter
public class Event {
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
}
