package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.pojos.Customer;
import com.app.pojos.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {

	@Query(value = "select v from Vehicle v where v.bookedStatus = false and v.purchasedStatus = false")
	List<Vehicle> getListOfAvailableVehicle();
	
	@Query(value = "select distinct v from Vehicle v where v.modelName = :name and v.color = :color and v.bookedStatus = false and v.purchasedStatus = false")
	List<Vehicle> bookingVehicle(String name, String color);

	Vehicle findByCustomer(Customer customer);

	Vehicle findByChassisNo(String chassisNo);
	
	@Query(value = "select v from Vehicle v where v.bookedStatus = true and v.purchasedStatus = false")
	List<Vehicle> getListOfBookedVehicle();
	
	@Query(value = "select v from Vehicle v where v.purchasedStatus = true")
	List<Vehicle> soldVehicles();
	
	@Modifying
	@Query(value = "update Vehicle v set v.vehicleNo = :vehicleNumber where v.chassisNo = :chNo")
	int addVehicleNumber(@Param("vehicleNumber") String vehicleNumber, @Param("chNo") String chNo);
}
