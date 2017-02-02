package autoParker;

import autoParker.AutoParkImpl.PositionStatus;

public interface IAutoPark {

	public PositionStatus moveForward();
	
	public int isEmpty();
	
	public PositionStatus moveBackward();
	
	public void park();
	
	public void unPark();
	
	public void whereIs();
}
