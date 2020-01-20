package com.niek125.roleconsumer.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class HandlerMethodConfig {
    @Bean
    @Autowired
    public List<HandlerMethod> handlerMethods(ProjectCreatedHandlerMethod projectCreatedHandlerMethod,
                                              ProjectDeletedHandlerMethod projectDeletedHandlerMethod,
                                              UserAddedToProjectHandlerMethod userAddedToProjectHandlerMethod,
                                              UserRoleChangedHandlerMethod userRoleChangedHandlerMethod,
                                              UserRemovedFromProjectHandlerMethod userRemovedFromProjectHandlerMethod) {
        final List<HandlerMethod> methods = new ArrayList<>();
        methods.add(projectCreatedHandlerMethod);
        methods.add(projectDeletedHandlerMethod);
        methods.add(userAddedToProjectHandlerMethod);
        methods.add(userRoleChangedHandlerMethod);
        methods.add(userRemovedFromProjectHandlerMethod);
        return methods;
    }
}
