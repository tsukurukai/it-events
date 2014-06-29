package tsukurukai.it_events;

import io.dropwizard.Application;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import tsukurukai.it_events.configuration.ItEventsConfiguration;
import tsukurukai.it_events.resources.EventResource;
import tsukurukai.it_events.resources.HelloWorldResource;

public class ItEventsApplication extends Application<ItEventsConfiguration> {
    public static void main(String[] args) throws Exception {
        new ItEventsApplication().run(args);
    }

    @Override
    public String getName() {
        return "it-events";
    }

    @Override
    public void initialize(Bootstrap<ItEventsConfiguration> bootstrap) {

    }

    @Override
    public void run(ItEventsConfiguration configuration, Environment environment) throws Exception {
        final String template = configuration.getTemplate();
        final String defaultName = configuration.getDefaultName();

        JerseyEnvironment jersey = environment.jersey();
        jersey.register(new HelloWorldResource(template, defaultName));
        jersey.register(new EventResource());
    }
}
