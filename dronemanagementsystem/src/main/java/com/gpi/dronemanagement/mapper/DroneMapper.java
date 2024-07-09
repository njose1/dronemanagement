package com.gpi.dronemanagement.mapper;

import com.gpi.dronemanagement.dto.DroneDto;
import com.gpi.dronemanagement.dto.DronePosition;
import com.gpi.dronemanagement.entity.Drone;

public class DroneMapper {

	//Converts drone JPA Entity to DroneDto
	public static DroneDto mapToDroneDto(Drone drone) {

		DroneDto droneDto = new DroneDto(drone.getName(), new DronePosition(drone.getPosition_x(),drone.getPosition_y()),
				drone.getDirection());

		return droneDto;

	}

	//Converts DroneDto into drone JPA entity
	public static Drone maptoDrone(DroneDto droneDto) {		
		Drone drone = new Drone(droneDto.getName(), droneDto.getPosition().getX(), droneDto.getPosition().getY(),
				droneDto.getDirection());
		return drone;
	}

}
