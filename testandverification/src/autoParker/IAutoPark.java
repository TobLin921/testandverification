package autoParker;

import autoParker.AutoParkImpl.PositionStatus;

public interface IAutoPark {

	public PositionStatus moveForward(PositionStatus positionStatus);
	
	public int isEmpty();
	
	public PositionStatus moveBackward(PositionStatus positionStatus);
	
	public void park(PositionStatus positonStatus);
	
	public void unPark();
	
	public void whereIs();
}
