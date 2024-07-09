package com.gpi.dronemanagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gpi.dronemanagement.constants.DroneConstants;
import com.gpi.dronemanagement.dto.DroneDto;
import com.gpi.dronemanagement.dto.DroneDto.Direction;
import com.gpi.dronemanagement.dto.DronePosition;
import com.gpi.dronemanagement.entity.Drone;
import com.gpi.dronemanagement.exception.DroneAlreadyExistException;
import com.gpi.dronemanagement.exception.DroneNotFoundException;
import com.gpi.dronemanagement.exception.InvalidDronePositionException;
import com.gpi.dronemanagement.mapper.DroneMapper;
import com.gpi.dronemanagement.repository.DroneRepository;
import com.gpi.dronemanagement.service.DroneService;

import lombok.AllArgsConstructor;

/*
 * Created by Neethu Jose
 */

@Service
@AllArgsConstructor
public class DroneServiceImpl implements DroneService {

	private DroneRepository droneRepo;

	/*
	 * Saves the drone's details in DB/memory
	 * 
	 * @param DroneDto
	 * 
	 * @return DroneDto
	 */
	@Override
	public DroneDto createDrone(DroneDto droneDto) {
		validateDronePosition(droneDto);
		Optional<Drone> droneObj = droneRepo.findById(droneDto.getName());
		if (droneObj.isPresent()) {
			throw new DroneAlreadyExistException("Drone is already registered in the system");
		}
		Drone drone = DroneMapper.maptoDrone(droneDto);
		droneRepo.save(drone);
		return droneDto;
	}

	/*
	 * Retrieves the drone's current position and direction from DB/memory
	 * 
	 * @param name of drone
	 * 
	 * @return DroneDto
	 */
	@Override
	public DroneDto getDroneByName(String name) {
		Drone drone = droneRepo.findById(name)
				.orElseThrow(() -> new DroneNotFoundException("Drone Not Found in the system"));

		return DroneMapper.mapToDroneDto(drone);
	}

	/*
	 * Updates drone's position in DB/memory
	 * 
	 * @param DronDto with name and position
	 * 
	 * @return List<DroneDto> which shows the full route of drone during the update
	 */
	@Override
	public List<DroneDto> updatePosition(DroneDto droneDto) {
		validateDronePosition(droneDto);
		Drone drone = droneRepo.findById(droneDto.getName())
				.orElseThrow(() -> new DroneNotFoundException("Drone Not Found in the system"));
		List<DroneDto> droneDtos = new ArrayList<DroneDto>();
		if (droneDto.getPosition().getX() == drone.getPosition_x()
				&& droneDto.getPosition().getY() == drone.getPosition_y()) {
			droneDtos.add(DroneMapper.mapToDroneDto(drone));
			return droneDtos;
		} else if (droneDto.getPosition().getX() == drone.getPosition_x()) {
			droneDtos.add(DroneMapper.mapToDroneDto(drone));
			return updatePositionForSameX(droneDtos, droneDto, drone);
		} else if (droneDto.getPosition().getY() != drone.getPosition_y()) {
			droneDtos.add(DroneMapper.mapToDroneDto(drone));
			return updatePositionForDifferentY(droneDtos, droneDto, drone);
		} else if (droneDto.getPosition().getY() == drone.getPosition_y()) {
			droneDtos.add(DroneMapper.mapToDroneDto(drone));
			return updatePositionForSameY(droneDtos, droneDto, drone);
		}
		return droneDtos;
	}

	/*
	 * Drone's x and y positions should be between the limits requested
	 * 
	 * @param DroneDto throw exception if it is not within the limits
	 */
	void validateDronePosition(DroneDto drone) {
		int x = drone.getPosition().getX();
		int y = drone.getPosition().getY();
		if (x < DroneConstants.START_VALUE_X || x > DroneConstants.END_VALUE_X || y < DroneConstants.START_VALUE_y
				|| y > DroneConstants.END_VALUE_y) {
			throw new InvalidDronePositionException(
					"Invalid drone position.Provide numeric position in x,y format and x, y values should be between 0 and 9.");
		}
	}

	/*
	 * Updates the position if current X == New X and saves the new position in
	 * DB/memory
	 * 
	 * @param List that holds the drone's route, new drone position, current drone
	 * position
	 * 
	 * @return List of drone's routes
	 */
	List<DroneDto> updatePositionForSameX(List<DroneDto> droneDtos, DroneDto droneDto, Drone drone) {

		if (droneDto.getPosition().getY() > drone.getPosition_y()) {
			droneDto.setDirection(Direction.EAST);
			drone.setDirection(Direction.EAST.toString());
		} else {
			droneDto.setDirection(Direction.WEST);
			drone.setDirection(Direction.WEST.toString());
		}
		drone.setPosition_x(droneDto.getPosition().getX());
		drone.setPosition_y(droneDto.getPosition().getY());
		droneRepo.save(drone);
		droneDtos.add(droneDto);
		return droneDtos;

	}

