package autoParkerTest;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Stack;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import autoParker.AutoParkImpl;
import autoParker.ISensor;

public class AutoParkIntegrationTest {

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
    public void integrationTestOne(){
    	try{
	    	ISensor mockSensor = mock(ISensor.class);
	    	Stack<Integer> dists = new Stack<Integer>();
	    	for(int i=0;i<2420;i++){
	    		dists.push(2);
	    	}
	    	for(int i=0;i<25;i++){
	    		dists.push(6);
	    	}
	    	for(int i=0;i<40;i++){
	    		dists.push(2);
	    	}
	    	for(int i=0;i<10;i++){
	    		dists.push(-1);
	    	}

	    	ISensor mockBrokenSensor = mock(ISensor.class);
	    	Stack<Integer> distsBroken = new Stack<Integer>();
	    	for(int i=0;i<1420;i++){
	    		distsBroken.push(-1);
	    	}
	    	for(int i=0;i<1000;i++){
	    		distsBroken.push(2);
	    	}
	    	for(int i=0;i<25;i++){
	    		distsBroken.push(6);
	    	}
	    	for(int i=0;i<40;i++){
	    		distsBroken.push(-1);
	    	}
	    	for(int i=0;i<10;i++){
	    		distsBroken.push(-1);
	    	}

	    	Answer<Integer> answer = new StackAnswer<Integer>(dists);
	    	when(mockSensor.record()).thenAnswer(answer);
	    	
	    	AutoParkImpl autoPark = new AutoParkImpl(mockSensor, mockBrokenSensor);
	    	AutoParkImpl spyAutoPark = spy(autoPark);
	    	
	    	spyAutoPark.park();
	    	verify(spyAutoPark, times(15)).moveForward();
    	}catch(Exception e){
    		fail("There should be no exceptions in this test");
    	}
    	
    }

	
}
