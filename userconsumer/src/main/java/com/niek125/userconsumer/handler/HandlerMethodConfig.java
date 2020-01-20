package com.niek125.userconsumer.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class HandlerMethodConfig {
    @Bean
    @Autowired
    public List<HandlerMethod> handlerMethods(UserLoggedInHandlerMethod userLoggedInHandlerMethod,
                                              UserDeletedAccountHandlerMethod userDeletedAccountHandlerMethod) {
        final List<HandlerMethod> methods = new ArrayList<>();
        methods.add(userLoggedInHandlerMethod);
        methods.add(userDeletedAccountHandlerMethod);
        return methods;
    }
}
