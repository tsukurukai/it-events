package tsukurukai.it_events.representation;

import lombok.Value;

@Value
public class EventDto {
    private final String title;
    private final String url;
    private final String start;
    private final String end;
    private final String address;
}
