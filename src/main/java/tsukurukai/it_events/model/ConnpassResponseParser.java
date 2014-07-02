package tsukurukai.it_events.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static tsukurukai.it_events.util.Util.getAsOpt;

public class ConnpassResponseParser implements ResponseParser {
    @Override
    public List<Event> parse(String responseBody) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Object> json = mapper.readValue(responseBody, Map.class);
        final List<Map<String, Object>> eventJsons = (List<Map<String, Object>>)json.get("events");
        final List<Event> results =
                eventJsons.stream()
                          .map((eventJson) ->
                              new Event(
                                  eventJson.get("title").toString(),
                                  eventJson.get("event_url").toString(),
                                  eventJson.get("started_at").toString(),
                                  eventJson.get("ended_at").toString(),
                                  getAsOpt(eventJson, "address", String.class)
                              )
                          )
                          .collect(Collectors.toList());
        return results;
    }
}
