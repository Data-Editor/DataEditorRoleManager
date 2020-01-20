package com.niek125.roleconsumer.handler;

import com.niek125.roleconsumer.events.DataEditorEvent;
import com.niek125.roleconsumer.events.ProjectCreatedEvent;
import com.niek125.roleconsumer.models.Role;
import com.niek125.roleconsumer.models.RoleType;
import com.niek125.roleconsumer.repository.RoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProjectCreatedHandlerMethod extends HandlerMethod {
    private final Logger logger = LoggerFactory.getLogger(ProjectCreatedHandlerMethod.class);
    private final RoleRepo roleRepo;

    @Autowired
    public ProjectCreatedHandlerMethod(RoleRepo roleRepo) {
        super(ProjectCreatedEvent.class);
        this.roleRepo = roleRepo;
    }

    @Override
    public void handle(DataEditorEvent event) {logger.info("creating role");
        final ProjectCreatedEvent projectCreatedEvent = (ProjectCreatedEvent) event;
        final Role role = new Role(UUID.randomUUID().toString(),
                projectCreatedEvent.getCreatorid(),
                projectCreatedEvent.getProject().getProjectid(),
                RoleType.OWNER);
        logger.info("saving role");
        roleRepo.save(role);
    }
}
