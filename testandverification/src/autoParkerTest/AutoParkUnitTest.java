package autoParkerTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Stack;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import autoParker.AutoParkImpl;
import autoParker.ISensor;
import autoParker.TestSensor;
import autoParker.AutoParkImpl.OffStreetException;

public class AutoParkUnitTest {

	/*
	 * --------------------------------------------------
	 * MOVE FORWARD
	 * --------------------------------------------------
	 */
	@Test
	public void testMoveForward(){
		try{
			AutoParkImpl autoPark = new AutoParkImpl();
			autoPark.moveForward();
			assertEquals(1, autoPark.getPosition());	
		}catch(Exception e){
			fail("There should be no exceptions in this test");
		}

	}
	
	@Test(expected=OffStreetException.class)
	public void testEndofStreet() throws OffStreetException{
		TestSensor testSensor1 = new TestSensor();
		TestSensor testSensor2 = new TestSensor();
		AutoParkImpl autoPark = new AutoParkImpl(500, false, testSensor1, testSensor2);
		int[] readings = {2, 2, 2, 2, 2};
		autoPark.setSensorValues(1, readings);
		autoPark.setSensorValues(2, readings);
		autoPark.moveForward();		
	}
	
	@Test
	public void testForwardSensorChange(){
		try{
			AutoParkImpl autoPark = new AutoParkImpl();
			int readingBefore = autoPark.getStreetValue(autoPark.getPosition());
			int[] readings = {2, 2, 2, 2, 2};
			autoPark.setSensorValues(1, readings);
			autoPark.setSensorValues(2, readings);
			autoPark.moveForward();
			assertNotSame(autoPark.getStreetValue(autoPark.getPosition()), readingBefore);
		}catch(Exception e){
			fail("There should be no exceptions in this test");
		}
	}
	
	@Test
	public void testForwardParked(){
		try{
			AutoParkImpl autoPark = new AutoParkImpl();
			int positionBefore = autoPark.getPosition();
			autoPark.setParked(true);
			autoPark.moveForward();
			int positionAfter = autoPark.getPosition();
			assertEquals(positionBefore, positionAfter);
		}catch(Exception e){
			fail("There should be no exceptions in this test");
		}
	}
	
	/*
	 * --------------------------------------------------
	 * IS EMPTY
	 * --------------------------------------------------
	 */
	
	@Test
	public void testIsEmptyNormal(){
		try{
			AutoParkImpl autoPark = new AutoParkImpl();
			int[] readings = {4, 4, 4, 4, 4};
			autoPark.setSensorValues(1, readings);
			autoPark.setSensorValues(2, readings);
			autoPark.moveForward();
			assertEquals(4, autoPark.getStreetValue(autoPark.getPosition()));
		}catch(Exception e){
			fail("There should be no exceptions in this test");
		}
	}
	
	@Test
	public void testIsEmptySensorOneOnly(){
		try{
			AutoParkImpl autoPark = new AutoParkImpl();
			int[] readings = {3, 3, 3, 3, 3};
			int[] readings2 = {1, 150, 300, 500, 1000};
			autoPark.setSensorValues(1, readings);
			autoPark.setSensorValues(2, readings2);
			autoPark.moveForward();
			assertEquals(3, autoPark.getStreetValue(autoPark.getPosition()));
		}catch(Exception e){
			fail("There should be no exceptions in this test");
		}
	}
	
	@Test
	public void testIsEmptyNoSensor(){
		try{
			AutoParkImpl autoPark = new AutoParkImpl();
			int[] readings = {1, 200, 500, 1000, 2000};
			autoPark.setSensorValues(1, readings);
			autoPark.setSensorValues(2, readings);
			autoPark.moveForward();
			assertEquals(-1, autoPark.getStreetValue(autoPark.getPosition()));
		}catch(Exception e){
			fail("There should be no exceptions in this test");
		}
	}
	
	@Test
	public void testIsEmptyParked(){
		try{
			AutoParkImpl autoPark = new AutoParkImpl();
			int readingBefore = autoPark.getStreetValue(autoPark.getPosition());
			autoPark.setParked(true);
			autoPark.moveForward();
			int readingAfter = autoPark.getStreetValue(autoPark.getPosition());
			assertEquals(readingBefore, readingAfter);
		}catch(Exception e){
			fail("There should be no exceptions in this test");
		}
	}
	
	/*
	 * --------------------------------------------------
	 * MOVE BACKWARD
	 * --------------------------------------------------
	 */
	
	@Test
	public void testMoveBackward(){
		try{
			AutoParkImpl autoPark = new AutoParkImpl();
			autoPark.setPosition(100);
			autoPark.moveBackward();
			assertEquals(99, autoPark.getPosition());
		}catch(Exception e){
			fail("There should be no exceptions in this test");
		}
	}
	
