package com.gpi.dronemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DroneDto {

	public DroneDto(String name, DronePosition position, String direction) {
		this.name = name;
		this.position = position;
		this.direction = Direction.valueOf(direction);
	}

	// Name of the drone should not be null or empty
	@NotBlank(groups = { OnRegister.class, OnUpdate.class })
	private String name;

	// Position of the drone should not be null or empty
	@NotNull(groups = { OnRegister.class, OnUpdate.class })
	private DronePosition position;

	// Default direction is mandatory only on registration
	@NotNull(groups = OnRegister.class)
	private Direction direction;

	public enum Direction {
		NORTH, SOUTH, WEST, EAST
	}

}
