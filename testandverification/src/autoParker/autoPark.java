package autoParker;

public class autoPark {

	private int[] street = new int[500];
	private int position;
	private int consecutiveEmpty;
	
	private void init(){
		position = 0;
		consecutiveEmpty = 0;
		
		for(int i=0;i<500;i++){
			street[i] = i+1;
		}
	}
	
	private PositionStatus moveForward(){
		
		/*
		This method moves the car 1 meter forward, queries the two infrared
		sensors through the isEmpty method described below and returns a data structure
		that contains the current position of the car,  and the situation
		of the detected parking places up to now. The car cannot be moved forward
		beyond the end of the street. 
		*/
		
		if(position<500){
			PositionStatus positionStatus = new PositionStatus();
			position += 1;
			positionStatus.position = position;
			
			if(isEmpty() >= 3){
				consecutiveEmpty += 1;
			}else{
				consecutiveEmpty = 0;
			}
			positionStatus.empty = consecutiveEmpty;
			
			return positionStatus;
		}else{
			throw new IllegalArgumentException("Car is at the end of the street!");
		}
		
	}
	
	private int isEmpty(){
		
		/*
		This method queries the two ultrasound sensors at least 5 times and filters 
		the noise in their results and returns the distance to the nearest object in 
		the right hand side. If one sensor is detected to continuously return very 
		noisy output, it should be completely disregarded.  You can use averaging 
		or any other statistical method to filter the noise from the signals received 
		from the ultrasound sensors. 
		*/
		
		return 0;
	}
	
	private void moveBackward(){
		
		/*
		The same as above; only it moves the car 1 meter backwards. The car cannot 
		be moved behind if it is already at the beginning of the street. 
		*/
		if(position >=1){
			position -= 1;
		}
	}
	
	private void park(){
		
		/*
		It moves the car to the beginning of the current 5 meter free stretch of parking 
		place, if it is already detected or moves the car forwards towards the end of the 
		street until such a stretch is detected. Then it performs a pre-programmed reverse 
		parallel parking maneuver.
		*/
		
		while(position<=500){
			if(moveForward().empty == 5){
				reverse();
			}
			
		}
	}

	private void unPark(){
		
		/*
		It moves the car forward (and to left) to front of the parking place, if it is parked. 
		*/
	}
	
	private void whereIs(){
		/*
		This method returns the current position of the car in the street as well as 
		its situation (whether it is parked or not). 
		*/
	}
	
	private void reverse() {
		//Pre-programmed reverse maneuver.	
	}
	
	class PositionStatus{
		int empty = 0;
		int position = 0;
	}
}
