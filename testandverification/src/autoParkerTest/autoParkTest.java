package autoParkerTest;

import org.junit.Test;

import autoParker.AutoParkImpl;
import junit.framework.Assert;

public class AutoParkTest {

	/*
	 * --------------------------------------------------
	 * MOVE FORWARD
	 * --------------------------------------------------
	 */
	@Test
	public void testMoveForward(){
		AutoParkImpl autoPark = new AutoParkImpl();
		autoPark.moveForward();
		Assert.assertEquals(1, autoPark.getPosition());
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testEndofStreet(){
		AutoParkImpl autoPark = new AutoParkImpl(500, false);
		autoPark.moveForward();		
	}
	
	@Test
	public void testForwardSensorChange(){
		AutoParkImpl autoPark = new AutoParkImpl();
		int readingBefore = autoPark.getStreetValue(autoPark.getPosition());
		int[] readings = {2, 2, 2, 2, 2};
		autoPark.setSensorValues(1, readings);
		autoPark.setSensorValues(2, readings);
		autoPark.moveForward();
		Assert.assertNotSame(autoPark.getStreetValue(autoPark.getPosition()), readingBefore);
	}
	
	@Test
	public void testForwardParked(){
		AutoParkImpl autoPark = new AutoParkImpl();
		int positionBefore = autoPark.getPosition();
		autoPark.setParked(true);
		autoPark.moveForward();
		int positionAfter = autoPark.getPosition();
		Assert.assertEquals(positionBefore, positionAfter);
	}
	
	/*
	 * --------------------------------------------------
	 * IS EMPTY
	 * --------------------------------------------------
	 */
	
	@Test
	public void testIsEmptyNormal(){
		AutoParkImpl autoPark = new AutoParkImpl();
		int[] readings = {4, 4, 4, 4, 4};
		autoPark.setSensorValues(1, readings);
		autoPark.setSensorValues(2, readings);
		autoPark.moveForward();
		Assert.assertEquals(4, autoPark.getStreetValue(autoPark.getPosition()));
	}
	
	@Test
	public void testIsEmptySensorOneOnly(){
		AutoParkImpl autoPark = new AutoParkImpl();
		int[] readings = {3, 3, 3, 3, 3};
		int[] readings2 = {1, 150, 300, 500, 1000};
		autoPark.setSensorValues(1, readings);
		autoPark.setSensorValues(2, readings2);
		autoPark.moveForward();
		Assert.assertEquals(3, autoPark.getStreetValue(autoPark.getPosition()));
	}
	
	@Test
	public void testIsEmptyNoSensor(){
		AutoParkImpl autoPark = new AutoParkImpl();
		int[] readings = {1, 200, 500, 1000, 2000};
		autoPark.setSensorValues(1, readings);
		autoPark.setSensorValues(2, readings);
		autoPark.moveForward();
		Assert.assertEquals(-1, autoPark.getStreetValue(autoPark.getPosition()));
	}
	
	@Test
	public void testIsEmptyParked(){
		AutoParkImpl autoPark = new AutoParkImpl();
		int readingBefore = autoPark.getStreetValue(autoPark.getPosition());
		autoPark.setParked(true);
		autoPark.moveForward();
		int readingAfter = autoPark.getStreetValue(autoPark.getPosition());
		Assert.assertEquals(readingBefore, readingAfter);
	}
	
	/*
	 * --------------------------------------------------
	 * MOVE BACKWARD
	 * --------------------------------------------------
	 */
	
	@Test
	public void testMoveBackward(){
		AutoParkImpl autoPark = new AutoParkImpl();
		autoPark.setPosition(100);
		autoPark.moveBackward();
		Assert.assertEquals(99, autoPark.getPosition());
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testBeginningofStreet(){
		AutoParkImpl autoPark = new AutoParkImpl();
		autoPark.moveBackward();		
	}
	
	@Test
	public void testMoveBackwardSensorChange(){
		AutoParkImpl autoPark = new AutoParkImpl();
		autoPark.setPosition(100);
		int readingBefore = autoPark.getStreetValue(autoPark.getPosition());
		int[] readings = {3, 3, 3, 3, 3};
		autoPark.setSensorValues(1, readings);
		autoPark.setSensorValues(2, readings);
		autoPark.moveBackward();
		Assert.assertNotSame(autoPark.getStreetValue(autoPark.getPosition()), readingBefore);
	}
	
	@Test
	public void testMoveBackwardParked(){
		AutoParkImpl autoPark = new AutoParkImpl();
		autoPark.setParked(true);
		autoPark.setPosition(100);
		int positionBefore = autoPark.getPosition();
		autoPark.setParked(true);
		autoPark.moveBackward();
		int positionAfter = autoPark.getPosition();
		Assert.assertEquals(positionBefore, positionAfter);
	}
	
	/*
	 * --------------------------------------------------
	 * PARK
	 * --------------------------------------------------
	 */
	
	@Test
	public void testParkWithSpot(){
		AutoParkImpl autoPark = new AutoParkImpl();
		autoPark.setPosition(50);
		int currentPos = autoPark.getPosition();
		autoPark.setStreetValue(currentPos, 5);
		autoPark.setStreetValue(currentPos-1, 7);
		autoPark.setStreetValue(currentPos-2, 5);
		autoPark.setStreetValue(currentPos-3, 10);
		autoPark.setStreetValue(currentPos-4, 12);
		autoPark.park();
		Assert.assertTrue(autoPark.getParked());
	}
	
	@Test
	public void testParkWithNoSpot(){
		AutoParkImpl autoPark = new AutoParkImpl();
		autoPark.setPosition(50);
		autoPark.park();
		Assert.assertFalse(autoPark.getParked());
	}
	
	@Test
	public void testParkForwardSpot(){
		AutoParkImpl autoPark = new AutoParkImpl();
		autoPark.setPosition(50);
		int currentPos = autoPark.getPosition();
		autoPark.setStreetValue(currentPos+50, 5);
		autoPark.setStreetValue(currentPos+51, 7);
		autoPark.setStreetValue(currentPos+52, 5);
		autoPark.setStreetValue(currentPos+53, 10);
		autoPark.setStreetValue(currentPos+54, 12);
		autoPark.park();
		Assert.assertTrue(autoPark.getParked());
	}
}
