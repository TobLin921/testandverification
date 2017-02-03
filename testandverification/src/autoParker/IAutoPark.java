package autoParker;

import autoParker.AutoParkImpl.PositionStatus;

public interface IAutoPark {

	public PositionStatus moveForward();
	
	public int isEmpty(ISensor sensor1, ISensor sensor2);
	
	public PositionStatus moveBackward();
	
	public void park();
	
	public void unPark();
	
	public PositionStatus whereIs();
}
