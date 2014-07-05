package tsukurukai.it_events.model

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import spock.lang.Specification
import com.google.common.base.Optional

class DoorKeeperResponseParserSpec extends Specification {

    DoorKeeperResponseParser sut

    def 'DoorKeeperAPIのレスポンスをEventオブジェクトに変換できる'() {
        setup:
        sut = new DoorKeeperResponseParser()
        def json =
                """
[
  {
    "event": {
      "title": "いぬ勉強会",
      "id": 8,
      "starts_at": "2014-07-29T12:00:00.000Z",
      "ends_at": "2014-07-29T14:00:00.000Z",
      "venue_name": null,
      "address": "神奈川県横浜市",
      "lat": null,
      "long": null,
      "ticket_limit": 10,
      "published_at": "2014-07-03T12:27:19.860Z",
      "updated_at": "2014-07-04T01:57:04.892Z",
      "banner": "https://banners/ham.png",
      "description": "<p>いぬを可愛がる会</p>",
      "public_url": "http://tsukurukai.example.com/events/8",
      "participants": 5,
      "waitlisted": 0,
      "group": {
        "id": 2,
        "name": "tsukurukai",
        "country_code": "JP",
        "logo": "https://logos/tsukurukai.png",
        "description": "<p>hogehoge</p>",
        "public_url": "http://tsukurukai.group.example.com/"
      }
    }
  },
  {
    "event": {
      "title": "ハムスター勉強会",
      "id": 10,
      "starts_at": "2014-07-31T10:00:00.000Z",
      "ends_at": "2014-07-31T12:00:00.000Z",
      "venue_name": null,
      "address": null,
      "lat": null,
      "long": null,
      "ticket_limit": 20,
      "published_at": "2014-07-04T12:27:19.860Z",
      "updated_at": "2014-07-05T01:57:04.892Z",
      "banner": "https://banners/ham.png",
      "description": "<p>ハムスターを可愛がる会</p>",
      "public_url": "http://tsukurukai.example.com/events/10",
      "participants": 10,
      "waitlisted": 0,
      "group": {
        "id": 2,
        "name": "tsukurukai",
        "country_code": "JP",
        "logo": "https://logos/tsukurukai.png",
        "description": "<p>hogehoge</p>",
        "public_url": "http://tsukurukai.group.example.com/"
      }
    }
  }
]
"""

        when:
        List<Event> result = sut.parse(json)

        then:
        result.size() == 2
        def ev1 = result.get(0)
        ev1.getTitle()   == "いぬ勉強会"
        ev1.getUrl()     == "http://tsukurukai.example.com/events/8"
        ev1.getStart()   == new DateTime(2014,7,29,21,0,0,DateTimeZone.forID("Asia/Tokyo"))
        ev1.getEnd()     == new DateTime(2014,7,29,23,0,0,DateTimeZone.forID("Asia/Tokyo"))
        ev1.getAddress() == Optional.of("神奈川県横浜市")

        def ev2 = result.get(1)
        ev2.getTitle()   == "ハムスター勉強会"
        ev2.getUrl()     == "http://tsukurukai.example.com/events/10"
        ev2.getStart()   == new DateTime(2014,7,31,19,0,0,DateTimeZone.forID("Asia/Tokyo"))
        ev2.getEnd()     == new DateTime(2014,7,31,21,0,0,DateTimeZone.forID("Asia/Tokyo"))
        ev2.getAddress() == Optional.absent()
    }

}
