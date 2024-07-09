package com.gpi.dronemanagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.gpi.dronemanagement.entity.Drone;

public interface DroneRepository extends CrudRepository<Drone, String> {

}
