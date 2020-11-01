package com.springboot.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{

//	Role is already defined in the bean
	List<User> findByRole(String role);
}
