package org.statsserver.util;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@Service
public class DateChecker {
    public static boolean isValidDate(String inDate) {
        //todo: prettify this code
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            dateFormat.setLenient(true);
            dateFormat.parse(inDate);
            return true;
        } catch(Exception e){ }

        try {
            TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(inDate);
            Instant i = Instant.from(ta);
            java.util.Date d = java.util.Date.from(i);
            return true;
        } catch (Exception pe) {
            return false;
        }
    }
}
