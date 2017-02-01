package autoParker;

import autoParker.AutoPark.IllegalActionException;

public class AutoPark {

	private int[] street;
	private int position;
	private boolean consecutiveEmpty;
	private boolean isParked;
	
	public AutoPark(){
		street = new int[500];
		position = 0;
		consecutiveEmpty = false;
		isParked = false;
	}
	
	public AutoPark(int position, boolean empty){
		street = new int[500];
		this.position = position;
		this.consecutiveEmpty = empty;
		isParked = false;
	}
	
	public PositionStatus moveForward(){
		
		/*
		This method moves the car 1 meter forward, queries the two infrared
		sensors through the isEmpty method described below and returns a data structure
		that contains the current position of the car,  and the situation
		of the detected parking places up to now. The car cannot be moved forward
		beyond the end of the street. 
		*/
		
		PositionStatus positionStatus = new PositionStatus();
		street[position+1] = isEmpty();
		position += 1;
		positionStatus.position = position;
		positionStatus.empty = checkIfEmpty(positionStatus.position);		
		return positionStatus;	
	}
	
	public int isEmpty(){
		
		/*
		This method queries the two ultrasound sensors at least 5 times and filters 
		the noise in their results and returns the distance to the nearest object in 
		the right hand side. If one sensor is detected to continuously return very 
		noisy output, it should be completely disregarded.  You can use averaging 
		or any other statistical method to filter the noise from the signals received 
		from the ultrasound sensors. 
		*/
		
		readSensor(1);
		
		return 0;
	}

	public PositionStatus moveBackward(){
		
		/*
		The same as above; only it moves the car 1 meter backwards. The car cannot 
		be moved behind if it is already at the beginning of the street. 
		*/
		
		PositionStatus positionStatus = new PositionStatus();
		street[position-1] = isEmpty();
		position -= 1;
		positionStatus.position = position;
		positionStatus.empty = checkIfEmpty(positionStatus.position);		
		return positionStatus;	
		
	}

	public void park(){
		
		/*
		It moves the car to the beginning of the current 5 meter free stretch of parking 
		place, if it is already detected or moves the car forwards towards the end of the 
		street until such a stretch is detected. Then it performs a pre-programmed reverse 
		parallel parking maneuver.
		*/
		
		if(consecutiveEmpty == true){
			reverse();
			isParked = true;
		}else{
			while(position<500){
				if(moveForward().empty == true){
					reverse();
					isParked = true;
				}
				
			}
		}
	}

	public void unPark(){
		
		/*
		It moves the car forward (and to left) to front of the parking place, if it is parked. 
		*/
		if(isParked == true){
			isParked = false;
		}else
			System.err.print("Car is already parked!");
	}
	
	public void whereIs(){
		/*
		This method returns the current position of the car in the street as well as 
		its situation (whether it is parked or not). 
		*/
	}
	
	public void reverse() {
		//Pre-programmed reverse maneuver.	
	}
	
	public int readSensor(int sensor) {
		int sensornr = sensor;
		
		return 5;
	}
	
	class PositionStatus{
		boolean empty = false;
		int position = 0;
	}

	public int getPosition() {
		return this.position;
	}

	public void setPosition(int position) {
		this.position = position;	
	}
	
	private boolean checkIfEmpty(int position) {
		int consecutiveEmpty = 0;
		
		for(int i=position;i<position-5;i--){
			if(street[position-1]>=3){
				consecutiveEmpty += 1;
			}
		}
		if(consecutiveEmpty == 5){
			return true;
		}else{
			return false;
		}
	}
	
	public class IllegalActionException extends Exception{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public IllegalActionException(){
			
		}
		
		public IllegalActionException(String string){
			super(string);
		}
	}
}
