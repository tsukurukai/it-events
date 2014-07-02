package tsukurukai.it_events.model;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static tsukurukai.it_events.util.Util.*;

public class DoorKeeperResponseParser implements ResponseParser {
    @Override
    public List<Event> parse(String responseBody) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonParser jsonParser = new JsonFactory().createParser(responseBody);
        final List<Event> results = new ArrayList<>();
        jsonParser.nextToken();
        while(jsonParser.nextToken() == JsonToken.START_OBJECT) {
            final Map<String, Object> json = mapper.readValue(jsonParser, Map.class);
            final Map<String, Object> eventJson = (Map<String, Object>)json.get("event");
            results.add(
                    new Event(
                            eventJson.get("title").toString(),
                            eventJson.get("public_url").toString(),
                            eventJson.get("starts_at").toString(),
                            eventJson.get("ends_at").toString(),
                            getAsOpt(eventJson, "address", String.class)
                    )
            );
        }
        return results;
    }
}
