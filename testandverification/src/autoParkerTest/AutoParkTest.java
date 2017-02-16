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
		assertEquals(1, autoPark.getPosition());
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testEndofStreet(){
		TestSensor testSensor = new TestSensor();
		AutoParkImpl autoPark = new AutoParkImpl(500, false, testSensor, testSensor);
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
		assertNotSame(autoPark.getStreetValue(autoPark.getPosition()), readingBefore);
	}
	
	@Test
	public void testForwardParked(){
		AutoParkImpl autoPark = new AutoParkImpl();
		int positionBefore = autoPark.getPosition();
		autoPark.setParked(true);
		autoPark.moveForward();
		int positionAfter = autoPark.getPosition();
		assertEquals(positionBefore, positionAfter);
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
		assertEquals(4, autoPark.getStreetValue(autoPark.getPosition()));
	}
	
	@Test
	public void testIsEmptySensorOneOnly(){
		AutoParkImpl autoPark = new AutoParkImpl();
		int[] readings = {3, 3, 3, 3, 3};
		int[] readings2 = {1, 150, 300, 500, 1000};
		autoPark.setSensorValues(1, readings);
		autoPark.setSensorValues(2, readings2);
		autoPark.moveForward();
		assertEquals(3, autoPark.getStreetValue(autoPark.getPosition()));
	}
	
	@Test
	public void testIsEmptyNoSensor(){
		AutoParkImpl autoPark = new AutoParkImpl();
		int[] readings = {1, 200, 500, 1000, 2000};
		autoPark.setSensorValues(1, readings);
		autoPark.setSensorValues(2, readings);
		autoPark.moveForward();
		assertEquals(-1, autoPark.getStreetValue(autoPark.getPosition()));
	}
	
	@Test
	public void testIsEmptyParked(){
		AutoParkImpl autoPark = new AutoParkImpl();
		int readingBefore = autoPark.getStreetValue(autoPark.getPosition());
		autoPark.setParked(true);
		autoPark.moveForward();
		int readingAfter = autoPark.getStreetValue(autoPark.getPosition());
		assertEquals(readingBefore, readingAfter);
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
		assertEquals(99, autoPark.getPosition());
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
		assertNotSame(autoPark.getStreetValue(autoPark.getPosition()), readingBefore);
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
		assertEquals(positionBefore, positionAfter);
	}
	
	/*
	 * --------------------------------------------------
	 * PARK
	 * --------------------------------------------------
	 */
	
	@Test
	public void testParkWithSpot(){
		AutoParkImpl autoPark = new AutoParkImpl();
		autoPark.setPosition(49);
		int currentPos = autoPark.getPosition();
		autoPark.setStreetValue(50, 5);
		autoPark.setStreetValue(49, 7);
		autoPark.setStreetValue(48, 5);
		autoPark.setStreetValue(47, 10);
		autoPark.setStreetValue(46, 12);
		autoPark.park();
		assertTrue(autoPark.getParked());
	}
	
	@Test
	public void testParkWithNoSpot(){
		AutoParkImpl autoPark = new AutoParkImpl();
		autoPark.setPosition(50);
		autoPark.park();
		assertFalse(autoPark.getParked());
	}
	
	@Test
	public void testParkForwardSpot(){
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
	}
	
	@Test
	public void testParkWhenParked(){
		AutoParkImpl autoPark = new AutoParkImpl();
		autoPark.setParked(true);
		autoPark.setPosition(100);
		int positionBefore = autoPark.getPosition();
		autoPark.park();
		int positionAfter = autoPark.getPosition();
		assertEquals(positionBefore, positionAfter);
	}
	
	@Test
	public void testParkWhenUnderFour(){
		AutoParkImpl autoPark = new AutoParkImpl();
		autoPark.setPosition(2);
		autoPark.park();
		assertFalse(autoPark.getParked());
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
    
    /*
	 * --------------------------------------------------
	 * Mock
	 * --------------------------------------------------
	 */

    class StackAnswer<T> implements Answer<T>{
    	private Stack<T> stack = new Stack<T>();
		StackAnswer(Stack<T> stack){
    		this.stack = stack;
    	}
		
		public T answer(InvocationOnMock inv){
    		return stack.pop();
    	}
    }
    
    @Test
    public void mockTest(){
    	ISensor mockSensor = mock(ISensor.class);
    	Stack<Integer> dists = new Stack<Integer>();
    	for(int i=0;i<10;i++){
    		dists.push(-1);
    	}
    	for(int i=0;i<40;i++){
    		dists.push(2);
    	}
    	for(int i=0;i<25;i++){
    		dists.push(6);
    	}
    	for(int i=0;i<2420;i++){
    		dists.push(2);
    	}
    	
    	ISensor mockBrokenSensor = mock(ISensor.class);
    	Stack<Integer> distsBroken = new Stack<Integer>();
    	for(int i=0;i<10;i++){
    		distsBroken.push(-1);
    	}
    	for(int i=0;i<40;i++){
    		distsBroken.push(2);
    	}
    	for(int i=0;i<25;i++){
    		distsBroken.push(6);
    	}
    	for(int i=0;i<1000;i++){
    		distsBroken.push(2);
    	}
    	for(int i=0;i<1420;i++){
    		distsBroken.push(-1);
    	}
    	
    	Answer<Integer> answer = new StackAnswer<Integer>(dists);
    	when(mockSensor.record()).thenAnswer(answer);
    	
    	AutoParkImpl autoPark = new AutoParkImpl(mockSensor, mockBrokenSensor);
    	AutoParkImpl spyAutoPark = spy(autoPark);
    	
    	for(int i=0;i<499;i++){
    		autoPark.moveForward();
    	}
    	
    }

}
