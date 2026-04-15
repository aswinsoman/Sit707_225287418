package sit707_tasks;


import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest {

    @Test
    public void testStudentIdentity() {
        String studentId = "s225287418";
        Assert.assertNotNull("Student ID is null", studentId);
    }

    @Test
    public void testStudentName() {
        String studentName = "Aswin Soman";
        Assert.assertNotNull("Student name is null", studentName);
    }

    // increment() tests

    // D1 + M1 + Y2: day in 1-28, 31-day month — next day is day+1, same month
    @Test
    public void testIncrementD1M1_NormalDay() {
        DateUtil date = new DateUtil(15, 1, 2023);
        date.increment();
        Assert.assertEquals(new DateUtil(16, 1, 2023), date);
    }

    // D1 + M2 + Y2: day in 1-28, 30-day month
    @Test
    public void testIncrementD1M2_NormalDay() {
        DateUtil date = new DateUtil(10, 4, 2023);
        date.increment();
        Assert.assertEquals(new DateUtil(11, 4, 2023), date);
    }

    // D1 + M3 + Y2: day in 1-28, February non-leap year
    @Test
    public void testIncrementD1M3_FebruaryNonLeap() {
        DateUtil date = new DateUtil(14, 2, 2023);
        date.increment();
        Assert.assertEquals(new DateUtil(15, 2, 2023), date);
    }

    // D2 + M1 + Y2: day=29, 31-day month — next day is 30
    @Test
    public void testIncrementD2M1_Day29In31DayMonth() {
        DateUtil date = new DateUtil(29, 1, 2023);
        date.increment();
        Assert.assertEquals(new DateUtil(30, 1, 2023), date);
    }

    // D2 + M2 + Y2: day=29, 30-day month — next day is 30
    @Test
    public void testIncrementD2M2_Day29In30DayMonth() {
        DateUtil date = new DateUtil(29, 4, 2023);
        date.increment();
        Assert.assertEquals(new DateUtil(30, 4, 2023), date);
    }

    // D2 + M3 + Y1: day=29, February in leap year — next day is March 1
    @Test
    public void testIncrementD2M3_Feb29LeapYear() {
        DateUtil date = new DateUtil(29, 2, 2024);
        date.increment();
        Assert.assertEquals(new DateUtil(1, 3, 2024), date);
    }

    // D2 + M3 + Y2: day=29 Feb in non-leap year is invalid — test day=28, last valid Feb day
    @Test
    public void testIncrementD1M3_LastDayFebNonLeap() {
        DateUtil date = new DateUtil(28, 2, 2023);
        date.increment();
        Assert.assertEquals(new DateUtil(1, 3, 2023), date);
    }

    // D3 + M1 + Y2: day=30, 31-day month — next day is 31
    @Test
    public void testIncrementD3M1_Day30In31DayMonth() {
        DateUtil date = new DateUtil(30, 1, 2023);
        date.increment();
        Assert.assertEquals(new DateUtil(31, 1, 2023), date);
    }

    // D3 + M2 + Y2: day=30, 30-day month — rolls over to 1st of next month
    @Test
    public void testIncrementD3M2_LastDayOf30DayMonth() {
        DateUtil date = new DateUtil(30, 4, 2023);
        date.increment();
        Assert.assertEquals(new DateUtil(1, 5, 2023), date);
    }

    // D4 + M1 + Y2: day=31, 31-day month, not December — rolls to 1st next month
    @Test
    public void testIncrementD4M1_LastDayOfJanuary() {
        DateUtil date = new DateUtil(31, 1, 2023);
        date.increment();
        Assert.assertEquals(new DateUtil(1, 2, 2023), date);
    }

    // D4 + M1 + Y2: day=31, December — rolls to Jan 1 next year
    @Test
    public void testIncrementD4M1_LastDayOfDecember() {
        DateUtil date = new DateUtil(31, 12, 2023);
        date.increment();
        Assert.assertEquals(new DateUtil(1, 1, 2024), date);
    }

    // D1 + M3 + Y1: day in 1-28, February in leap year
    @Test
    public void testIncrementD1M3_FebruaryLeapYear() {
        DateUtil date = new DateUtil(14, 2, 2024);
        date.increment();
        Assert.assertEquals(new DateUtil(15, 2, 2024), date);
    }
    
    // decrement() tests

    // D1 + M1 + Y2: day in 2-28, 31-day month — previous day is day-1
    @Test
    public void testDecrementD1M1_NormalDay() {
        DateUtil date = new DateUtil(15, 3, 2023);
        date.decrement();
        Assert.assertEquals(new DateUtil(14, 3, 2023), date);
    }

    // D1 + M2 + Y2: day=1, 30-day month — rolls back to last day of previous month
    @Test
    public void testDecrementD1M2_FirstDayOf30DayMonth() {
        DateUtil date = new DateUtil(1, 4, 2023);
        date.decrement();
        Assert.assertEquals(new DateUtil(31, 3, 2023), date);
    }

    // D1 + M3 + Y2: day=1, February non-leap — rolls back to Jan 31
    @Test
    public void testDecrementD1M3_FirstDayOfFebNonLeap() {
        DateUtil date = new DateUtil(1, 2, 2023);
        date.decrement();
        Assert.assertEquals(new DateUtil(31, 1, 2023), date);
    }

    // D1 + M1 + Y2: day=1, January — rolls back to Dec 31 of previous year
    @Test
    public void testDecrementD1M1_FirstDayOfJanuary() {
        DateUtil date = new DateUtil(1, 1, 2023);
        date.decrement();
        Assert.assertEquals(new DateUtil(31, 12, 2022), date);
    }

    // D2 + M1 + Y2: day=29, 31-day month — previous day is 28
    @Test
    public void testDecrementD2M1_Day29In31DayMonth() {
        DateUtil date = new DateUtil(29, 3, 2023);
        date.decrement();
        Assert.assertEquals(new DateUtil(28, 3, 2023), date);
    }

    // D2 + M3 + Y1: day=29, February leap year — previous day is 28
    @Test
    public void testDecrementD2M3_Feb29LeapYear() {
        DateUtil date = new DateUtil(29, 2, 2024);
        date.decrement();
        Assert.assertEquals(new DateUtil(28, 2, 2024), date);
    }

    // D3 + M2 + Y2: day=30, 30-day month — previous day is 29
    @Test
    public void testDecrementD3M2_Day30In30DayMonth() {
        DateUtil date = new DateUtil(30, 6, 2023);
        date.decrement();
        Assert.assertEquals(new DateUtil(29, 6, 2023), date);
    }

    // D4 + M1 + Y2: day=31, 31-day month — previous day is 30
    @Test
    public void testDecrementD4M1_Day31In31DayMonth() {
        DateUtil date = new DateUtil(31, 8, 2023);
        date.decrement();
        Assert.assertEquals(new DateUtil(30, 8, 2023), date);
    }

    // D3 + M1 + Y2: day=30, March — previous day is 29
    @Test
    public void testDecrementD3M1_Day30InMarch() {
        DateUtil date = new DateUtil(30, 3, 2023);
        date.decrement();
        Assert.assertEquals(new DateUtil(29, 3, 2023), date);
    }

    // D1 + M3 + Y1: day=1, March, leap year — rolls back to Feb 29
    @Test
    public void testDecrementD1M1_FirstDayMarchLeapYear() {
        DateUtil date = new DateUtil(1, 3, 2024);
        date.decrement();
        Assert.assertEquals(new DateUtil(29, 2, 2024), date);
    }

    // D1 + M3 + Y2: day=1, March, non-leap year — rolls back to Feb 28
    @Test
    public void testDecrementD1M1_FirstDayMarchNonLeapYear() {
        DateUtil date = new DateUtil(1, 3, 2023);
        date.decrement();
        Assert.assertEquals(new DateUtil(28, 2, 2023), date);
    }
}