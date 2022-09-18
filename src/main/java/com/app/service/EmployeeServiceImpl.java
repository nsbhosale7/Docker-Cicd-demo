package com.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custome_exception.ResourseNotFountException;
import com.app.dao.EmployeeRepositiry;
import com.app.dao.RoleRepository;
import com.app.dao.UserRepository;
import com.app.pojos.Employee;
import com.app.pojos.Role;
import com.app.pojos.User;
import com.app.pojos.UserRoles;

@Service
@Transactional
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private EmployeeRepositiry empRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public Employee getEmployeeDetailsById(int empId) {
		return empRepo.findById(empId)
				.orElseThrow(() -> new ResourseNotFountException("Employee with id " + empId + " not found!!!!!!!!!"));

	}

	@Override
	public List<Employee> listOfEmployee() {
		return empRepo.findAll();
	}

	@Override
	public Employee addEmployeeDetails(Employee transientEmp) {
//		if (empRepo.existsById(transientEmp.getId())) {
//			return null;
//		}
		User user = new User();
		user.setActive(true);
		user.setEmail(transientEmp.getEmailId());
		user.setPassword(encoder.encode(transientEmp.getPassword()));
		user.setUserName(transientEmp.getName());
		Set<Role> roles = new HashSet<Role>();
		Role role = roleRepo.findByUserRole(UserRoles.ROLE_EMPLOYEE).orElseThrow();
//		Role role = new Role();
//		role.setUserRole(UserRoles.ROLE_EMPLOYEE);
		roles.add(role);
		user.setRoles(roles);
		User saveToDB = userRepo.save(user);
		return empRepo.save(transientEmp);

	}

	@Override
	public Employee updateEmployeeDetails(Employee detachedEmp) {
		return empRepo.save(detachedEmp);
	}

	@Override
	public String DeleteEmployeeDetails(int empId) {
		if (empRepo.existsById(empId)) {
			empRepo.deleteById(empId);
			return "Employee Details deleted with" + empId;
		}
		return null;
	}

}
