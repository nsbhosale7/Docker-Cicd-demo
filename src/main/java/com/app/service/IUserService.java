package com.app.service;

import com.app.dto.AuthenticationRequest;
import com.app.dto.ChangePasswordDto;
import com.app.dto.SignUpRequest;
import com.app.dto.UserResponseDTO;
import com.app.pojos.User;

//Nothing to do with spring security : it's job currently is user registration
public interface IUserService {
	UserResponseDTO registerUser(SignUpRequest request);
	
	User getUserDetails(String email);
	
	User getUserDetailsForOTP(String email);

//	String changePassword(AuthenticationRequest user);

	String changePassword(ChangePasswordDto changePass);
}
