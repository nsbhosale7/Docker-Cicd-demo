package com.app.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;



import com.app.pojos.ServiceType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceServiceDto {
	
	private String vehicleNo;
	
	private int invoiceId;
	
	private String customerName;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String zipCode;
	
	private String emailId;
	
	private String contactNumber;
	
	private String modelName;
	
	private double kmsDriven;
	
	private LocalDate serviceBookingDate;
	
	private LocalDate servicingDate;
	
	private int empId;

	private Set<ServiceType> types = new HashSet<>();
	
	private double grossTotal;
	
	private double actualTotal;
}
