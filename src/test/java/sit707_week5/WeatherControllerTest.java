package sit707_week5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WeatherControllerTest {
	
	private static WeatherController wController;
	private static double[] todaysHourlyTemperature;

	
	@BeforeClass
	public static void startUp() {
		
//		initialise WeatherController Object
		wController = WeatherController.getInstance();
		
//		Load all temperature in local variable to speed up process
		int nHours = wController.getTotalHours();
		todaysHourlyTemperature = new double[nHours];
		for (int i=0; i<nHours; i++) {
			todaysHourlyTemperature[i] = wController.getTemperatureForHour(i+1); 
		}

	}
	
	@AfterClass
	public static void cleanUp() {
		wController.close();
	}

	@Test
	public void testStudentIdentity() {
		String studentId = "224114235";
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "shreya bista";
		Assert.assertNotNull("Student name is null", studentName);
	}
	
    // Test case to check if the minimun temperature is calculated 
	@Test
	public void testTemperatureMin() {
		System.out.println("+++ testTemperatureMin +++");
		
		
		// Retrieve all the hours temperatures recorded as for today
		int nHours = wController.getTotalHours();
		double minTemperature = 1000;
		for (int i=0; i<nHours; i++) {
			// Hour range: 1 to nHours
			double temperatureVal = todaysHourlyTemperature[i]; 
			if (minTemperature > temperatureVal) {
				minTemperature = temperatureVal;
			}
		}
		
		// Should be equal to the min value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureMinFromCache() == minTemperature);
			
	}
	
    // Test case to check if the maximun temperature is calculated
	@Test
	public void testTemperatureMax() {
		System.out.println("+++ testTemperatureMax +++");
		
		// Retrieve all the hours temperatures recorded as for today
		int nHours = wController.getTotalHours();
		double maxTemperature = -1;
		for (int i=0; i<nHours; i++) {
			// Hour range: 1 to nHours
			double temperatureVal = todaysHourlyTemperature[i]; 
			if (maxTemperature < temperatureVal) {
				maxTemperature = temperatureVal;
			}
		}
		
		// Should be equal to the min value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureMaxFromCache() == maxTemperature);
		
	}

    // Test case to check if the average temperature is calculated
	@Test
	public void testTemperatureAverage() {
		System.out.println("+++ testTemperatureAverage +++");
		
		// Retrieve all the hours temperatures recorded as for today
		int nHours = wController.getTotalHours();
		double sumTemp = 0;
		for (int i=0; i<nHours; i++) {
			// Hour range: 1 to nHours
			double temperatureVal = todaysHourlyTemperature[i]; 
			sumTemp += temperatureVal;
		}
		double averageTemp = sumTemp / nHours;
		
		// Should be equal to the min value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureAverageFromCache() == averageTemp);
		
	}
	
    // Test case to check the persistTemperature() method
	@Test
	public void testTemperaturePersist() throws ParseException {
		
		System.out.println("+++ testTemperaturePersist +++");
		String persistTime = wController.persistTemperature(10, 19.5);
		persistTime = new SimpleDateFormat("H:m").format(new SimpleDateFormat("H:m:s").parse(persistTime));
		String now = new SimpleDateFormat("H:m").format(new Date());
		System.out.println("Persist time: " + persistTime + ", now: " + now);
		Assert.assertTrue(persistTime.equals(now));
		
	}
}
