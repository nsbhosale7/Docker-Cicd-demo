package com.app.controller;

import java.io.IOException;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.app.dto.ServiceDto;
import com.app.dto.VehicleModelDto;
import com.app.pojos.Admin;
import com.app.pojos.BookService;
import com.app.pojos.Customer;
import com.app.pojos.Employee;
import com.app.pojos.ServiceType;
import com.app.pojos.Vehicle;
import com.app.pojos.VehicleModel;
import com.app.service.IAdminService;
import com.app.service.IBookService;
import com.app.service.ICustomerService;
import com.app.service.IEmployeeService;
import com.app.service.IInvoiceService;
import com.app.service.IServiceType;
import com.app.service.IVehicleModelService;
import com.app.service.IVehicleService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
	@Autowired
	private IAdminService adminSer;

	@Autowired
	private IVehicleService vehicleSer;

	@Autowired
	private IEmployeeService employeeSer;

	@Autowired
	private ICustomerService customerSer;

	@Autowired
	private IBookService bookSer;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private IServiceType serviceType;

	@Autowired
	private IVehicleModelService vehicleModelser;
	
	@Autowired
	private IVehicleService vehicleService;
	
	@Autowired
	private IInvoiceService invoiceService;

	@Value("${file.upload.location}")
	private String location;

	@GetMapping
	public ResponseEntity<?> testMe() {
		System.out.println("in test me");

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(adminSer.listAdmin());
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginAdmin(@RequestBody Admin trasAdmin) {
		System.out.println(trasAdmin.getEmail());
		System.out.println(adminSer.loginAdmin(trasAdmin.getEmail(), trasAdmin.getPassword()));
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(adminSer.loginAdmin(trasAdmin.getEmail(), trasAdmin.getPassword()));
	}

	@GetMapping("/vehicleList")
	public ResponseEntity<?> listVehicles() {
		System.out.println("in admin vehicle List");
		return ResponseEntity.status(HttpStatus.OK).body(vehicleSer.listOfVehicles());
	}

//	@PostMapping("/addVehicle")
//	public ResponseEntity<?> addVehicle(@RequestBody Vehicle vh1) {
//		Vehicle veh = vehicleSer.addVehicleDetails(vh1);
//
//		if (veh == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//		}
//		return ResponseEntity.status(HttpStatus.CREATED).body(veh);
//	}

//	@PostMapping("/addVehicleWithImage")
//	public ResponseEntity<?> addVehicleWithImage(@RequestParam String vh1, @RequestParam MultipartFile image)
//			throws IOException {
////	Vehicle veh = vehicleSer.addVehicleDetails(vh1);
//		System.out.println("inside add vehicle method");
//		Vehicle veh = mapper.readValue(vh1, Vehicle.class);
//		System.out.println("after mapping");
//		if (veh == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//		}
//		return ResponseEntity.status(HttpStatus.CREATED).body(vehicleSer.addVehicleDetails(veh, image));
//	}

	@PostMapping("/addModelWithImage")
	public ResponseEntity<?> addVehicleModelWithImage(@RequestParam String vh1, @RequestParam MultipartFile image)
			throws IOException {
//	//Vehicle veh = vehicleSer.addVehicleDetails(vh1);
		System.out.println("inside add Model method");
		VehicleModelDto veh = mapper.readValue(vh1, VehicleModelDto.class);
		System.out.println("after mapping");
		if (veh == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(vehicleModelser.addVehicleModelDetails(veh, image));
	}

	@GetMapping("/getModelList")
	public ResponseEntity<?> getVehicleModelList() {
		return ResponseEntity.status(HttpStatus.OK).body(vehicleModelser.getListOfAvailableModel());
	}

	@PutMapping("/updateVehicleModel")
	public ResponseEntity<?> updateVehicleModel(@RequestBody VehicleModel vehicleModel){
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(vehicleModelser.updateVehicleModel(vehicleModel));
	}
	
	// Vehicle model Mappings Methods
	@GetMapping("/getVehicleModel/{id}")
	public ResponseEntity<?> getVehicleModelDetails(@PathVariable Integer id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(vehicleModelser.getVehicleModelDetails(id));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/deleteVehicleModel/{id}")
	public ResponseEntity<?> deleteVehicleModel(@PathVariable Integer id){
		try {
		return ResponseEntity.status(HttpStatus.OK).body(vehicleModelser.deleteVehicleModel(id));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping("/addBulkVehicles")
	public ResponseEntity<?> addBulkVehicles(@RequestParam MultipartFile vehicleList) throws IOException {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(vehicleSer.addVehiclesList(vehicleList));
	}

	@PutMapping("/updateVehicle")
	public Vehicle updateVehicle(@RequestBody Vehicle vh1) {
		return vehicleSer.updateVehicleDetails(vh1);
	}

	@DeleteMapping("/deleteVehicle/{chassisNo}")
	public ResponseEntity<?> deleteVehicleDetails(@PathVariable String chassisNo) {
		// return vehicleSer.DeleteVehicleDetails(chassisNo);
		try {
			return ResponseEntity.status(HttpStatus.OK).body(vehicleSer.DeleteVehicleDetails(chassisNo));
		} catch (RuntimeException e) {
			// send error mesg wrapped in RespEntity with suitable sts code (404)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/soldVehicleList")
	public ResponseEntity<?> getListOfSoldVehicles() {
		List<Vehicle> soldVehicles = vehicleService.soldVehicles();
		if (!soldVehicles.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(soldVehicles);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@GetMapping("/getVehicle/{chassisNo}")
	public ResponseEntity<?> getVehicleDetails(@PathVariable String chassisNo) {
		// return
		// ResponseEntity.status(HttpStatus.OK).body(vehicleSer.getVehicleDetailsbyChassisNo(chassisNo));
		try {
			return ResponseEntity.status(HttpStatus.OK).body(vehicleSer.getVehicleDetailsbyChassisNo(chassisNo));
		} catch (RuntimeException e) {
			// send error mesg wrapped in RespEntity with suitable sts code (404)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/customerList")
	public ResponseEntity<?> getListOfCustomers() {
		return ResponseEntity.status(HttpStatus.OK).body(customerSer.listOfCustomers());
	}

	@GetMapping("/getCustomer/{id}")
	public ResponseEntity<?> getCustomerDetails(@PathVariable Integer id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(customerSer.getCustomerDetailsById(id));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping("/addCustomer")
	public ResponseEntity<?> addCustomer(@RequestBody Customer cust) {
		Customer c = customerSer.addCustomerDetails(cust);

		if (c == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(c);
	}

	@PutMapping("/updateCustomer")
	public Customer updateCutomer(@RequestBody Customer cust) {
		return customerSer.updateCustomerDetails(cust);
	}

	@DeleteMapping("/deleteCustomer/{id}")
	public ResponseEntity<?> deleteCustomerDetails(@PathVariable Integer id) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(customerSer.DeleteCustomerDetails(id));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/employeeList")
	public ResponseEntity<?> getListOfEmployees() {
		return ResponseEntity.status(HttpStatus.OK).body(employeeSer.listOfEmployee());
	}

	@GetMapping("/getEmployee/{id}")
	public ResponseEntity<?> getEmployeeDetails(@PathVariable Integer id) {
		try {
			System.out.println(employeeSer.getEmployeeDetailsById(id));
			return ResponseEntity.status(HttpStatus.OK).body(employeeSer.getEmployeeDetailsById(id));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping("/addEmployee")
	public ResponseEntity<?> addEmployee(@RequestBody Employee emp) {
		Employee e = employeeSer.addEmployeeDetails(emp);

		if (e == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(e);
	}

	@PutMapping("/updateEmployee")
	public Employee updateEmployee(@RequestBody Employee emp) {
		return employeeSer.updateEmployeeDetails(emp);
	}

	@DeleteMapping("/deleteEmployee/{id}")
	public ResponseEntity<?> deleteEmployeeDetails(@PathVariable Integer id) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(employeeSer.DeleteEmployeeDetails(id));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/BookedServiceList")
	public ResponseEntity<?> getListOfBookedServices() {
		return ResponseEntity.status(HttpStatus.OK).body(bookSer.listOfBookingServices());
	}

	@GetMapping("/getBooking/{id}")
	public ResponseEntity<?> getBookingDetails(@PathVariable Integer id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(bookSer.getServiceDetails(id));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping("/bookService")
	public ResponseEntity<?> bookService(@RequestBody ServiceDto bs) {
		BookService e = bookSer.bookService(bs);

		if (e == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(e);
	}

//	@PutMapping("/updateServiceBooking")
//	public BookService updateBooking(@RequestBody BookService bs) {
//		return bookSer.updateService(bs);
//	}

	@DeleteMapping("/deleteSerciceBooking/{id}")
	public ResponseEntity<?> deleteSerciceBooking(@PathVariable Integer id) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(bookSer.removeServiceDetails(id));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/getServiceTypes")
	public ResponseEntity<?> getServiceTypes() {
		return ResponseEntity.ok().body(serviceType.getServiceTypeList());
	}

	@PostMapping("/addServiceType")
	public ResponseEntity<?> addServiceType(@RequestBody ServiceType servType) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(serviceType.addServiceType(servType));
	}
	
	@GetMapping("/invoiceList")
	public ResponseEntity<?> getListOfinvoices() {
		return ResponseEntity.status(HttpStatus.OK).body(invoiceService.listofInvoices());
	}
}
