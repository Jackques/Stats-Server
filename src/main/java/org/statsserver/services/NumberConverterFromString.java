package org.statsserver.services;

import org.springframework.util.NumberUtils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Pattern;

public class NumberConverterFromString {
    private static final Locale myLocaleDutch = new Locale("nl", "NL");

    // Regular expression pattern to test input
//    private static final String regex = "\"[^,.\\d]\"gx"; // old regex, whilst i don't necessarily need the global pattern flags i do think it's weird this regex does not work. Might want to investigate further
    private static final String regex = "[^,.\\d]";
    private static final Pattern pattern = Pattern.compile(regex);


    public static Object getConvertedNumberFromString(String value) {

        if (value == null || value.isEmpty()) {
            return null;
        }

        String trimmedValue = value.trim();
        if(isInvalidNumberString(trimmedValue)){
            return trimmedValue;
        }

        Long convertedLongValue = getStringLongValue(trimmedValue);
        if (convertedLongValue != null) {
            return convertedLongValue;
        }

        Double convertedDoubleValue = getStringDoubleValue(trimmedValue);
        if (convertedDoubleValue != null) {
            return convertedDoubleValue;
        } else {
            return trimmedValue;
        }
    }

    private static Long getStringLongValue(String value) {
        try {
            Long parsedLongValue = NumberUtils.parseNumber(value, Long.class);
//            System.out.println("Received string rep. parsedLongValue converted to type: " + parsedLongValue.getClass());
//            System.out.println("Received string rep. parsedLongValue: " + parsedLongValue);
            return parsedLongValue;
        } catch (Exception e) {
            // might be a whole number (long/int) or an actual string
            return null;
        }
    }

    private static Double getStringDoubleValue(String value) {
        try {
            Number parsedNumberValue = NumberFormat.getInstance(myLocaleDutch).parse(value);
            Double parsedDoubleValue = parsedNumberValue.doubleValue();
            return parsedDoubleValue;
        } catch (ParseException e) {
            // might be an
            return null;
        }
    }

    private static boolean isInvalidNumberString(String value){
        // if pattern matcher finds any non-allowed characters except commas or dots
        Boolean result = pattern.matcher(value).find();
        return result;
    }
}
