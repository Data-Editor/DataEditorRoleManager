package com.niek125.rolemanager.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niek125.rolemanager.models.Permission;
import com.niek125.rolemanager.models.User;
import com.niek125.rolemanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public UserController(){
        java.security.Security.addProvider(
            new org.bouncycastle.jce.provider.BouncyCastleProvider()
    );
        this.objectMapper = new ObjectMapper();
    }

    @RequestMapping("/getusers/{projectid}")
    public List<User> getusers(@RequestHeader("Authorization") String token, @PathVariable("projectid") String projectid){
        try {
            Algorithm algorithm = Algorithm.RSA512((RSAPublicKey) readPublicKeyFromFile("src/main/resources/PublicKey.pem", "RSA"), null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("data-editor-token-service")
                    .build();
            DecodedJWT jwt = verifier.verify(token.replace("Bearer ", ""));
            Permission[] perms = objectMapper.readValue(((jwt.getClaims()).get("pms")).asString(), Permission[].class);
            if(Arrays.stream(perms).filter(p -> p.getProjectid().equals(projectid)).findFirst().isPresent()){
                return userRepo.findUsersByProject(projectid);
            }
        } catch (JWTVerificationException | IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
