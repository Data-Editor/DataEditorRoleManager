package com.niek125.rolemanager.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;

import static com.niek125.rolemanager.controllers.PemUtils.readPublicKeyFromFile;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepo userRepo;
    private ObjectMapper objectMapper;

    public UserController() {
        java.security.Security.addProvider(
                new org.bouncycastle.jce.provider.BouncyCastleProvider()
        );
        this.objectMapper = new ObjectMapper();
    }

    @RequestMapping(value = "/getusers/{projectid}", method = RequestMethod.GET)
    public String getUsers(@RequestHeader("Authorization") String token, @PathVariable("projectid") String projectid) {
        try {
            Algorithm algorithm = Algorithm.RSA512((RSAPublicKey) readPublicKeyFromFile("src/main/resources/PublicKey.pem", "RSA"), null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("data-editor-token-service")
                    .build();
            DecodedJWT jwt = verifier.verify(token.replace("Bearer ", ""));
            Permission[] perms = objectMapper.readValue(((jwt.getClaims()).get("pms")).asString(), Permission[].class);
            if (Arrays.stream(perms).filter(p -> p.getProjectid().equals(projectid)).findFirst().isPresent()) {
                List<Tuple> users = userRepo.findUsersByProject(projectid);
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
    public String autocompleteUsers(@PathVariable("email") String email) {
        List<User> users = userRepo.findFiveByEmail(email + "%", PageRequest.of(0, 5));
        String json = "[";
        for (int i = 0; i < users.size(); i++) {
            if (i > 0) {
                json += ",";
            }
            json += users.get(i).toJSON();
        }
        return json + "]";
    }
}
