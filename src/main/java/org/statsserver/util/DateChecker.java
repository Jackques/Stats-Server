package org.statsserver.util;

import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

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
    public static boolean isDateOneBeforeDateTwo(String dateOne, String dateTwo){
        if(!DateChecker.isValidDate(dateOne) || !DateChecker.isValidDate(dateTwo)){
            throw new RuntimeException("Provided date is invalid date");
        }
        return ZonedDateTime.parse(dateOne).isBefore(ZonedDateTime.parse(dateTwo));
    }

    public static boolean isDateOneAfterDateTwo(String dateOne, String dateTwo){
        if(!DateChecker.isValidDate(dateOne) || !DateChecker.isValidDate(dateTwo)){
            throw new RuntimeException("Provided date is invalid date");
        }
        return ZonedDateTime.parse(dateOne).isAfter(ZonedDateTime.parse(dateTwo));
    }
}
