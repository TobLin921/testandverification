package autoParker;

public class TestSensor implements ISensor{
	
	int[] sensorReadings = {0, 0, 0, 0, 0};
	int i = 0;
	
	public void prepare(int[] sensorReadings){
		i = 0;
		this.sensorReadings = sensorReadings;
	}
	
	public Integer record(){
		int tmp = sensorReadings[i];
		if(i <= 3){
			i++;
		}else{
			i = 0;
		}
		return tmp;
	}
}