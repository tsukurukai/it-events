package tsukurukai.it_events.model;

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

public class Site {
    public static Site doorKeeper() {
        return new Site("http://api.doorkeeper.jp/events", new DoorKeeperResponseParser());
    }

    public static Site connpass() {
        return new Site("http://connpass.com/api/v1/event/", new ConnpassResponseParser());
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
}
