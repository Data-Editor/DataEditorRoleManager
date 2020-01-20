package com.niek125.rolemanagerservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.niek125.rolemanagerservice.models.Role;
import com.niek125.rolemanagerservice.repository.RoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final Logger logger = LoggerFactory.getLogger(RoleController.class);
    private final RoleRepo roleRepo;
    private final ObjectMapper mapper;

    @Autowired
    public RoleController(RoleRepo roleRepo, ObjectMapper mapper) {
        this.roleRepo = roleRepo;
        this.mapper = mapper;
    }

    @GetMapping(value = "/getroles/{userid}")
    public String getRoles(@PathVariable("userid") String userid) throws JsonProcessingException {
        logger.info("getting roles for: {}", userid);
        final List<Role> roles = roleRepo.findRolesByUserid(userid);
        logger.info("constructing json");
        final String rls = mapper.writeValueAsString(roles);
        final DocumentContext doc = JsonPath.parse(rls).delete("$..userid").delete("$..roleid");
        logger.info("returning json");
        return doc.jsonString();
    }
}
