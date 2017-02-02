package autoParkerTest;

import org.junit.Test;

import autoParker.AutoParkImpl;
import junit.framework.Assert;

public class AutoParkTest {

	@Test
	public void testForward(){
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
		int valueBefore = autoPark.getPosition();
		int[] readings = {2, 2, 2, 2, 2};
		autoPark.setSensorValues(1, readings);
		autoPark.setSensorValues(2, readings);
		autoPark.moveForward();
		Assert.assertNotSame(autoPark.getPosition(), valueBefore);
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
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testBeginningofStreet(){
		AutoParkImpl autoPark = new AutoParkImpl();
		autoPark.moveBackward();		
	}
}
