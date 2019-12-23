package com.niek125.rolemanager.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niek125.rolemanager.models.Permission;
import com.niek125.rolemanager.models.Role;
import com.niek125.rolemanager.models.User;
import com.niek125.rolemanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
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
    public String getUsers(@RequestHeader("Authorization") String token, @PathVariable("projectid") String projectid) {
        try {
            final DecodedJWT jwt = jwtVerifier.verify(token.replace("Bearer ", ""));
            final Permission[] perms = objectMapper.readValue(((jwt.getClaims()).get("pms")).asString(), Permission[].class);
            if (Arrays.stream(perms).filter(p -> p.getProjectid().equals(projectid)).findFirst().isPresent()) {
                final List<Tuple> users = userRepo.findUsersByProject(projectid);
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
                return json + "]";
            }
        } catch (JWTVerificationException | IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveUser(@RequestBody() User user) {
        userRepo.save(user);
    }

    @RequestMapping("/users/{email}")
    public List<User> autocompleteUsers(@PathVariable("email") String email) {
        return userRepo.findFiveByEmail(email + "%", PageRequest.of(0, 5));
    }
}
