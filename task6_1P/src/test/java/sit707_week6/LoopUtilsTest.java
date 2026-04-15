package sit707_week6;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for LoopUtils (Part B).
 * Achieves >90% code coverage across both functions.
 */
public class LoopUtilsTest {

    // -------------------------------------------------------
    // sumUpTo() — Part B (a): loop with simple statements
    // -------------------------------------------------------

    /** n = 0 → loop body never executes, returns 0 */
    @Test
    public void testSumUpToZero() {
        Assert.assertEquals(0, LoopUtils.sumUpTo(0));
    }

    /** n = 1 → single iteration */
    @Test
    public void testSumUpToOne() {
        Assert.assertEquals(1, LoopUtils.sumUpTo(1));
    }

    /** n = 5 → 1+2+3+4+5 = 15 */
    @Test
    public void testSumUpToFive() {
        Assert.assertEquals(15, LoopUtils.sumUpTo(5));
    }

    /** n = 10 → 55 */
    @Test
    public void testSumUpToTen() {
        Assert.assertEquals(55, LoopUtils.sumUpTo(10));
    }

    /** n = 100 → 5050 */
    @Test
    public void testSumUpToHundred() {
        Assert.assertEquals(5050, LoopUtils.sumUpTo(100));
    }

    /** Negative input → IllegalArgumentException */
    @Test(expected = IllegalArgumentException.class)
    public void testSumUpToNegative() {
        LoopUtils.sumUpTo(-1);
    }

    // -------------------------------------------------------
    // countEvens() — Part B (b): loop with conditional statement
    // -------------------------------------------------------

    /** n = 0 → no numbers to check, count = 0 */
    @Test
    public void testCountEvensZero() {
        Assert.assertEquals(0, LoopUtils.countEvens(0));
    }

    /** n = 1 → only 1 (odd), count = 0 */
    @Test
    public void testCountEvensOne() {
        Assert.assertEquals(0, LoopUtils.countEvens(1));
    }

    /** n = 2 → 2 is even, count = 1 */
    @Test
    public void testCountEvensTwo() {
        Assert.assertEquals(1, LoopUtils.countEvens(2));
    }

    /** n = 6 → evens are 2,4,6 → count = 3 */
    @Test
    public void testCountEvensSix() {
        Assert.assertEquals(3, LoopUtils.countEvens(6));
    }

    /** n = 7 → evens are 2,4,6 → count = 3 */
    @Test
    public void testCountEvensSeven() {
        Assert.assertEquals(3, LoopUtils.countEvens(7));
    }

    /** n = 10 → evens 2,4,6,8,10 → count = 5 */
    @Test
    public void testCountEvensTen() {
        Assert.assertEquals(5, LoopUtils.countEvens(10));
    }

    /** Negative input → IllegalArgumentException */
    @Test(expected = IllegalArgumentException.class)
    public void testCountEvensNegative() {
        LoopUtils.countEvens(-3);
    }
}