	@Test(expected=OffStreetException.class)
	public void testBeginningofStreet() throws OffStreetException{
		AutoParkImpl autoPark = new AutoParkImpl();
		autoPark.moveBackward();		
	}
	
	@Test
	public void testMoveBackwardSensorChange(){
		try{
			AutoParkImpl autoPark = new AutoParkImpl();
			autoPark.setPosition(100);
			int readingBefore = autoPark.getStreetValue(autoPark.getPosition());
			int[] readings = {3, 3, 3, 3, 3};
			autoPark.setSensorValues(1, readings);
			autoPark.setSensorValues(2, readings);
			autoPark.moveBackward();
			assertNotSame(autoPark.getStreetValue(autoPark.getPosition()), readingBefore);
		}catch(Exception e){
			fail("There should be no exceptions in this test");
		}
	}
	
	@Test
	public void testMoveBackwardParked(){
		try{
			AutoParkImpl autoPark = new AutoParkImpl();
			autoPark.setParked(true);
			autoPark.setPosition(100);
			int positionBefore = autoPark.getPosition();
			autoPark.setParked(true);
			autoPark.moveBackward();
			int positionAfter = autoPark.getPosition();
			assertEquals(positionBefore, positionAfter);
		}catch(Exception e){
			fail("There should be no exceptions in this test");
		}
	}
	
	/*
	 * --------------------------------------------------
	 * PARK
	 * --------------------------------------------------
	 */
	
	@Test
	public void testParkWithSpot(){
		try{
			AutoParkImpl autoPark = new AutoParkImpl();
			autoPark.setPosition(50);
			int currentPos = autoPark.getPosition();
			autoPark.setStreetValue(50, 5);
			autoPark.setStreetValue(49, 7);
			autoPark.setStreetValue(48, 5);
			autoPark.setStreetValue(47, 10);
			autoPark.setStreetValue(46, 12);
			autoPark.park();
			assertTrue(autoPark.getParked());
		}catch(Exception e){
			fail("There should be no exceptions in this test");
		}
	}
	
	@Test
	public void testParkWithNoSpot(){
		try{
			AutoParkImpl autoPark = new AutoParkImpl();
			autoPark.setPosition(50);
			autoPark.park();
			assertFalse(autoPark.getParked());
		}catch(Exception e){
			fail("There should be no exceptions in this test");
		}
	}
	
	@Test
	public void testParkForwardSpot(){
		try{
			AutoParkImpl autoPark = new AutoParkImpl();
			autoPark.setUseSensors(false);
			autoPark.setPosition(50);
			autoPark.setStreetValue(100, 5);
			autoPark.setStreetValue(101, 7);
			autoPark.setStreetValue(102, 5);
			autoPark.setStreetValue(103, 10);
			autoPark.setStreetValue(104, 12);
			autoPark.park();
			assertTrue(autoPark.getParked());
		}catch(Exception e){
			fail("There should be no exceptions in this test");
		}
	}
	
	@Test
	public void testParkWhenParked(){
		try{
			AutoParkImpl autoPark = new AutoParkImpl();
			autoPark.setParked(true);
			autoPark.setPosition(100);
			int positionBefore = autoPark.getPosition();
			autoPark.park();
			int positionAfter = autoPark.getPosition();
			assertEquals(positionBefore, positionAfter);
		}catch(Exception e){
			fail("There should be no exceptions in this test");
		}
	}
	
	@Test
	public void testParkWhenUnderFour(){
		try{
			AutoParkImpl autoPark = new AutoParkImpl();
			autoPark.setPosition(2);
			autoPark.park();
			assertFalse(autoPark.getParked());
		}catch(Exception e){
			fail("There should be no exceptions in this test");
		}
	}

	/*
	 * --------------------------------------------------
	 * UNPARK
	 * --------------------------------------------------
	 */
	
	@Test
    public void testUnPark(){
        AutoParkImpl autoPark = new AutoParkImpl();
        autoPark.setPosition(100);
        autoPark.setParked(true);
        autoPark.unPark();
        assertFalse(autoPark.getParked());
    }

    @Test
    public void testUnParkPersistantPos(){
        AutoParkImpl autoPark = new AutoParkImpl();
        autoPark.setPosition(100);
        autoPark.setParked(true);
        autoPark.unPark();
        assertEquals(100, autoPark.getPosition());
    }

    @Test
    public void testUnParkWhenNotParked() {
        AutoParkImpl autoPark = new AutoParkImpl();
        autoPark.setPosition(100);
        autoPark.setParked(false);
        autoPark.unPark();
        assertEquals(100, autoPark.getPosition());
        assertFalse(autoPark.getParked());
    }

    /*
	 * --------------------------------------------------
	 * Where is car (position)
	 * --------------------------------------------------
	 */

    @Test
    public void testWhereIs(){
        AutoParkImpl autoPark = new AutoParkImpl();
        autoPark.setPosition(250);
        assertEquals(autoPark.getPositionStatus(),autoPark.whereIs());
    }
}
