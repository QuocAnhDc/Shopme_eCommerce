package com.shopme.admin.controller.user;

import com.shopme.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/check_email")
    public Map<String, Object> checkDuplicateEmail(@Param("id") Integer id,@Param("email") String email){
        String message =  userService.isEmailUnique(id,email) ? "OK" : "Duplicated";
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);

        return response;
    }
}
