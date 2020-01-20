package com.niek125.rolemanagerservice.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niek125.rolemanagerservice.constructor.JsonConstructor;
import com.niek125.rolemanagerservice.models.Permission;
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
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepo userRepo;
    private final ObjectMapper objectMapper;
    private final JWTVerifier jwtVerifier;
    private final JsonConstructor jsonConstructor;

    @Autowired
    public UserController(UserRepo userRepo, ObjectMapper objectMapper, JWTVerifier jwtVerifier, JsonConstructor jsonConstructor) {
        this.userRepo = userRepo;
        this.objectMapper = objectMapper;
        this.jwtVerifier = jwtVerifier;
        this.jsonConstructor = jsonConstructor;
    }

    private DecodedJWT verifyToken(String token){
        return jwtVerifier.verify(token.replace("Bearer ", ""));
    }

    private boolean hasPermission(String token, String projectid) throws JsonProcessingException {
        logger.info("verifying token");
        final DecodedJWT jwt = verifyToken(token);
        final Permission[] perms = objectMapper.readValue(((jwt.getClaims()).get("pms")).asString(), Permission[].class);
        return Arrays.stream(perms).anyMatch(p -> p.getProjectid().equals(projectid));
    }

    @GetMapping(value = "/getusers/{projectid}")
    public String getUsers(@RequestHeader("Authorization") String token, @PathVariable("projectid") String projectid) throws JsonProcessingException {
        if (!hasPermission(token, projectid)) {
            logger.info("no permission");
            return "[]";
        }
        logger.info("getting users");
        final List<Tuple> users = userRepo.findUsersByProject(projectid);
        logger.info("constructing json");
        final String json = jsonConstructor.constructJson(users);
        logger.info("returning json");
        return json;
    }

    @GetMapping("/users/{email}")
    public List<User> autocompleteUsers(@PathVariable("email") String email, @RequestHeader("Authorization") String token) {
        verifyToken(token);
        logger.info("getting users like: {}", email);
        List<User> users = userRepo.findFiveByEmail(email + "%", PageRequest.of(0, 5));
        logger.info("returning: {} users", users.size());
        return users;
    }
}
