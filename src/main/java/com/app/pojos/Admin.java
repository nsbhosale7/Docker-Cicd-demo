package com.app.pojos;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "admin_tb")
public class Admin extends BaseEntity {
	@NotBlank(message = "pls provide the first name")
	@Column(length = 30, name = "first_name")
	@JsonProperty("name")
	private String firstName;
	@Length(min = 3, max = 29, message = "Invalid length of lastName")
	@Column(length = 30, name = "last_name")
	private String lastName;
	@Column(length = 30, unique = true)
	@NotBlank(message = "pls provide the email")
	@Email
	private String email;
	@Column(length = 30)
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[#@$*]).{5,20})", message = "invalid password")
	private String password;
//	@Column(length = 30)
//	private String location;
//	@Column(length = 30)
//	private String department;
//	@NotNull(message = "salary must be supplied")
//	@Range(min = 20000, max = 50000, message = "invalid salary")
//	private double salary;
//	@NotNull(message = "join date is req")
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private LocalDate joinDate;

	public Admin(@NotBlank(message = "pls provide the email") @Email String email,
			@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[#@$*]).{5,20})", message = "invalid password") String password) {
		super();
		this.email = email;
		this.password = password;
	}

}
