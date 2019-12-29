package com.niek125.rolemanagerservice.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niek125.rolemanagerservice.models.Permission;
import com.niek125.rolemanagerservice.models.Role;
import com.niek125.rolemanagerservice.models.User;
import com.niek125.rolemanagerservice.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepo userRepo;
    private final ObjectMapper objectMapper;
    private final JWTVerifier jwtVerifier;

    @Autowired
    public UserController(UserRepo userRepo, ObjectMapper objectMapper, JWTVerifier jwtVerifier) {
        this.userRepo = userRepo;
        this.objectMapper = objectMapper;
        this.jwtVerifier = jwtVerifier;
    }

    @RequestMapping(value = "/getusers/{projectid}", method = RequestMethod.GET)
    public String getUsers(@RequestHeader("Authorization") String token, @PathVariable("projectid") String projectid) throws JsonProcessingException {
        logger.info("verifying token");
        final DecodedJWT jwt = jwtVerifier.verify(token.replace("Bearer ", ""));
        final Permission[] perms = objectMapper.readValue(((jwt.getClaims()).get("pms")).asString(), Permission[].class);
        if (!Arrays.stream(perms).filter(p -> p.getProjectid().equals(projectid)).findFirst().isPresent()) {
            logger.info("no permission");
            return "[]";
        }
        logger.info("getting users");
        final List<Tuple> users = userRepo.findUsersByProject(projectid);
        logger.info("constructing json");
        String json = "[";
        for (int i = 0; i < users.size(); i++) {
            if (i > 0) {
                json += ",";
            }
            User user = (User) users.get(i).get("user");
            Role role = (Role) users.get(i).get("role");
            json += "{\"user\":" + objectMapper.writeValueAsString(user) +
                    ",\"role\":" + objectMapper.writeValueAsString(role) + "}";
        }
        logger.info("returning json");
        return json + "]";
    }

    @RequestMapping("/users/{email}")
    public List<User> autocompleteUsers(@PathVariable("email") String email) {
        logger.info("getting users like: " + email);
        List<User> users = userRepo.findFiveByEmail(email + "%", PageRequest.of(0, 5));
        logger.info("returning: " + users.size() + " users");
        return users;
    }
}
