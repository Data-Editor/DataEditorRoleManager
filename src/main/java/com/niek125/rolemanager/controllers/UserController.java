package com.niek125.rolemanager.controllers;

import com.niek125.rolemanager.models.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/getusers/{projectid}")
    public List<User> getusers(@PathVariable("projectid") String projectid){
        return null;
    }
}
