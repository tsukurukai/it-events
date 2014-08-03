package tsukurukai.it_events.util

import spock.lang.Specification

class UtilSpec extends Specification {


    def '指定した範囲のlistを取得する'() {

        setup:
        def list = new ArrayList<Integer>( Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) );

        when:
        Optional optionalResult = Util.getRangeListAsOpt(list, 1, 6);

        then:
        optionalResult.isPresent();
        List<Integer> result = optionalResult.get()
        result.size() == 5
        result.get(0) == 1
        result.get(1) == 2
        result.get(2) == 3
        result.get(3) == 4
        result.get(4) == 5
    }

    def 'offset, limitで指定した範囲のlistを取得する'() {

        setup:
        def list = new ArrayList<Integer>( Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) );

        when:
        Optional optionalResult = Util.getPagenationList(list, 2, 3);

        then:
        optionalResult.isPresent();
        List<Integer> result = optionalResult.get();
        result.size() == 3
        result.get(0) == 6
        result.get(1) == 7
        result.get(2) == 8
    }

}
