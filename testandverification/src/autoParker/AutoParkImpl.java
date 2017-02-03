package autoParker;

public class AutoParkImpl implements IAutoPark {

	private int[] street;
	private boolean isParked;
	private boolean useSensors;
	private PositionStatus positionStatus = new PositionStatus();
	private TestSensor sensorFront = new TestSensor();
	private TestSensor sensorBack = new TestSensor();
	private final int ACCEPTABLE_DEVIATION = 5;
	
	
	public AutoParkImpl(){
		street = new int[500];
		isParked = false;
		useSensors = true;
		positionStatus.empty = false;
		positionStatus.position = 0;
		int[] defaultValues = {0, 0, 0, 0, 0};
		sensorFront.prepare(defaultValues);
		sensorBack.prepare(defaultValues);
		
	}
	
	public AutoParkImpl(int position, boolean empty){
		street = new int[500];
		isParked = false;
		useSensors = true;
		positionStatus.empty = empty;
		positionStatus.position = position-1;
	}
	
	public PositionStatus moveForward(){
		
		if(isParked == false){
			if(useSensors == true){
				street[positionStatus.position+1] = isEmpty(sensorFront, sensorBack);
			}
			positionStatus.position += 1;
			positionStatus.empty = checkIfEmpty(positionStatus.position);
			return positionStatus;
		}else{
			return positionStatus;
		}
	}
	
	public int isEmpty(ISensor sensor1, ISensor sensor2){
		
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
		
		if(isParked == false){
			if(useSensors == true){
			street[positionStatus.position-1] = isEmpty(sensorFront, sensorBack);
			}
			positionStatus.position -= 1;
			positionStatus.empty = checkIfEmpty(positionStatus.position);		
			return positionStatus;
		}else{
			return positionStatus;
		}
	}

	public void park(){
		
		if(isParked == false){
			if(positionStatus.empty == true){
				reverse();
				isParked = true;
			}else if(isParked == false){
				while(positionStatus.position<499 && isParked == false){
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
		
		if(isParked == true){
			isParked = false;
		}else
			System.err.print("Car is not parked already.");
	}
	
	public PositionStatus whereIs(){
		
		return positionStatus;
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
			for(int i=position-1;i>position-6;i--){
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
	
	public void setUseSensors(boolean b){
		useSensors = b;
	}
}
