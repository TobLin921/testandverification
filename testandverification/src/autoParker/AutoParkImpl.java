package autoParker;

public class AutoParkImpl implements IAutoPark{

	private int[] street;
	private boolean useSensors;
	private PositionStatus positionStatus = new PositionStatus();
	private ISensor sensorFront;
	private ISensor sensorBack;
	private final int ACCEPTABLE_DEVIATION = 5;
	
	
	public AutoParkImpl(){
		street = new int[500];
		useSensors = true;
		positionStatus.empty = false;
		positionStatus.position = 0;
		int[] defaultValues = {0, 0, 0, 0, 0};
		sensorFront = new TestSensor();
		((TestSensor) sensorFront).prepare(defaultValues);
		sensorBack = new TestSensor();
		((TestSensor) sensorBack).prepare(defaultValues);
	}
	
	public AutoParkImpl(ISensor sensor1, ISensor sensor2){
		street = new int[500];
		useSensors = true;
		positionStatus.empty = false;
		positionStatus.position = 0;
		sensorFront = sensor1;
		sensorBack = sensor2;
	}
	
	
	public AutoParkImpl(int position, boolean empty, ISensor sensor1, ISensor sensor2){
		street = new int[500];
		useSensors = true;
		positionStatus.empty = empty;
		positionStatus.position = position;
		sensorFront = sensor1;
		sensorBack = sensor2;
	}
	
	public PositionStatus moveForward() throws OffStreetException{
		
		if(positionStatus.parked == false){
			
			if(useSensors == true){
				try{
					street[positionStatus.position+1] = isEmpty(sensorFront, sensorBack);
				}catch(ArrayIndexOutOfBoundsException e){
					throw new OffStreetException();
				}
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
		
		
		if(getAverage(sensorReadings1) == -1 ||
			getDeviation(sensorReadings1) > ACCEPTABLE_DEVIATION  && 
			getDeviation(sensorReadings2) <= ACCEPTABLE_DEVIATION){
			return getAverage(sensorReadings2);
		}else if(getAverage(sensorReadings2) == -1 ||
				getDeviation(sensorReadings1) <= ACCEPTABLE_DEVIATION && 
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

	public PositionStatus moveBackward() throws OffStreetException{
		
		if(positionStatus.parked == false){
			if(useSensors == true){
				try{
					street[positionStatus.position-1] = isEmpty(sensorFront, sensorBack);
				}catch(ArrayIndexOutOfBoundsException e){
					throw new OffStreetException();
				}
			}
			positionStatus.position -= 1;
			positionStatus.empty = checkIfEmpty(positionStatus.position);		
			return positionStatus;
		}else{
			return positionStatus;
		}
	}

	public void park() throws OffStreetException{
		
		if(positionStatus.parked == false){
			if(checkIfEmpty(positionStatus.position) == true){
				reverse();
				positionStatus.parked = true;
			}else if(positionStatus.parked == false){
				while(positionStatus.position<499 && positionStatus.parked == false){
					moveForward();
					if(positionStatus.empty == true){
						reverse();
						positionStatus.parked = true;
					}
				}
				if(positionStatus.parked == false){
					System.err.print("There are no possible places to park. \n\n");
				}
			}
		}else{
			System.err.print("Car is already parked.\n\n");
		}
	}

	public void unPark(){
		
		if(positionStatus.parked == true){
			positionStatus.parked = false;
		}else
			System.err.print("Car is not parked already.\n\n");
	}
	
	public PositionStatus whereIs(){
		
		return positionStatus;
	}
	
	public void reverse() {
		//Pre-programmed reverse maneuver.	
	}
	
	class PositionStatus{
		boolean parked = false;
		boolean empty = false;
		int position = 0;
	}

	public void setSensorValues(int sensor, int[] sensorValues){
		if(sensor == 1){
			((TestSensor) sensorFront).prepare(sensorValues);
		}else if(sensor == 2){
			((TestSensor) sensorBack).prepare(sensorValues);
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
		this.positionStatus.parked = b;
		
	}

	public int getStreetValue(int position) {
		return street[position];
	}

	public void setStreetValue(int position, int value) {
		street[position] = value;
	}

	public boolean getParked() {
		return positionStatus.parked;
	}
	
	public void setUseSensors(boolean b){
		useSensors = b;
	}
	
	public class OffStreetException extends Exception {
	    /**
		 * 
		 */
		private static final long serialVersionUID = -326199579566464032L;

		public OffStreetException() {
	        super("Movement halted to avoid driving off street.");
	    }
	}
	
}
