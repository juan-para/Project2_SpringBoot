package com.springboot.jpa;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(UserCommandLineRunner.class);
	
	@Autowired
	UserRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("User are:");
		
		repository.save(new User("Juan","admin"));
		repository.save(new User("Lucas","user"));
		repository.save(new User("Agustina","admin"));
		repository.save(new User("Estefania","user"));
		
		for (User user : repository.findAll()) {
			log.info(user.toString());
		}
		
		System.out.println("Admin user are:");
		for (User user : repository.findByRole("admin")) {
			log.info(user.toString());
		}
		System.out.println("Admin role order by name desc:");
		for (User user : repository.findByRoleOrderByName("admin")) {
			log.info(user.toString());
		}
		System.out.println("Admin role and name:");
		for (User user : repository.findByNameAndRole("Agustina", "admin")) {
			log.info(user.toString());
		}
	}

}
