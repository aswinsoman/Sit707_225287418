package sit707_week6;
import org.junit.Assert;
import org.junit.Test;
public class WeatherAndMathUtilsTest {
	
	@Test
	public void testStudentIdentity() {
		String studentId ="s225287418";
		Assert.assertNotNull("Student ID is null", studentId);
	}
	@Test
	public void testStudentName() {
		String studentName = "Aswin Soman";
		Assert.assertNotNull("Student name is null", studentName);
	}

	// -------------------------------------------------------
	// isEven() tests
	// -------------------------------------------------------

	@Test
	public void testFalseNumberIsEven() {
		Assert.assertFalse(WeatherAndMathUtils.isEven(3));
	}

	@Test
	public void testTrueNumberIsEven() {
		Assert.assertTrue(WeatherAndMathUtils.isEven(4));
	}

	@Test
	public void testZeroIsEven() {
		Assert.assertTrue(WeatherAndMathUtils.isEven(0));
	}

	@Test
	public void testNegativeEvenIsEven() {
		Assert.assertTrue(WeatherAndMathUtils.isEven(-2));
	}

	@Test
	public void testNegativeOddIsNotEven() {
		Assert.assertFalse(WeatherAndMathUtils.isEven(-3));
	}

	// -------------------------------------------------------
	// isPrime() tests
	// -------------------------------------------------------

	@Test
	public void testIsPrimeWithPrime() {
		Assert.assertTrue(WeatherAndMathUtils.isPrime(7));
	}

	@Test
	public void testIsPrimeWithTwo() {
		Assert.assertTrue(WeatherAndMathUtils.isPrime(2));
	}

	@Test
	public void testIsPrimeWithLargePrime() {
		Assert.assertTrue(WeatherAndMathUtils.isPrime(97));
	}

	@Test
	public void testIsPrimeWithOne() {
		Assert.assertFalse(WeatherAndMathUtils.isPrime(1));
	}

	@Test
	public void testIsPrimeWithZero() {
		Assert.assertFalse(WeatherAndMathUtils.isPrime(0));
	}

	@Test
	public void testIsPrimeWithNegative() {
		Assert.assertFalse(WeatherAndMathUtils.isPrime(-5));
	}

	@Test
	public void testIsPrimeWithComposite() {
		Assert.assertFalse(WeatherAndMathUtils.isPrime(9));
	}

	@Test
	public void testIsPrimeWithEvenComposite() {
		Assert.assertFalse(WeatherAndMathUtils.isPrime(100));
	}

	// -------------------------------------------------------
	// weatherAdvice() — CANCEL branch tests
	// -------------------------------------------------------

    @Test
    public void testCancelWeatherAdvice() {
    	Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(70.1, 0.0));
    }

	@Test
	public void testCancelDangerousRainfallOnly() {
		Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(0.0, 6.1));
	}

	@Test
	public void testCancelWindAndRainCombined() {
		Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(45.1, 4.1));
	}

	// -------------------------------------------------------
	// weatherAdvice() — WARN branch tests
	// -------------------------------------------------------

	@Test
	public void testWarnHighWindOnly() {
		Assert.assertEquals("WARN", WeatherAndMathUtils.weatherAdvice(45.1, 0.0));
	}

	@Test
	public void testWarnHighRainfallOnly() {
		Assert.assertEquals("WARN", WeatherAndMathUtils.weatherAdvice(0.0, 4.1));
	}

	// -------------------------------------------------------
	// weatherAdvice() — ALL CLEAR branch tests
	// -------------------------------------------------------

	@Test
	public void testAllClearNormal() {
		Assert.assertEquals("ALL CLEAR", WeatherAndMathUtils.weatherAdvice(10.0, 1.0));
	}

	@Test
	public void testAllClearZeroValues() {
		Assert.assertEquals("ALL CLEAR", WeatherAndMathUtils.weatherAdvice(0.0, 0.0));
	}

	@Test
	public void testAllClearBoundaryWind45() {
		Assert.assertEquals("ALL CLEAR", WeatherAndMathUtils.weatherAdvice(45.0, 0.0));
	}

	@Test
	public void testAllClearBoundaryRain4() {
		Assert.assertEquals("ALL CLEAR", WeatherAndMathUtils.weatherAdvice(0.0, 4.0));
	}

	// -------------------------------------------------------
	// weatherAdvice() — IllegalArgumentException tests
	// -------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeWindSpeedThrowsException() {
		WeatherAndMathUtils.weatherAdvice(-1.0, 0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativePrecipitationThrowsException() {
		WeatherAndMathUtils.weatherAdvice(0.0, -1.0);
	}
}