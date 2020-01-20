package com.niek125.roleconsumer.handler;

import com.niek125.roleconsumer.events.DataEditorEvent;
import com.niek125.roleconsumer.events.UserRoleChangedEvent;
import com.niek125.roleconsumer.repository.RoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRoleChangedHandlerMethod extends HandlerMethod {
    private final Logger logger = LoggerFactory.getLogger(UserRoleChangedHandlerMethod.class);
    private final RoleRepo roleRepo;

    @Autowired
    protected UserRoleChangedHandlerMethod(RoleRepo roleRepo) {
        super(UserRoleChangedEvent.class);
        this.roleRepo = roleRepo;
    }

    @Override
    public void handle(DataEditorEvent event) {
        logger.info("saving role");
        roleRepo.save(((UserRoleChangedEvent) event).getRole());
    }
}
