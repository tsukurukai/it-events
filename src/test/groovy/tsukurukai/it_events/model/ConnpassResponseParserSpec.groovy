package tsukurukai.it_events.model

import org.joda.time.DateTime
import spock.lang.Specification
import com.google.common.base.Optional

class ConnpassResponseParserSpec extends Specification {
    def 'ConnpassAPIのレスポンスをEventオブジェクトに変換できる'() {
        setup:
        def sut = new ConnpassResponseParser()
        def json =
"""
{
  "results_returned": 2,
  "events": [
    {
      "event_url": "http://tsukurukai.com/event/7/",
      "event_type": "_event_type",
      "owner_nickname": "_owner_nickname",
      "series": {
        "url": "http://tsukurukai.com/series",
        "id": 807,
        "title": "tsukurukai"
      },
      "updated_at": "2014-07-04T05:18:54+09:00",
      "lat": 35.0000000,
      "started_at": "2014-07-19T12:00:00+09:00",
      "hash_tag": "Java",
      "title": "_title",
      "event_id": 7,
      "lon": 138.0000000,
      "waiting": 0,
      "limit": null,
      "owner_id": 2,
      "owner_display_name": "_owner_display_name",
      "description": "<p>Java勉強会です！</p> <p>楽しみましょう！</p>",
      "address": "神奈川県横浜市",
      "catch": "ジャバ！",
      "accepted": 1,
      "ended_at": "2014-07-21T14:00:00+09:00",
      "place": "公民館"
    },
    {
      "event_url": "http://tsukurukai.com/event/12/",
      "event_type": "_event_type2",
      "owner_nickname": "_owner_nickname2",
      "series": {
        "url": "http://tsukurukai.com/series",
        "id": 807,
        "title": "tsukurukai"
      },
      "updated_at": "2014-07-06T05:18:54+09:00",
      "lat": 35.0000000,
      "started_at": "2014-07-20T12:00:00+09:00",
      "hash_tag": "Java",
      "title": "_title2",
      "event_id": 12,
      "lon": 128.0000000,
      "waiting": 0,
      "limit": null,
      "owner_id": 2,
      "owner_display_name": "_owner_display_name",
      "description": "<p>Javaもくもく会</p>",
      "address": "神奈川県川崎市",
      "catch": "Java8",
      "accepted": 1,
      "ended_at": "2014-07-20T14:00:00+09:00",
      "place": "ビル"
    }
  ]
}
"""

        when:
        List<Event> result = sut.parse(json)


        then:
        result.size() == 2
        def ev1 = result.get(0)
        ev1.getTitle()   == "_title"
        ev1.getUrl()     == "http://tsukurukai.com/event/7/"
        ev1.getStart()   == new DateTime(2014,7,19,12,0,0)
        ev1.getEnd()     == new DateTime(2014,7,21,14,0,0)
        ev1.getAddress() == Optional.of("神奈川県横浜市")

        def ev2 = result.get(1)
        ev2.getTitle() == "_title2"
        ev2.getUrl()   == "http://tsukurukai.com/event/12/"
        ev2.getStart() == new DateTime(2014,7,20,12,0,0)
        ev2.getEnd() == new DateTime(2014,7,20,14,0,0)
        ev2.getAddress() == Optional.of("神奈川県川崎市")
    }
}
