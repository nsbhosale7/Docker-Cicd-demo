package com.app.service;

import java.util.List;

import com.app.dto.InvoiceServiceDto;
import com.app.dto.InvoiceVehicleDto;
import com.app.pojos.Invoice;

public interface IInvoiceService {

	InvoiceVehicleDto bookVehicleInvoice(int custId, String chassisNo);

	InvoiceVehicleDto purchaseVehcleInvoice(String chassisNo);

	List<Invoice> listofInvoices();
	
	InvoiceServiceDto bookServiceInvoice(int id);
}
