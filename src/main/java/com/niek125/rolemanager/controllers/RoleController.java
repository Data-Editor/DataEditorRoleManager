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
    private final RoleRepo roleRepo;
    private final ObjectMapper mapper;

    @Autowired
    public RoleController(RoleRepo roleRepo, ObjectMapper mapper) {
        this.roleRepo = roleRepo;
        this.mapper = mapper;
    }

    @RequestMapping(value = "/getroles/{userid}", method = RequestMethod.GET)
    public String getRoles(@PathVariable("userid") String userid) throws JsonProcessingException {
        final String roles = mapper.writeValueAsString(roleRepo.findRolesByUserid(userid));
        final DocumentContext doc = JsonPath.parse(roles).delete("$..userid").delete("$..roleid");
        return doc.jsonString();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveRole(@RequestBody String role) throws IOException {
        roleRepo.save(mapper.readValue(role, Role.class));
    }
}
