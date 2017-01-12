package net.hosain.service

import net.hosain.dao.QuoteDao
import spock.lang.Specification
import spock.lang.Unroll

import static java.time.DayOfWeek.*

class ApiServiceSpec extends Specification {

    private ApiService apiService
    private QuoteDao mockQuoteDao

    void setup() {
        mockQuoteDao = Mock(QuoteDao)
        apiService = new ApiService(mockQuoteDao)
    }

    @Unroll
    void "test api service returns vehicles for correct day #day with quotes #expected"() {
        given:
        mockQuoteDao.findByDayAndMarketing(MONDAY, true) >> ["monday-quote"]
        mockQuoteDao.findByDayAndMarketing(TUESDAY, true) >> ["tuesday-quote"]
        mockQuoteDao.findByDayAndMarketing(WEDNESDAY, true) >> ["wednesday-quote"]
        mockQuoteDao.findByDayAndMarketing(THURSDAY, true) >> ["thursday-quote"]
        mockQuoteDao.findByDayAndMarketing(FRIDAY, true) >> ["friday-quote"]
        mockQuoteDao.findByDayAndMarketing(SATURDAY, true) >> ["saturday-quote"]
        mockQuoteDao.findByDayAndMarketing(SUNDAY, true) >> ["sunday-quote"]


        when:
        List<String> result = apiService.getQuotesForDay(day)

        then:
        result == expected

        where:
        day         | expected
        MONDAY      | ["friday-quote"]
        TUESDAY     | ["monday-quote", "saturday-quote", "sunday-quote"]
        WEDNESDAY   | ["tuesday-quote"]
        THURSDAY    | ["wednesday-quote"]
        FRIDAY      | ["thursday-quote"]
        SATURDAY    | []
        SUNDAY      | []
    }
}