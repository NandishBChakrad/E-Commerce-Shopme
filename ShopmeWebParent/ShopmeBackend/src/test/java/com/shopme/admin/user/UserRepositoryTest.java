package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	@Autowired
	UserRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userNand = new User("nandishbc27@gmail.com", "1234", "Nandish", "B Chakrad");
		userNand.addRole(roleAdmin);
		User savedUser = repo.save(userNand);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userRavi = new User("ravi@gmail.com", "ABCD", "Ravi", "Kumar");

		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);

		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);

		User savedUser = repo.save(userRavi);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testUserById() {
		User userNand = repo.findById(1).get();
		System.out.println(userNand);
		assertThat(userNand).isNotNull();
	}
	@Test
	public void testUpdateUserDetails() {
		User userNand = repo.findById(1).get();
		userNand.setEnabled(true);
		userNand.setEmail("Nandishbc27@gmail.com");
		repo.save(userNand);
	}
	
	@Test
	public void testUpdateRole() {
		User userRavi = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalesPerson = new Role(2);
		
		userRavi.getRoles().remove(roleEditor);
		userRavi.addRole(roleSalesPerson);
		
		repo.save(userRavi);
	}
	@Test
	public void testDeleteuser() {
		Integer userId =2;
		repo.deleteById(userId);
		
	}
}
