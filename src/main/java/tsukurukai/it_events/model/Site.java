package tsukurukai.it_events.model;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Site {
    public static Site doorKeeper() {
        return new Site("http://api.doorkeeper.jp/events", new DoorKeeperResponseParser());
    }

    private final String url;
    private final ResponseParser responseParser;

    private Site(String url, ResponseParser responseParser) {
        this.url = url;
        this.responseParser = responseParser;
    }

    public List<Event> events() {
        final RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(3000)
                .build();
        final HttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .build();
        List<Event> results = new ArrayList<>();
        try {
            HttpResponse response = client.execute(new HttpGet(url));
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                results = responseParser.parse(EntityUtils.toString(entity, "UTF-8"));
                EntityUtils.consume(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }



    private static interface ResponseParser {
        public List<Event> parse(String responseBody) throws IOException;
    }

    private static class DoorKeeperResponseParser implements ResponseParser {
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

    private static <T> Optional<T> getAsOpt(Map<String, Object> map, String key, Class<T> clazz) {
        return map.get(key) == null ? Optional.<T>absent() : Optional.of( (T)map.get(key) );
    }
}
