package sit707_tasks;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author Ahsan Habib
 */
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

	@Test
	public void testMaxJanuary31ShouldIncrementToFebruary1() {
		// January max boundary area: max+1
		DateUtil date = new DateUtil(31, 1, 2024);
        System.out.println("january31ShouldIncrementToFebruary1 > " + date);
        date.increment();
        System.out.println(date);
        Assert.assertEquals(2, date.getMonth());
        Assert.assertEquals(1, date.getDay());
	}
	
	@Test
	public void testMaxJanuary31ShouldDecrementToJanuary30() {
		// January max boundary area: max-1
		DateUtil date = new DateUtil(31, 1, 2024);
        System.out.println("january31ShouldDecrementToJanuary30 > " + date);
        date.decrement();
        System.out.println(date);
        Assert.assertEquals(30, date.getDay());
        Assert.assertEquals(1, date.getMonth());
	}
	
	@Test
	public void testNominalJanuary() {
		int rand_day_1_to_31 = 1 + new Random().nextInt(31);
        DateUtil date = new DateUtil(rand_day_1_to_31, 1, 2024);
        System.out.println("testJanuaryNominal > " + date);
        date.increment();
        System.out.println(date);
	}
	
	/*
	 * Complete below test cases.
	 */
	
	@Test
	public void testMinJanuary1ShouldIncrementToJanuary2() {
		    DateUtil date = new DateUtil(1, 1, 2024);
	        System.out.println("testMinJanuary1ShouldIncrementToJanuary2 > " + date);
	        date.increment();
	        System.out.println(date);
	        Assert.assertEquals(2, date.getDay());
	        Assert.assertEquals(1, date.getMonth());
	}
	
	@Test
	public void testMinJanuary1ShouldDecrementToDecember31() {
		DateUtil date = new DateUtil(1, 1, 2024);
        System.out.println("testMinJanuary1ShouldDecrementToDecember31 > " + date);
        date.decrement();
        System.out.println(date);
        Assert.assertEquals(31, date.getDay());
        Assert.assertEquals(12, date.getMonth());
        Assert.assertEquals(2023, date.getYear());
	}
	
	/*
	 * Write tests for rest months of year 2024.
	 */
	
	@Test
    public void testCase1A_June1ShouldDecrementToMay31() {
        DateUtil date = new DateUtil(1, 6, 1994);
        date.decrement();
        Assert.assertEquals(31, date.getDay());
        Assert.assertEquals(5, date.getMonth());
        Assert.assertEquals(1994, date.getYear());
    }
 
    // 2A: Day=2, Month=6, Year=1994 -> prev = 1-6-1994
    @Test
    public void testCase2A_June2ShouldDecrementToJune1() {
        DateUtil date = new DateUtil(2, 6, 1994);
        date.decrement();
        Assert.assertEquals(1, date.getDay());
        Assert.assertEquals(6, date.getMonth());
        Assert.assertEquals(1994, date.getYear());
    }
 
    // 3A: Day=15, Month=6, Year=1994 -> prev = 14-6-1994
    @Test
    public void testCase3A_June15ShouldDecrementToJune14() {
        DateUtil date = new DateUtil(15, 6, 1994);
        date.decrement();
        Assert.assertEquals(14, date.getDay());
        Assert.assertEquals(6, date.getMonth());
        Assert.assertEquals(1994, date.getYear());
    }
 
    // 4A: Day=30, Month=6, Year=1994 -> prev = 29-6-1994
    @Test
    public void testCase4A_June30ShouldDecrementToJune29() {
        DateUtil date = new DateUtil(30, 6, 1994);
        date.decrement();
        Assert.assertEquals(29, date.getDay());
        Assert.assertEquals(6, date.getMonth());
        Assert.assertEquals(1994, date.getYear());
    }
 
    // 5A: Day=31, Month=6, Year=1994 -> Invalid Date (June has 30 days)
    @Test(expected = RuntimeException.class)
    public void testCase5A_June31ShouldBeInvalid() {
        new DateUtil(31, 6, 1994); // Should throw RuntimeException
    }
 
    // 6A: Day=15, Month=1, Year=1994 -> prev = 14-1-1994
    @Test
    public void testCase6A_January15ShouldDecrementToJanuary14() {
        DateUtil date = new DateUtil(15, 1, 1994);
        date.decrement();
        Assert.assertEquals(14, date.getDay());
        Assert.assertEquals(1, date.getMonth());
        Assert.assertEquals(1994, date.getYear());
    }
 
    // 7A: Day=15, Month=2, Year=1994 -> prev = 14-2-1994
    @Test
    public void testCase7A_February15ShouldDecrementToFebruary14() {
        DateUtil date = new DateUtil(15, 2, 1994);
        date.decrement();
        Assert.assertEquals(14, date.getDay());
        Assert.assertEquals(2, date.getMonth());
        Assert.assertEquals(1994, date.getYear());
    }
 
    // 8A: Day=15, Month=11, Year=1994 -> prev = 14-11-1994
    @Test
    public void testCase8A_November15ShouldDecrementToNovember14() {
        DateUtil date = new DateUtil(15, 11, 1994);
        date.decrement();
        Assert.assertEquals(14, date.getDay());
        Assert.assertEquals(11, date.getMonth());
        Assert.assertEquals(1994, date.getYear());
    }
 
    // 9A: Day=15, Month=12, Year=1994 -> prev = 14-12-1994
    @Test
    public void testCase9A_December15ShouldDecrementToDecember14() {
        DateUtil date = new DateUtil(15, 12, 1994);
        date.decrement();
        Assert.assertEquals(14, date.getDay());
        Assert.assertEquals(12, date.getMonth());
        Assert.assertEquals(1994, date.getYear());
    }
 
    // 10A: Day=15, Month=6, Year=1700 -> prev = 14-6-1700
    @Test
    public void testCase10A_Year1700June15ShouldDecrementToJune14() {
        DateUtil date = new DateUtil(15, 6, 1700);
        date.decrement();
        Assert.assertEquals(14, date.getDay());
        Assert.assertEquals(6, date.getMonth());
        Assert.assertEquals(1700, date.getYear());
    }
 
    // 11A: Day=15, Month=6, Year=1701 -> prev = 14-6-1701
    @Test
    public void testCase11A_Year1701June15ShouldDecrementToJune14() {
        DateUtil date = new DateUtil(15, 6, 1701);
        date.decrement();
        Assert.assertEquals(14, date.getDay());
        Assert.assertEquals(6, date.getMonth());
        Assert.assertEquals(1701, date.getYear());
    }
 
    // 12A: Day=15, Month=6, Year=2023 -> prev = 14-6-2023
    @Test
    public void testCase12A_Year2023June15ShouldDecrementToJune14() {
        DateUtil date = new DateUtil(15, 6, 2023);
        date.decrement();
        Assert.assertEquals(14, date.getDay());
        Assert.assertEquals(6, date.getMonth());
        Assert.assertEquals(2023, date.getYear());
    }
 
    // 13A: Day=15, Month=6, Year=2024 -> prev = 14-6-2024
    @Test
    public void testCase13A_Year2024June15ShouldDecrementToJune14() {
        DateUtil date = new DateUtil(15, 6, 2024);
        date.decrement();
        Assert.assertEquals(14, date.getDay());
        Assert.assertEquals(6, date.getMonth());
        Assert.assertEquals(2024, date.getYear());
    }
 
    // =========================================================
    // TABLE 1B–13B: NEXT DATE (increment) TEST CASES
    // =========================================================
 
    // 1B: Day=1, Month=6, Year=1994 -> next = 2-6-1994
    @Test
    public void testCase1B_June1ShouldIncrementToJune2() {
        DateUtil date = new DateUtil(1, 6, 1994);
        date.increment();
        Assert.assertEquals(2, date.getDay());
        Assert.assertEquals(6, date.getMonth());
        Assert.assertEquals(1994, date.getYear());
    }
 
    // 2B: Day=2, Month=6, Year=1994 -> next = 3-6-1994
    @Test
    public void testCase2B_June2ShouldIncrementToJune3() {
        DateUtil date = new DateUtil(2, 6, 1994);
        date.increment();
        Assert.assertEquals(3, date.getDay());
        Assert.assertEquals(6, date.getMonth());
        Assert.assertEquals(1994, date.getYear());
    }
 
    // 3B: Day=15, Month=6, Year=1994 -> next = 16-6-1994
    @Test
    public void testCase3B_June15ShouldIncrementToJune16() {
        DateUtil date = new DateUtil(15, 6, 1994);
        date.increment();
        Assert.assertEquals(16, date.getDay());
        Assert.assertEquals(6, date.getMonth());
        Assert.assertEquals(1994, date.getYear());
    }
 
    // 4B: Day=30, Month=6, Year=1994 -> next = 1-7-1994 (June has 30 days)
    @Test
    public void testCase4B_June30ShouldIncrementToJuly1() {
        DateUtil date = new DateUtil(30, 6, 1994);
        date.increment();
        Assert.assertEquals(1, date.getDay());
        Assert.assertEquals(7, date.getMonth());
        Assert.assertEquals(1994, date.getYear());
    }
 
    // 5B: Day=31, Month=6, Year=1994 -> Invalid Date
    @Test(expected = RuntimeException.class)
    public void testCase5B_June31ShouldBeInvalid() {
        new DateUtil(31, 6, 1994);
    }
 
    // 6B: Day=15, Month=1, Year=1994 -> next = 16-1-1994
    @Test
    public void testCase6B_January15ShouldIncrementToJanuary16() {
        DateUtil date = new DateUtil(15, 1, 1994);
        date.increment();
        Assert.assertEquals(16, date.getDay());
        Assert.assertEquals(1, date.getMonth());
        Assert.assertEquals(1994, date.getYear());
    }
 
    // 7B: Day=15, Month=2, Year=1994 -> next = 16-2-1994
    @Test
    public void testCase7B_February15ShouldIncrementToFebruary16() {
        DateUtil date = new DateUtil(15, 2, 1994);
        date.increment();
        Assert.assertEquals(16, date.getDay());
        Assert.assertEquals(2, date.getMonth());
        Assert.assertEquals(1994, date.getYear());
    }
 
    // 8B: Day=15, Month=11, Year=1994 -> next = 16-11-1994
    @Test
    public void testCase8B_November15ShouldIncrementToNovember16() {
        DateUtil date = new DateUtil(15, 11, 1994);
        date.increment();
        Assert.assertEquals(16, date.getDay());
        Assert.assertEquals(11, date.getMonth());
        Assert.assertEquals(1994, date.getYear());
    }
 
    // 9B: Day=15, Month=12, Year=1994 -> next = 16-12-1994
    @Test
    public void testCase9B_December15ShouldIncrementToDecember16() {
        DateUtil date = new DateUtil(15, 12, 1994);
        date.increment();
        Assert.assertEquals(16, date.getDay());
        Assert.assertEquals(12, date.getMonth());
        Assert.assertEquals(1994, date.getYear());
    }
 
    // 10B: Day=15, Month=6, Year=1700 -> next = 16-6-1700
    @Test
    public void testCase10B_Year1700June15ShouldIncrementToJune16() {
        DateUtil date = new DateUtil(15, 6, 1700);
        date.increment();
        Assert.assertEquals(16, date.getDay());
        Assert.assertEquals(6, date.getMonth());
        Assert.assertEquals(1700, date.getYear());
    }
 
    // 11B: Day=15, Month=6, Year=1701 -> next = 16-6-1701
    @Test
    public void testCase11B_Year1701June15ShouldIncrementToJune16() {
        DateUtil date = new DateUtil(15, 6, 1701);
        date.increment();
        Assert.assertEquals(16, date.getDay());
        Assert.assertEquals(6, date.getMonth());
        Assert.assertEquals(1701, date.getYear());
    }
 
    // 12B: Day=15, Month=6, Year=2023 -> next = 16-6-2023
    @Test
    public void testCase12B_Year2023June15ShouldIncrementToJune16() {
        DateUtil date = new DateUtil(15, 6, 2023);
        date.increment();
        Assert.assertEquals(16, date.getDay());
        Assert.assertEquals(6, date.getMonth());
        Assert.assertEquals(2023, date.getYear());
    }
 
    // 13B: Day=15, Month=6, Year=2024 -> next = 16-6-2024
    @Test
    public void testCase13B_Year2024June15ShouldIncrementToJune16() {
        DateUtil date = new DateUtil(15, 6, 2024);
        date.increment();
        Assert.assertEquals(16, date.getDay());
        Assert.assertEquals(6, date.getMonth());
        Assert.assertEquals(2024, date.getYear());
    }
 
    // =========================================================
    // EXTRA: FEBRUARY LEAP YEAR TEST CASES
    // =========================================================
 
    // Leap year: Feb 28 increments to Feb 29
    @Test
    public void testLeapYear_Feb28ShouldIncrementToFeb29() {
        DateUtil date = new DateUtil(28, 2, 2024); // 2024 is a leap year
        date.increment();
        Assert.assertEquals(29, date.getDay());
        Assert.assertEquals(2, date.getMonth());
        Assert.assertEquals(2024, date.getYear());
    }
 
    // Leap year: Feb 29 increments to Mar 1
    @Test
    public void testLeapYear_Feb29ShouldIncrementToMarch1() {
        DateUtil date = new DateUtil(29, 2, 2024);
        date.increment();
        Assert.assertEquals(1, date.getDay());
        Assert.assertEquals(3, date.getMonth());
        Assert.assertEquals(2024, date.getYear());
    }
 
    // Leap year: Feb 29 decrements to Feb 28
    @Test
    public void testLeapYear_Feb29ShouldDecrementToFeb28() {
        DateUtil date = new DateUtil(29, 2, 2024);
        date.decrement();
        Assert.assertEquals(28, date.getDay());
        Assert.assertEquals(2, date.getMonth());
        Assert.assertEquals(2024, date.getYear());
    }
 
    // Non-leap year: Feb 28 increments to Mar 1
    @Test
    public void testNonLeapYear_Feb28ShouldIncrementToMarch1() {
        DateUtil date = new DateUtil(28, 2, 2023); // 2023 is NOT a leap year
        date.increment();
        Assert.assertEquals(1, date.getDay());
        Assert.assertEquals(3, date.getMonth());
        Assert.assertEquals(2023, date.getYear());
    }
 
    // Non-leap year: Feb 29 should be invalid
    @Test(expected = RuntimeException.class)
    public void testNonLeapYear_Feb29ShouldBeInvalid() {
        new DateUtil(29, 2, 2023);
    }
 
    // Leap year: Mar 1 decrements to Feb 29
    @Test
    public void testLeapYear_March1ShouldDecrementToFeb29() {
        DateUtil date = new DateUtil(1, 3, 2024);
        date.decrement();
        Assert.assertEquals(29, date.getDay());
        Assert.assertEquals(2, date.getMonth());
        Assert.assertEquals(2024, date.getYear());
    }
 
    // Non-leap year: Mar 1 decrements to Feb 28
    @Test
    public void testNonLeapYear_March1ShouldDecrementToFeb28() {
        DateUtil date = new DateUtil(1, 3, 2023);
        date.decrement();
        Assert.assertEquals(28, date.getDay());
        Assert.assertEquals(2, date.getMonth());
        Assert.assertEquals(2023, date.getYear());
    }
}
