package com.gaea.gaeabackend;

import com.gaea.gaeabackend.repository.UserRepo;
import com.gaea.gaeabackend.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.gaea.gaeabackend.entity.User;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GaeaBackendApplicationTests {
    
    @Autowired
    private UserService userService;

	@Test
	public void contextLoads() {
//            User user = new User();
//            user.setFirstName("Admin");
//            user.setLastName("Gaea");
//            user.setEmail("admingaea@gaea.id");
//            user.setPassword("welcome1");
//            user.setCreatedBy("gaea");
//            user.setCreatedDate(new Date());
//            userService.saveUser(user);
	}

}
