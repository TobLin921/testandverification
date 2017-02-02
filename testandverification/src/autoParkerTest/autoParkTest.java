package autoParkerTest;

import org.junit.Test;

import autoParker.AutoParkImpl;
import junit.framework.Assert;

public class AutoParkTest {

	@Test
	public void testForward(){
		AutoParkImpl autoPark = new AutoParkImpl();
		autoPark.moveForward();
		Assert.assertEquals(1, autoPark.getPosition(), 0);
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testEndofStreet(){
		AutoParkImpl autoPark = new AutoParkImpl(500, false);
		autoPark.moveForward();		
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testBeginningofStreet(){
		AutoParkImpl autoPark = new AutoParkImpl();
		autoPark.moveBackward();		
	}
}
