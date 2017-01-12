package net.hosain.service;

import net.hosain.dao.QuoteDao;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static java.time.DayOfWeek.*;

@Service
public class ApiService {

    private QuoteDao quoteDao;

    public ApiService(QuoteDao quoteDao) {
        this.quoteDao = quoteDao;
    }

    public String getAllVehicles() {
        return "real";
    }

    public List<String> getQuotesForDay(DayOfWeek day) {
        ArrayList<String> quotes = new ArrayList<>();
        switch (day) {
            case MONDAY :
                quotes.addAll(quoteDao.findByDayAndMarketing(FRIDAY, true));
                break;
            case TUESDAY :
                quotes.addAll(quoteDao.findByDayAndMarketing(MONDAY, true));
                quotes.addAll(quoteDao.findByDayAndMarketing(SATURDAY, true));
                quotes.addAll(quoteDao.findByDayAndMarketing(SUNDAY, true));
                break;
            case WEDNESDAY:
                quotes.addAll(quoteDao.findByDayAndMarketing(TUESDAY, true));
                break;
            case THURSDAY:
                quotes.addAll(quoteDao.findByDayAndMarketing(WEDNESDAY, true));
                break;
            case FRIDAY:
                quotes.addAll(quoteDao.findByDayAndMarketing(THURSDAY, true));
                break;
        }
        return quotes;
    }
}
