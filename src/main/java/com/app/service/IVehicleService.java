package com.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.pojos.Vehicle;

public interface IVehicleService {

	Vehicle getVehicleDetailsbyChassisNo(String ChassisNo);

	List<Vehicle> listOfVehicles();

//	Vehicle addVehicleDetails(Vehicle veh, MultipartFile image) throws IOException;

	Vehicle updateVehicleDetails(Vehicle veh);

	String DeleteVehicleDetails(String chassisNo);
	// customer flow
	List<Vehicle> getAvailableVehicle();

	String addVehiclesList(MultipartFile vehicleList) throws IOException;

	String checkAvailabilityForBooking(String modelName, String color);

	Vehicle bookVehicle(String modelName, String color, Integer custId);

	List<Vehicle> listOfBookedVehicles();

	Vehicle purchaseVehicle(String chassisNo);
	
	List<Vehicle> soldVehicles();

	int addVehicleNumber(String vehicleNumber, String chNo);
}
