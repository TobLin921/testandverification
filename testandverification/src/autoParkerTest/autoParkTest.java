package autoParkerTest;

import org.junit.Test;

import autoParker.AutoPark;
import autoParker.AutoPark.IllegalActionException;
import junit.framework.Assert;

public class AutoParkTest {

	@Test
	public void testForward(){
		AutoPark autoPark = new AutoPark();
		autoPark.moveForward();
		Assert.assertEquals(1, autoPark.getPosition(), 0);
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testEndofStreet(){
		AutoPark autoPark = new AutoPark();
		autoPark.setPosition(500);
		autoPark.moveForward();		
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testBeginningofStreet(){
		AutoPark autoPark = new AutoPark();
		autoPark.moveBackward();		
	}
}
