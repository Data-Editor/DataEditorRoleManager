package com.niek125.rolemanager.controllers;

import com.niek125.rolemanager.models.Role;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @RequestMapping("/getroles/{userid}")
    public List<Role> getRoles(@PathVariable("userid") String userid){
        return null;
    }
}
