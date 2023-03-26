package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false  )
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

//	@Test
//	public void testCreateNewUserWithOneRole() {
//		Role roleAdmin = entityManager.find(Role.class, 1);
//		User userQuocAnh = new User("quocanhute@gmail.com","123456","Quoc Anh","Ta");
//		userQuocAnh.addRole(roleAdmin);
//
//		User saveUser = userRepository.save(userQuocAnh);
//
//		assertThat(saveUser.getId()).isGreaterThan(0);
//	}
//	@Test
//	public void testCreateNewUserWithTwoRole() {
//		User userTest = new User("test@gmail.com","123456","Test Anh","Test");
//		Role roleEditor = new Role(3);
//		Role roleAssistant = new Role(5);
//		userTest.addRole(roleEditor);
//		userTest.addRole(roleAssistant);
//
//
//		User saveUser = userRepository.save(userTest);
//
//		assertThat(saveUser.getId()).isGreaterThan(0);
//	}
//
//    @Test
//    public void testCountById(){
//        Integer id = 1;
//        Long countById = userRepository.countById(id);
//
//        assertThat(countById).isNotNull().isGreaterThan(0);
//    }
//
//    @Test
//    public void updateDisableUser(){
//        Integer id = 3;
//        userRepository.updateEnabledStatus(id,false);
//    }
//    @Test
//    public void updateEnableUser(){
//        Integer id = 3;
//        userRepository.updateEnabledStatus(id,true);
//    }
//
//    @Test
//    public void testListFirstPage(){
//        int pageNumber = 0;
//        int pageSize = 4;
//
//        Pageable pageable = PageRequest.of(pageNumber,pageSize);
//        Page<User> page = userRepository.findAll(pageable);
//        List<User> userList = page.getContent();
//
//        userList.forEach(user -> System.out.println(user));
//
//        assertThat(userList.size()).isEqualTo(pageSize);
//
//    }

}
