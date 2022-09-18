package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.pojos.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	// List<Employee>
	// Admin loginAdmin(String use)
	// List<Admin> findBySalaryGreaterThan(double salary);
	Admin findByEmailAndPassword(String email, String password);
	// add custom query method for bulk updation
//	@Modifying
//	@Query("update Employee e set e.salary=e.salary+:incr where e.department=:dept")
//	int updateSalary(@Param(value="incr") double increment, @Param(value="dept") String dept);
}
