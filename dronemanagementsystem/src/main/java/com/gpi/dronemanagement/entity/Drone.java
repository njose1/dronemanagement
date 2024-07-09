package com.gpi.dronemanagement.entity;

import com.gpi.dronemanagement.dto.DroneDto.Direction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drones")
public class Drone {

	public Drone(String name, int x, int y, Direction direction) {
		this.name = name;
		this.position_x = x;
		this.position_y = y;
		this.direction = direction.toString();
	}

	@Id
	private String name;

	@Column(nullable = false)
	private int position_x;

	@Column(nullable = false)
	private int position_y;

	@Column(nullable = false)
	private String direction;

}
