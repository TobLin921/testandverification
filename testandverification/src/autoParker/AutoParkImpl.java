package autoParker;

public class AutoParkImpl implements IAutoPark {

	private int[] street;
	//private int position;
	//private boolean consecutiveEmpty;
	private boolean isParked;
	private PositionStatus positionStatus = new PositionStatus();
	private TestSensor sensorFront = new TestSensor();
	private TestSensor sensorBack = new TestSensor();
	private final int ACCEPTABLE_DEVIATION = 5;
	
	public AutoParkImpl(){
		street = new int[500];
		//position = 0;
		//consecutiveEmpty = false;
		isParked = false;
		positionStatus.empty = false;
		positionStatus.position = 0;
		int[] defaultValues = {0, 0, 0, 0, 0};
		sensorFront.prepare(defaultValues);
		sensorBack.prepare(defaultValues);
		
	}
	
	public AutoParkImpl(int position, boolean empty){
		street = new int[500];
		//this.position = position;
		//this.consecutiveEmpty = empty;
		isParked = false;
		positionStatus.empty = empty;
		positionStatus.position = position-1;
	}
	
	public PositionStatus moveForward(){
		
		/*
		This method moves the car 1 meter forward, queries the two infrared
		sensors through the isEmpty method described below and returns a data structure
		that contains the current position of the car,  and the situation
		of the detected parking places up to now. The car cannot be moved forward
		beyond the end of the street. 
		*/
		
		if(isParked == false){
			street[positionStatus.position+1] = isEmpty(sensorFront, sensorBack);
			positionStatus.position += 1;
			positionStatus.empty = checkIfEmpty(positionStatus.position);		
			return positionStatus;
		}else{
			return positionStatus;
		}
	}
	
	public int isEmpty(ISensor sensor1, ISensor sensor2){
		
		/*
		This method queries the two ultrasound sensors at least 5 times and filters 
		the noise in their results and returns the distance to the nearest object in 
		the right hand side. If one sensor is detected to continuously return very 
		noisy output, it should be completely disregarded.  You can use averaging 
		or any other statistical method to filter the noise from the signals received 
		from the ultrasound sensors. 
		*/
		
		int[] sensorReadings1 = {sensor1.record(), sensor1.record(), 
		                         sensor1.record(), sensor1.record(), sensor1.record()}; 
		                         
		int[] sensorReadings2 = {sensor2.record(), sensor2.record(), 
                				 sensor2.record(), sensor2.record(), sensor2.record()};
        
		int [] combinedReadings = new int[sensorReadings1.length+sensorReadings2.length];
		System.arraycopy( sensorReadings1, 0, combinedReadings, 0, sensorReadings1.length);
		System.arraycopy( sensorReadings2, 0, combinedReadings, sensorReadings1.length, sensorReadings2.length );
		
		
		if(getDeviation(sensorReadings1) > ACCEPTABLE_DEVIATION  && 
				getDeviation(sensorReadings2) <= ACCEPTABLE_DEVIATION){
			return getAverage(sensorReadings2);
		}else if(getDeviation(sensorReadings1) <= ACCEPTABLE_DEVIATION && 
				getDeviation(sensorReadings2) > ACCEPTABLE_DEVIATION){
			return getAverage(sensorReadings1);
		}else if(getDeviation(sensorReadings1) <= ACCEPTABLE_DEVIATION && 
				getDeviation(sensorReadings2) <= ACCEPTABLE_DEVIATION){
			return getAverage(combinedReadings);
		}else{
			return -1;
		}
	}

	private int getAverage(int[] array) {
		
		int sensorSum = 0;
		
		for(int i:array){
			sensorSum += i;
		}
		int totalAverage = 0;
		totalAverage = sensorSum/array.length;
		return totalAverage;
	}

	private int getDeviation(int[] array) {
		
		int deviationSum = 0;
		for(int i:array){
			deviationSum += (int)Math.pow((i-getAverage(array)), 2);
		}
		int deviation = (int)Math.sqrt(deviationSum/array.length);
		
		return deviation;
	}

	public PositionStatus moveBackward(){
		
		/*
		The same as above; only it moves the car 1 meter backwards. The car cannot 
		be moved behind if it is already at the beginning of the street. 
		*/
		if(isParked == false){
			street[positionStatus.position-1] = isEmpty(sensorFront, sensorBack);
			positionStatus.position -= 1;
			positionStatus.empty = checkIfEmpty(positionStatus.position);		
			return positionStatus;
		}else{
			return positionStatus;
		}
	}

	public void park(){
		
		/*
		It moves the car to the beginning of the current 5 meter free stretch of parking 
		place, if it is already detected or moves the car forwards towards the end of the 
		street until such a stretch is detected. Then it performs a pre-programmed reverse 
		parallel parking maneuver.
		*/
		if(isParked == false){
			if(positionStatus.empty == true){
				reverse();
				isParked = true;
			}else{
				while(positionStatus.position<499){
					if(moveForward().empty == true){
						reverse();
						isParked = true;
					}
				}
				if(isParked == false){
					System.err.print("There are no possible places to park.");
				}
			}
		}else{
			System.err.print("Car is already parked.");
		}
	}

	public void unPark(){
		
		/*
		It moves the car forward (and to left) to front of the parking place, if it is parked. 
		*/
		if(isParked == true){
			isParked = false;
		}else
			System.err.print("Car is not parked already.");
	}
	
	public int whereIs(){
		/*
		This method returns the current position of the car in the street as well as 
		its situation (whether it is parked or not). 
		*/
		
		return positionStatus.position;
	}
	
	public void reverse() {
		//Pre-programmed reverse maneuver.	
	}
	
	class PositionStatus{
		boolean empty = false;
		int position = 0;
	}
	
	class TestSensor implements ISensor{
		
		int[] sensorReadings = {0, 0, 0, 0, 0};
		int i = 0;
		
		public void prepare(int[] sensorReadings){
			i = 0;
			this.sensorReadings = sensorReadings;
		}
		
		public int record(){
			int tmp = sensorReadings[i];
			if(i <= 3){
				i++;
			}else{
				i = 0;
			}
			return tmp;
		}
	}

	public void setSensorValues(int sensor, int[] sensorValues){
		if(sensor == 1){
			sensorFront.prepare(sensorValues);
		}else if(sensor == 2){
			sensorBack.prepare(sensorValues);
		}
	}
	
	public int getPosition() {
		return this.positionStatus.position;
	}

	public void setPosition(int position) {
		this.positionStatus.position = position;	
	}
	
	public PositionStatus getPositionStatus(){
		return this.positionStatus;
	}
	
	private boolean checkIfEmpty(int position) {
		int consecutiveEmpty = 0;
		
		if(position <= 4){
			return false;
		}else{
			for(int i=position;i>position-5;i--){
				if(street[i]>=3){
					consecutiveEmpty += 1;
				}
			}
			if(consecutiveEmpty == 5){
				return true;
			}else{
				return false;
			}
		}
	}

	public void setParked(boolean b) {
		this.isParked = b;
		
	}

	public int getStreetValue(int position) {
		return street[position];
	}

	public void setStreetValue(int position, int value) {
		street[position] = value;
	}

	public boolean getParked() {
		return isParked;
	}
	
	/*
	public class IllegalActionException extends Exception{
		
		
		private static final long serialVersionUID = 1L;

		public IllegalActionException(){
			
		}
		
		public IllegalActionException(String string){
			super(string);
		}
	}
	*/
}
