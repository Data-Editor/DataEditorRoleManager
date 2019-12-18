package com.niek125.rolemanager.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.niek125.rolemanager.models.Role;
import com.niek125.rolemanager.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleRepo roleRepo;
    private ObjectMapper mapper;

    public RoleController() {
        mapper = new ObjectMapper();
    }

    @RequestMapping(value = "/getroles/{userid}", method = RequestMethod.GET)
    public String getRoles(@PathVariable("userid") String userid) throws JsonProcessingException {
        String roles = mapper.writeValueAsString(roleRepo.findRolesByUserid(userid));
        DocumentContext doc = JsonPath.parse(roles).delete("$..userid").delete("$..roleid");
        return doc.jsonString();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveRole(@RequestBody String role) throws IOException {
        System.out.println(role);
        roleRepo.save(mapper.readValue(role, Role.class));
    }
}
