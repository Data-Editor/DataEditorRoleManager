package com.niek125.rolemanager.controllers;

import com.niek125.rolemanager.models.Role;
import com.niek125.rolemanager.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleRepo roleRepo;

    @RequestMapping(value = "/getroles/{userid}", method = RequestMethod.GET)
    public List<Role> getRoles(@PathVariable("userid") String userid){
        return roleRepo.findRolesByUserid(userid);
    }
}
