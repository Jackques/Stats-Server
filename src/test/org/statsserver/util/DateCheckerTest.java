package org.statsserver.util;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateCheckerTest {
    @Test
    public void isValidDateISODateFormat(){
        String date = "2021-01-27T14:26:11.339Z";
        assertEquals(DateChecker.isValidDate(date), true);
    }

    @Test
    public void isValidDateDefaultMementoFormat(){
        String date = "13-12-2021 07:21:33";
        assertEquals(DateChecker.isValidDate(date), true);
    }

    @Test
    public void date1ShouldBeBeforeDate2(){
        String date1 = "2021-01-27T14:26:11.339Z";
        String date2 = "2022-01-27T14:26:11.339Z";
        assertEquals(DateChecker.isDateOneBeforeDateTwo(date1, date2), true);
    }
    @Test
    public void date1ShouldBeBeforeDate2_2(){
        String date1 = "2021-02-01T14:26:11.339Z";
        String date2 = "2021-02-02T14:26:11.339Z";
        assertEquals(DateChecker.isDateOneBeforeDateTwo(date1, date2), true);
    }
    @Test
    public void date1ShouldBeBeforeDate2_3(){
        String date1 = "2021-02-01T14:26:11.339Z";
        String date2 = "2021-02-01T14:26:12.339Z";
        assertEquals(DateChecker.isDateOneBeforeDateTwo(date1, date2), true);
    }
    @Test
    public void date1ShouldBeAfterDate2(){
        String date1 = "2022-01-27T14:26:11.339Z";
        String date2 = "2021-01-27T14:26:11.339Z";
        assertEquals(DateChecker.isDateOneBeforeDateTwo(date1, date2), false);
    }

    @Test
    public void test(){
        String date1 = "2020-01-27T14:26:11.339Z"; // accepted date format
        String date2 = "26-02-2023 11:45:38"; // apparantly not accepetd date format

        assertEquals(DateChecker.isDateOneBeforeDateTwo(date1, date2), true);
    }

}