package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.InvoiceServiceDto;
import com.app.dto.InvoiceVehicleDto;
import com.app.dto.ServiceDto;
import com.app.pojos.BookService;
import com.app.pojos.ServiceType;
import com.app.pojos.Vehicle;
import com.app.service.IBookService;
import com.app.service.IInvoiceService;
import com.app.service.IServiceType;
import com.app.service.IVehicleService;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

	@Autowired
	private IBookService bookService;

	@Autowired
	private IServiceType servType;

	@Autowired
	private IVehicleService vehicleService;
	
	@Autowired
	private IInvoiceService invoiceService;

	@GetMapping("/getVehicle/{chassisNo}")
	public ResponseEntity<?> getVehicleDetails(@PathVariable String chassisNo) {
		// return
		// ResponseEntity.status(HttpStatus.OK).body(vehicleSer.getVehicleDetailsbyChassisNo(chassisNo));
		try {
			return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getVehicleDetailsbyChassisNo(chassisNo));
		} catch (RuntimeException e) {
			// send error mesg wrapped in RespEntity with suitable sts code (404)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/bookedServices")
	public ResponseEntity<?> getBookedServiceList() {
		return ResponseEntity.ok().body(bookService.getBookedServices());
	}

	@GetMapping("/getAllServiceTypes")
	public ResponseEntity<List<ServiceType>> getAllServiceTypes() {
		return ResponseEntity.ok().body(servType.getServiceTypeList());
	}

	@PostMapping("/addServicesToBookedService")
	public ResponseEntity<?> addServicesToBookedService(@RequestBody ServiceDto bookSer) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookService.updateService(bookSer));
	}

	@GetMapping("/bookedServiceDetails/{id}")
	public ResponseEntity<?> getBookedServiceDetails(@PathVariable Integer id) {
		return ResponseEntity.ok().body(bookService.getServiceDetails(id));
	}
	
	@PostMapping("/bookServiceInvoice/{id}")
	public ResponseEntity<?> bookServiceInvoice(@PathVariable Integer id
			) {
		System.out.println(id );
		//InvoiceVehicleDto purchased = invoiceService.purchaseVehcleInvoice(chassisNo);
		InvoiceServiceDto booked=invoiceService.bookServiceInvoice(id);
		if (booked == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Invoice Not generated Successfully");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(booked);
	}

	@GetMapping("/getBookedVehicles")
	public ResponseEntity<?> getBookedVehiclesList() {
		return ResponseEntity.status(HttpStatus.OK).body(vehicleService.listOfBookedVehicles());
	}
	
	@PostMapping("/purchaseVehicle/{chassisNo}")
	public ResponseEntity<?> purchaseBookedVehicle(@PathVariable String chassisNo) {
		Vehicle purchased = vehicleService.purchaseVehicle(chassisNo);
		if (purchased == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Vehicle not available to purchase");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Vehicle purchased ");
	}
	
	@PostMapping("/purchaseVehicleInvoice")
	public ResponseEntity<?> purchaseBookedVehicleInvoice(@RequestParam String chassisNo
			) {
		System.out.println(chassisNo );
		InvoiceVehicleDto purchased = invoiceService.purchaseVehcleInvoice(chassisNo);
		if (purchased == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Vehicle not available to purchase");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(purchased);
	}
	
	@GetMapping("/soldVehicleList")
	public ResponseEntity<?> getListOfSoldVehicles() {
		List<Vehicle> soldVehicles = vehicleService.soldVehicles();
		if (!soldVehicles.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(soldVehicles);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	@PostMapping("/addVehicleNumber")
	public ResponseEntity<?> addVehicleNumber(@RequestParam String vehicleNumber, @RequestParam String chNo) {
		int added = vehicleService.addVehicleNumber(vehicleNumber, chNo);
		if (added == 1) {
			return ResponseEntity.status(HttpStatus.OK).body("no added");
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

}