	/*
	 * Updates the position if current Y != New Y saves the new position in
	 * DB/memory
	 * 
	 * @param List that holds the drone's route, new drone position, current drone
	 * position
	 * 
	 * @return List of drone's routes
	 */
	List<DroneDto> updatePositionForDifferentY(List<DroneDto> droneDtos, DroneDto droneDto, Drone drone) {

		if (droneDto.getPosition().getY() > drone.getPosition_y()) {
			droneDto.setDirection(Direction.EAST);
			drone.setDirection(Direction.EAST.toString());
		} else {
			droneDto.setDirection(Direction.WEST);
			drone.setDirection(Direction.WEST.toString());
		}
		droneDtos.add(new DroneDto(drone.getName(),
				new DronePosition(drone.getPosition_x(), droneDto.getPosition().getY()), drone.getDirection()));
		if (droneDto.getPosition().getX() > drone.getPosition_x()) {
			droneDto.setDirection(Direction.SOUTH);
			drone.setDirection(Direction.SOUTH.toString());
		} else {
			droneDto.setDirection(Direction.NORTH);
			drone.setDirection(Direction.NORTH.toString());
		}
		droneDtos.add(new DroneDto(drone.getName(),
				new DronePosition(droneDto.getPosition().getX(), droneDto.getPosition().getY()), drone.getDirection()));
		drone.setPosition_x(droneDto.getPosition().getX());
		drone.setPosition_y(droneDto.getPosition().getY());
		droneRepo.save(drone);
		return droneDtos;

	}

	/*
	 * Updates the position if current Y == New Y and saves the new position in
	 * DB/memory
	 * 
	 * @param List that holds the drone's route, new drone position, current drone
	 * position
	 * 
	 * @return List of drone's routes
	 */
	List<DroneDto> updatePositionForSameY(List<DroneDto> droneDtos, DroneDto droneDto, Drone drone) {

		if (droneDto.getPosition().getX() > drone.getPosition_x()
				&& Direction.SOUTH.toString().equals(drone.getDirection())) {
			droneDto.setDirection(Direction.SOUTH);
			drone.setDirection(Direction.SOUTH.toString());
		} else if (droneDto.getPosition().getX() < drone.getPosition_x()
				&& Direction.NORTH.toString().equals(drone.getDirection())) {
			droneDto.setDirection(Direction.NORTH);
			drone.setDirection(Direction.NORTH.toString());
		} else {
			if (DroneConstants.END_VALUE_y - drone.getPosition_y() >= 1) {
				drone.setPosition_y(drone.getPosition_y() + 1);
				droneDto.setDirection(Direction.EAST);
				drone.setDirection(Direction.EAST.toString());
			} else {
				drone.setPosition_y(drone.getPosition_y() - 1);
				droneDto.setDirection(Direction.WEST);
				drone.setDirection(Direction.WEST.toString());
			}
			droneDtos.add(new DroneDto(drone.getName(), new DronePosition(drone.getPosition_x(), drone.getPosition_y()),
					drone.getDirection()));
			return updatePositionForDifferentXForSameY(droneDtos, droneDto, drone);

		}
		droneDtos.add(new DroneDto(drone.getName(),
				new DronePosition(droneDto.getPosition().getX(), droneDto.getPosition().getY()), drone.getDirection()));
		drone.setPosition_x(droneDto.getPosition().getX());
		drone.setPosition_y(droneDto.getPosition().getY());
		droneRepo.save(drone);
		return droneDtos;

	}

	/*
	 * Updates the position if current Y = New Y and current X != New X and saves
	 * the new position in DB/memory
	 * 
	 * @param List that holds the drone's route, new drone position, current drone
	 * position
	 * 
	 * @return List of drone's routes
	 */
	List<DroneDto> updatePositionForDifferentXForSameY(List<DroneDto> droneDtos, DroneDto droneDto, Drone drone) {
		if (droneDto.getPosition().getX() > drone.getPosition_x()) {
			droneDto.setDirection(Direction.SOUTH);
			drone.setDirection(Direction.SOUTH.toString());
		} else {
			droneDto.setDirection(Direction.NORTH);
			drone.setDirection(Direction.NORTH.toString());
		}
		droneDtos.add(new DroneDto(drone.getName(),
				new DronePosition(droneDto.getPosition().getX(), drone.getPosition_y()), drone.getDirection()));

		if (droneDto.getPosition().getY() > drone.getPosition_y()) {
			droneDto.setDirection(Direction.EAST);
			drone.setDirection(Direction.EAST.toString());
		} else {
			droneDto.setDirection(Direction.WEST);
			drone.setDirection(Direction.WEST.toString());
		}
		droneDtos.add(new DroneDto(drone.getName(),
				new DronePosition(droneDto.getPosition().getX(), droneDto.getPosition().getY()), drone.getDirection()));

		drone.setPosition_x(droneDto.getPosition().getX());
		drone.setPosition_y(droneDto.getPosition().getY());
		droneRepo.save(drone);
		return droneDtos;

	}
}
