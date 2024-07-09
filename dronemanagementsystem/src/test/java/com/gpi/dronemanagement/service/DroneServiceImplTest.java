package com.gpi.dronemanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gpi.dronemanagement.dto.DroneDto;
import com.gpi.dronemanagement.dto.DronePosition;
import com.gpi.dronemanagement.repository.DroneRepository;
import com.gpi.dronemanagement.service.impl.DroneServiceImpl;
import com.gpi.dronemanagement.entity.Drone;

class DroneServiceImplTest {

	@Mock
	private DroneRepository droneRepo;
	private DroneService droneService;
	AutoCloseable autoclose;
	DroneDto droneDto;
	Drone drone;
	List<DroneDto> droneDtos;

	@BeforeEach
	void setUp() {
		autoclose = MockitoAnnotations.openMocks(this);
		droneService = new DroneServiceImpl(droneRepo);
		drone = new Drone("testdronetest", 2, 2, "NORTH");
		droneDto = new DroneDto("testdronetest", new DronePosition(3,4),"SOUTH");
		droneDtos = new ArrayList<DroneDto>();
		droneDtos.add(droneDto);
	}

	@AfterEach
	void tearDown() throws Exception {
		autoclose.close();
	}

	@Test
	void registerDroneTest() {
		mock(DroneDto.class);
		mock(DroneRepository.class);
		mock(Drone.class);
		when(droneRepo.save(drone)).thenReturn(drone);
		assertThat(droneService.createDrone(droneDto)).isEqualTo(droneDto);
	}

	@Test
	void getDroneTest() {
		mock(DroneDto.class);
		mock(DroneRepository.class);
		mock(Drone.class);
		when(droneRepo.findById("testdronetest")).thenReturn(Optional.ofNullable(drone));
		assertThat(droneService.getDroneByName("testdronetest").getName()).isEqualTo(Optional.ofNullable(drone).get().getName());
	}

	@Test
	void updateDroneTest() {
		mock(DroneDto.class);
		mock(DroneRepository.class);
		mock(Drone.class);
		when(droneRepo.save(drone)).thenReturn(drone);
		when(droneRepo.findById("testdronetest")).thenReturn(Optional.ofNullable(drone));
		assertThat(droneService.updatePosition(droneDto).get(0).getName()).isEqualTo(droneDtos.get(0).getName());
	}

}
