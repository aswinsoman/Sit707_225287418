package sit707_week5;

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

		// Retrieve all the hours temperatures recorded as for today
		double minTemperature = 1000;
		for (int i=0; i<nHours; i++) {
			// Hour range: 1 to nHours
			double temperatureVal = hourlyTemperatures[i];
			if (minTemperature > temperatureVal) {
				minTemperature = temperatureVal;
			}
		}

		// Should be equal to the min value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureMinFromCache() == minTemperature);
	}

	@Test
	public void testTemperatureMax() {
		System.out.println("+++ testTemperatureMax +++");

		// Retrieve all the hours temperatures recorded as for today
		double maxTemperature = -1;
		for (int i=0; i<nHours; i++) {
			// Hour range: 1 to nHours
			double temperatureVal = hourlyTemperatures[i];
			if (maxTemperature < temperatureVal) {
				maxTemperature = temperatureVal;
			}
		}

		// Should be equal to the min value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureMaxFromCache() == maxTemperature);
	}

	@Test
	public void testTemperatureAverage() {
		System.out.println("+++ testTemperatureAverage +++");

		// Retrieve all the hours temperatures recorded as for today
		double sumTemp = 0;
		for (int i=0; i<nHours; i++) {
			// Hour range: 1 to nHours
			double temperatureVal = hourlyTemperatures[i];
			sumTemp += temperatureVal;
		}
		double averageTemp = sumTemp / nHours;

		// Should be equal to the min value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureAverageFromCache() == averageTemp);
	}

	@Test
	public void testTemperaturePersist() {
		/*
		 * Remove below comments ONLY for 5.3C task.
		 */
//		System.out.println("+++ testTemperaturePersist +++");
//		
//		// Initialise controller
//		WeatherController wController = WeatherController.getInstance();
//		
//		String persistTime = wController.persistTemperature(10, 19.5);
//		String now = new SimpleDateFormat("H:m:s").format(new Date());
//		System.out.println("Persist time: " + persistTime + ", now: " + now);
//		
//		Assert.assertTrue(persistTime.equals(now));
//		
//		wController.close();
	}
}