package org.statsserver.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormattedDateMatcher {
    //todo: rename class to reflect working with filenames

    private static Pattern DATE_PATTERN = Pattern.compile(
            "\\d{2}-\\d{2}-\\d{4}");

    private static Pattern DATETIME_PATTERN = Pattern.compile(
            "\\d{2}-\\d{2}-\\d{4}--\\d{2}-\\d{2}-\\d{2}");

    public static String getDateTimeStringFromFileName(String fileName) {

        Matcher dateMatcher = DATE_PATTERN.matcher(fileName);
        Matcher dateTimeMatcher = DATETIME_PATTERN.matcher(fileName);

        if(dateTimeMatcher.find()){
//            System.out.println(dateTimeMatcher.group(0));
            return dateTimeMatcher.group(0);
        }

        if(dateMatcher.find()){
//            System.out.println(dateMatcher.group(0));
            return dateMatcher.group(0)+"--00-00-00";
        }

        //todo: throw exception to inform user that no datetime match has been found in filename
        return "";
    }

}