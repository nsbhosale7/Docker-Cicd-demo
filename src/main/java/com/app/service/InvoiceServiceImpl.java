package com.app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.CustomerAdmRepository;
import com.app.dao.InvoiceRepository;
import com.app.dao.ServiceRepository;
import com.app.dao.VehicleRepository;
import com.app.dto.InvoiceServiceDto;
import com.app.dto.InvoiceVehicleDto;
import com.app.pojos.Address;
import com.app.pojos.BookService;
import com.app.pojos.Customer;
import com.app.pojos.Invoice;
import com.app.pojos.ServiceType;
import com.app.pojos.Vehicle;

@Service
@Transactional
public class InvoiceServiceImpl implements IInvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepo;

	@Autowired
	private VehicleRepository vehicleRepo;
	@Autowired
	private CustomerAdmRepository customerRepo;
	
	@Autowired
	private ServiceRepository serviceRepo;

	@Override
	public InvoiceVehicleDto bookVehicleInvoice(int custId,String chassisNo1) {
		InvoiceVehicleDto booked = new InvoiceVehicleDto();
		//Customer customer = new Customer(custId);
		System.out.println(chassisNo1 +"in method");
		Vehicle v1 = vehicleRepo.findByChassisNo(chassisNo1);
		Customer c1 =customerRepo.findById(custId).orElseThrow();
		booked.setChassisNo(v1.getChassisNo());
		booked.setEngineNo(v1.getEngineNo());
		booked.setColor(v1.getColor());
		booked.setPrice(v1.getPrice());
		booked.setModelName(v1.getModelName());
		booked.setVehicleNo(v1.getVehicleNo());

		booked.setCustomerName(c1.getName());
		booked.setEmailId(c1.getEmailId());
		booked.setContactNumber(c1.getContactNumber());
		Address address = c1.getAddress();
		booked.setCity(address.getCity());
		booked.setCountry(address.getCountry());
		booked.setZipCode(address.getZipCode());
		booked.setState(address.getState());
		booked.setPrice(10000);
		double gst = 10000 * 0.12;
		double total = 10000 * 0.28 + 10000;
		booked.setTotalAmount(total);
		booked.setGst(gst);
		Invoice vehicleInvoice = new Invoice();
		//Customer customer1=customerRepo.findById(custId).orElseThrow();
		vehicleInvoice.setCustomer(c1);
		
		vehicleInvoice.setTotalCost(total);
		vehicleInvoice.setTypeofInvoice("VEHICLE BOOKING");
		vehicleInvoice.setVehicle(v1);
		vehicleInvoice.setDateOfInvoice(LocalDate.now());
		Invoice i1 = invoiceRepo.save(vehicleInvoice);
		booked.setInvoiceId(i1.getId());

		return booked;
	}

	@Override
	public InvoiceVehicleDto purchaseVehcleInvoice(String chassisNo) {
		InvoiceVehicleDto booked = new InvoiceVehicleDto();
		// Customer customer=new Customer(custId);
		

		Vehicle v1 = vehicleRepo.findByChassisNo(chassisNo);
		Customer c1 = v1.getCustomer();
		booked.setChassisNo(v1.getChassisNo());
		booked.setEngineNo(v1.getEngineNo());
		booked.setColor(v1.getColor());
		booked.setPrice(v1.getPrice());
		booked.setModelName(v1.getModelName());
		booked.setVehicleNo(v1.getVehicleNo());

		booked.setCustomerName(c1.getName());
		booked.setEmailId(c1.getEmailId());
		booked.setContactNumber(c1.getContactNumber());
		Address address = c1.getAddress();
		booked.setCity(address.getCity());
		booked.setCountry(address.getCountry());
		booked.setZipCode(address.getZipCode());
		booked.setState(address.getState());
		booked.setPrice(v1.getPrice());
		double price = v1.getPrice();
		double total = price + (price * 0.05) + (price * 0.05) + (price * 0.04) + (price * 0.18);
		booked.setTotalAmount(total);

		Invoice vehicleInvoice = new Invoice();
//		vehicleInvoice=invoiceRepo.findByVehicleId(chassisNo);
		vehicleInvoice.setTypeofInvoice("VEHICLE PURCHASING");
		vehicleInvoice.setCustomer(c1);
		vehicleInvoice.setBookservice(null);
		// vehicleInvoice.setId(null);
		vehicleInvoice.setTotalCost(total);
		vehicleInvoice.setVehicle(v1);
		vehicleInvoice.setDateOfInvoice(LocalDate.now());
		Invoice i1 = invoiceRepo.save(vehicleInvoice);
		booked.setInvoiceId(i1.getId());

		return booked;
	}
	
	@Override
	public InvoiceServiceDto bookServiceInvoice(int id) {
		BookService service=serviceRepo.findById(id).orElseThrow();
		InvoiceServiceDto invoicedto=new InvoiceServiceDto();
		Customer customer=customerRepo.findById(service.getCustomerId()).orElseThrow();
		invoicedto.setCustomerName(customer.getName());
		invoicedto.setCity(customer.getAddress().getCity());
		invoicedto.setState(customer.getAddress().getState());
		invoicedto.setCountry(customer.getAddress().getCountry());
		invoicedto.setZipCode(customer.getAddress().getZipCode());
		invoicedto.setEmailId(customer.getEmailId());
		invoicedto.setContactNumber(customer.getContactNumber());
	
		invoicedto.setServiceBookingDate(service.getServiceBookingDate());
		invoicedto.setServicingDate(service.getServicingDate());
		invoicedto.setModelName(service.getModelName());
		invoicedto.setKmsDriven(service.getKmsDriven());
		invoicedto.setVehicleNo(service.getVehicleNo());
		invoicedto.setTypes(service.getTypes());
		Set<ServiceType> types =service.getTypes();
		double total=0;
		for (ServiceType serviceType : types) {
			total=total+serviceType.getAmount();
		}
		double grosstotal=total;
		System.out.println(total);
		invoicedto.setGrossTotal(total);
		double actualTotal=grosstotal*1.18;
		invoicedto.setActualTotal(actualTotal);
		
		Invoice serviceinvoice=new Invoice();
		serviceinvoice.setBookservice(service);
		serviceinvoice.setCustomer(customer);
		serviceinvoice.setDateOfInvoice(LocalDate.now());
		serviceinvoice.setTypeofInvoice("SERVICE INVOICE");
		serviceinvoice.setTotalCost(actualTotal);
		
		Invoice saved=invoiceRepo.save(serviceinvoice);
		invoicedto.setInvoiceId(saved.getId());
		return invoicedto;
	}

	@Override
	public List<Invoice> listofInvoices() {
		return invoiceRepo.findAll();
	}
}
