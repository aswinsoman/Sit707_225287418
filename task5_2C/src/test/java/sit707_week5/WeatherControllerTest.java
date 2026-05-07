package sit707_week5;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class WeatherControllerTest {

	// Shared across all tests - initialised once in @BeforeClass
	private static WeatherController wController;
	private static int nHours;
	private static double[] hourlyTemperatures;

	// Arrange: runs once before all tests - slow init happens only once
	@BeforeClass
	public static void setUpBeforeClass() {
		System.out.println("+++ setUpBeforeClass +++");

		// Initialise controller
		wController = WeatherController.getInstance();
		nHours = wController.getTotalHours();

		// Retrieve all the hours temperatures recorded as for today - stored locally to reuse across tests
		hourlyTemperatures = new double[nHours];
		for (int i = 0; i < nHours; i++) {
			// Hour range: 1 to nHours
			hourlyTemperatures[i] = wController.getTemperatureForHour(i + 1);
		}
	}

	// After: runs once after all tests - slow shutdown happens only once
	@AfterClass
	public static void tearDownAfterClass() {
		System.out.println("+++ tearDownAfterClass +++");

		// Shutdown controller
		wController.close();
	}

	@Test
	public void testStudentIdentity() {
		String studentId = "225287418";
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Aswin Soman";
		Assert.assertNotNull("Student name is null", studentName);
	}

	@Test
	public void testTemperatureMin() {
		System.out.println("+++ testTemperatureMin +++");

		// Act: compute min from locally cached hourly values
		double minTemperature = 1000;
		for (int i = 0; i < nHours; i++) {
			double temperatureVal = hourlyTemperatures[i];
			if (minTemperature > temperatureVal) {
				minTemperature = temperatureVal;
			}
		}

		// Assert: should match the controller's cached minimum
		Assert.assertTrue(wController.getTemperatureMinFromCache() == minTemperature);
	}

	@Test
	public void testTemperatureMax() {
		System.out.println("+++ testTemperatureMax +++");

		// Act: compute max from locally cached hourly values
		double maxTemperature = -1;
		for (int i = 0; i < nHours; i++) {
			double temperatureVal = hourlyTemperatures[i];
			if (maxTemperature < temperatureVal) {
				maxTemperature = temperatureVal;
			}
		}

		// Assert: should match the controller's cached maximum
		Assert.assertTrue(wController.getTemperatureMaxFromCache() == maxTemperature);
	}

	@Test
	public void testTemperatureAverage() {
		System.out.println("+++ testTemperatureAverage +++");

		// Act: compute average from locally cached hourly values
		double sumTemp = 0;
		for (int i = 0; i < nHours; i++) {
			double temperatureVal = hourlyTemperatures[i];
			sumTemp += temperatureVal;
		}
		double averageTemp = sumTemp / nHours;

		// Assert: should match the controller's cached average
		Assert.assertTrue(wController.getTemperatureAverageFromCache() == averageTemp);
	}

	@Test
	public void testTemperaturePersist() {
		System.out.println("+++ testTemperaturePersist +++");

		
		// Create a single fixed point in time
		final Date fixedNow = new Date();

		// Inject a fake clock that always returns this fixed date
		wController.setClock(() -> fixedNow);

		// Format the expected time string from the same fixed date
		String expectedTime = new SimpleDateFormat("H:m:s").format(fixedNow);

		// ------- Act -------
		// Call persist -- the method will use our fake clock, not the real system clock
		String persistTime = wController.persistTemperature(10, 19.5);
		System.out.println("Persist time: " + persistTime + ", expected: " + expectedTime);

		// ------- Assert -------
		// Both strings come from the same frozen Date, so they will always match
		Assert.assertEquals(expectedTime, persistTime);

		// Restore the real clock so other tests are not affected
		wController.setClock(Date::new);
	}
}