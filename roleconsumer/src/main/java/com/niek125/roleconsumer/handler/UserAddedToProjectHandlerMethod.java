package com.niek125.roleconsumer.handler;

import com.niek125.roleconsumer.events.DataEditorEvent;
import com.niek125.roleconsumer.events.UserAddedToProjectEvent;
import com.niek125.roleconsumer.models.Role;
import com.niek125.roleconsumer.models.RoleType;
import com.niek125.roleconsumer.repository.RoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserAddedToProjectHandlerMethod extends HandlerMethod {
    private final Logger logger = LoggerFactory.getLogger(UserAddedToProjectHandlerMethod.class);
    private final RoleRepo roleRepo;

    @Autowired
    public UserAddedToProjectHandlerMethod(RoleRepo roleRepo) {
        super(UserAddedToProjectEvent.class);
        this.roleRepo = roleRepo;
    }

    @Override
    public void handle(DataEditorEvent event) {
        logger.info("creating role");
        final UserAddedToProjectEvent userAddedToProjectEvent = (UserAddedToProjectEvent) event;
        final Role role = new Role(UUID.randomUUID().toString(),
                userAddedToProjectEvent.getUserid(),
                userAddedToProjectEvent.getProjectid(),
                RoleType.GUEST);
        logger.info("saving role");
        roleRepo.save(role);
    }
}
