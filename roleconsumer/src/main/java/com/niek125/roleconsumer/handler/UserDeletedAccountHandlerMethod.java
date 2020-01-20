package com.niek125.roleconsumer.handler;

import com.niek125.roleconsumer.events.DataEditorEvent;
import com.niek125.roleconsumer.events.UserDeletedAccountEvent;
import com.niek125.roleconsumer.repository.RoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDeletedAccountHandlerMethod extends HandlerMethod<UserDeletedAccountEvent> {
    private final Logger logger = LoggerFactory.getLogger(UserDeletedAccountHandlerMethod.class);
    private final RoleRepo roleRepo;

    @Autowired
    public UserDeletedAccountHandlerMethod(RoleRepo roleRepo) {
        super(UserDeletedAccountEvent.class);
        this.roleRepo = roleRepo;
    }

    @Override
    public void handle(DataEditorEvent event) {
        logger.info("deleteing roles");
        roleRepo.deleteRolesByUserid(((UserDeletedAccountEvent) event).getUserid());
    }
}