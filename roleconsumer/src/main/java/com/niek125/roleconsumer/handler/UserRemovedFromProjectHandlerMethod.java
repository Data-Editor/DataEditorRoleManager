package com.niek125.roleconsumer.handler;

import com.niek125.roleconsumer.events.DataEditorEvent;
import com.niek125.roleconsumer.events.UserRemovedFromProjectEvent;
import com.niek125.roleconsumer.repository.RoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRemovedFromProjectHandlerMethod extends HandlerMethod {
    private final Logger logger = LoggerFactory.getLogger(UserRemovedFromProjectHandlerMethod.class);
    private final RoleRepo roleRepo;

    @Autowired
    public UserRemovedFromProjectHandlerMethod(RoleRepo roleRepo) {
        super(UserRemovedFromProjectEvent.class);
        this.roleRepo = roleRepo;
    }

    @Override
    public void handle(DataEditorEvent event) {
        logger.info("deleting role");
        roleRepo.deleteById(((UserRemovedFromProjectEvent) event).getRoleid());
    }
}
