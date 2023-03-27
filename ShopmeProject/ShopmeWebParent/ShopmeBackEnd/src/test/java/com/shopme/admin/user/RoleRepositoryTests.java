package com.shopme.admin.user;

import com.shopme.admin.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false  )
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;

//    @Test
//    public void testCreateFirstRole(){
//        Role roleAdmin = new Role("Admin","manage everything");
//        Role savedRole = roleRepository.save(roleAdmin);
//        assertThat(savedRole.getId()).isGreaterThan(0);
//    }
//    @Test
//	public void testCreateRestRole() {
//		Role roleAdmin = new Role("Admin", "manage everything");
//		Role roleSalesperson = new Role("Salesperson", "many product price, "+
//							"customer, shipping, order and sales report");
//		Role roleEditor = new Role("Editor", "manage categories, brands" +
//							", products, acticles and menus");
//		Role roleShiper = new Role("Shiper", "view products, view orders"+
//							" and update order status");
//		Role roleAssistant = new Role("Assistant","manage questions and reviews");
//
//		roleRepository.saveAll(List.of(roleAdmin,roleSalesperson,roleEditor,roleShiper,roleAssistant));
//	}
}
