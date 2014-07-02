package tsukurukai.it_events.model;

import java.io.IOException;
import java.util.List;

public interface ResponseParser {
    public List<Event> parse(String responseBody) throws IOException;
}
