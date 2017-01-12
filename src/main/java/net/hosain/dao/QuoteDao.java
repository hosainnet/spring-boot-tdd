package net.hosain.dao;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.List;

@Component
public class QuoteDao {
    public List<String> findByDayAndMarketing(DayOfWeek day, boolean marketing) {
        return null;
    }
}
