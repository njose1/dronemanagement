package com.gpi.dronemanagement.service;

import java.util.List;

import com.gpi.dronemanagement.dto.DroneDto;

public interface DroneService {
	
	DroneDto createDrone(DroneDto drone);
	DroneDto getDroneByName(String name);
	List<DroneDto> updatePosition(DroneDto drone);

}
