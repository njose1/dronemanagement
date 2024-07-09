package com.gpi.dronemanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gpi.dronemanagement.dto.DroneDto;
import com.gpi.dronemanagement.dto.OnRegister;
import com.gpi.dronemanagement.dto.OnUpdate;
import com.gpi.dronemanagement.service.DroneService;

import lombok.AllArgsConstructor;

/*
 * Created by Neethu Jose
 */

@AllArgsConstructor
@RestController
@RequestMapping("/dronemanagement")
public class DroneController {

	private DroneService droneService;

	/*
	 * Method to register a new drone
	 * 
	 * @param DroneDto @return DroneDto
	 */
	@PostMapping("/")
	public ResponseEntity<DroneDto> registerDrone(@Validated(OnRegister.class) @RequestBody DroneDto drone) {

		DroneDto droneDto = droneService.createDrone(drone);

		return new ResponseEntity<>(droneDto, HttpStatus.CREATED);

	}

	/*
	 * Method to get current position of drone
	 * 
	 * @param String name of drone @return DroneDto
	 */
	@GetMapping("/{name}")
	public ResponseEntity<DroneDto> getDroneByName(@PathVariable("name") String name) {

		DroneDto droneDto = droneService.getDroneByName(name);

		return new ResponseEntity<>(droneDto, HttpStatus.OK);
	}

	/*
	 * Method to update position of drone
	 * 
	 * @param DroneDto @return List<DroneDto> that represents the route of drone
	 * from current position/direction to the new position/direction
	 */
	@PutMapping("/")
	public ResponseEntity<List<DroneDto>> updatePosition(@Validated(OnUpdate.class) @RequestBody DroneDto drone) {

		List<DroneDto> droneDtos = droneService.updatePosition(drone);

		return new ResponseEntity<>(droneDtos, HttpStatus.OK);

	}

}
