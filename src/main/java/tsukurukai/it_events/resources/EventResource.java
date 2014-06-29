package tsukurukai.it_events.resources;

import tsukurukai.it_events.model.Event;
import tsukurukai.it_events.model.Site;
import tsukurukai.it_events.representation.EventDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/events")
@Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
public class EventResource {

    @GET
    public List<EventDto> sayHello() {
        List<Event> events = Site.doorKeeper().events();
        return events.stream()
                     .map(ev -> new EventDto(
                                     ev.getTitle(),
                                     ev.getUrl(),
                                     ev.getStart().toString(),
                                     ev.getEnd().toString(),
                                     ev.getAddress().orNull()))
                     .collect(Collectors.toList());
    }
}
