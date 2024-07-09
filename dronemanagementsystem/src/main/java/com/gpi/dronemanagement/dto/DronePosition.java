package com.gpi.dronemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DronePosition {

	private int x;
	private int y;

	public DronePosition(String position) {
		String[] pos = position.split(",", 2);
		this.x = Integer.parseInt(pos[0]);
		this.y = Integer.parseInt(pos[1]);
	}

	@Override
	public String toString() {
		return x + "," + y;
	}

}
