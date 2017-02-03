package autoParker;

import autoParker.AutoParkImpl.PositionStatus;

public interface IAutoPark {

	/*
	 * public PositionStatus moveForward();
	
	Description: Causes the car’s position to be incremented by 1. The car then reads the two sensors and registers the distance to the nearest obstacle at that position.
	
	Pre-Condition: Car must be at any position other than the last position of the street. Can can not be parked.
	
	Post-Condition: Car is one position in front of where it was before, and there is a value registered in the new position’s index on the street.
	
	Test-Cases:
	Test that the car’s position is incremented on one moveForward call.
	Test that the car is not able to move past the end of the street.
	Test that the value of the position’s index in the street is different from what it was before.
	Test that the car does nothing when it is already parked.
	*/
	
	public PositionStatus moveForward();
	
	
	/*
	 * public int isEmpty(ISensor sensor1, ISensor sensor2);
	
	Description: Records the readings from two sensors on the side of the car. Averages all 5 recordings from each sensor into one distance. Disregards a sensor if the recordings are too erratic.

	Pre-Condition: Car must have a position which is somewhere on the street (0-500). Car can not be parked.
	
	Post-Condition: A value is entered into the street array at the index matching the car’s position.
	
	Test-Cases:
	Test that an integer value is inserted into the street array when similar or same sensor readings are prepared.
	Test that an integer value is inserted into the street array when one sensor has very erratic values prepared.
	Test that a -1 is inserted into the street array when both sensors have very erratic values prepared.
	Test that the car does nothing when it is already parked.
	*/
	
	public int isEmpty(ISensor sensor1, ISensor sensor2);
	
	
	/*
	 * public PositionStatus moveBackward();
	
	Description: Causes the car’s position to be decremented by 1. The car then reads the two sensors and registers the distance to the nearest obstacle at that position.

	Pre-Condition: Car can be on any position on the street other than on position zero. Car can not be parked.
	
	Post-Condition: Car is one position behind where it was before, and there is a value registered in the new position’s index on the street.
	
	Test-Cases:
	Test that the car’s position is decremented on one moveBackward call.
	Test that the car is not able to move past the beginning of the street.
	Test that the value of the position’s index in the street is different from what it was before.
	Test that the car does nothing when it is already parked.
	*/
	
	public PositionStatus moveBackward();
	
	
	/*
	 * public void park();
	
	Description: If there is an available parking spot behind the car currently, the car executes a predetermined parking maneuver. Otherwise, the car moves forwards until it finds a parking spot, then it executes a predetermined parking maneuver.

	Pre-Condition: Car can not be parked already.
	
	Post-Condition: Car is either parked, or is on the final position on the street.
	
	Test-Cases:
	Test that the car parks when there is an empty spot behind the car.
	Test that the program does not crash when there are no parking spots on the street.
	Test that the car stops and parks when it has found an empty spot.
	Test that the program does not crash when the car is parked and attempts to run park().
	Test that the car does not find a spot without crashing when positioned on 4 or under.
	*/
	
	public void park();
	
	
	/*
	 * public void unPark();
	 
	Description: The car moves out of the parking spot and enters the position on the front-most index of the empty parking spot.

	Pre-Condition: The car must be parked.
	
	Post-Condition: The car is no longer parked, and has the same position that it had when it parked.
	
	Test-Cases:
	Test that the car is no longer parked after unPark() is ran.
	Test that parking a car and unparking it again does not affect its position.
	Test that the car does nothing if the car is not parked already.
	*/
	
	public void unPark();
	
	
	/*
	 * public int whereIs();
	
	Description: Returns the current position of the car.

	Pre-Condition: The car must have a position.
	
	Post-Condition: The position value is returned.
	Test-Cases:
	Test that the value which is returned by whereIs() matches the current position of the car.
	*/
	
	public PositionStatus whereIs();
}
