package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.VehicleModel;

public interface VehicleModelRepository extends JpaRepository<VehicleModel, Integer> {

	VehicleModel findByModelName(String modelName);

}
