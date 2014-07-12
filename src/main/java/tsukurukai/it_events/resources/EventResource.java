package tsukurukai.it_events.resources;

import tsukurukai.it_events.model.Event;
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
        List<Event> events = Event.findAll();
        events.sort((ev1, ev2) -> ev1.getStart().compareTo(ev2.getStart()));
        return events.stream()
                     .map(ev -> new EventDto(
                                     ev.getTitle(),
                                     ev.getUrl(),
                                     ev.getStart().toString(),
                                     ev.getEnd().toString(),
                                     ev.getAddress().orElse(null)))
                     .collect(Collectors.toList());
    }
}
