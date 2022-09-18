package com.app.pojos;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "customer_tb")
public class Customer extends BaseEntity {

	public Customer(int custId) {
		super(custId);
	}

	@Column(name = "customer_name", nullable = false)
	private String name;

	@Column(nullable = false)
	@Embedded
	private Address address;

	@Column(name = "contact_no", nullable = false)
	private String contactNumber;
//	@NotBlank(message = "pls provide the vechicle no")
//	@Column(name = "vechicle_no")

	@Column(name = "email_id", unique = true)
	private String emailId;

//	@Column(nullable = false)
//	private String password;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "registration_date")
	private LocalDate dateOfRegistration;
	
	private boolean active;

}
