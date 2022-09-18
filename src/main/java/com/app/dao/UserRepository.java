package com.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.pojos.User;

public interface UserRepository extends JpaRepository<User, Long>{
	@Query("select distinct u from User u left outer join fetch u.roles where u.email=:nm")
	Optional<User> findByEmail(@Param("nm") String email);
	
	User getByEmail(String email);
//	boolean existsByUserName(String userName);
//	boolean existsByEmail(String email);
	@Query("select  u from User u where u.email=:nm")
	Optional<User> fetchUserDetails(@Param("nm") String userName);

}
