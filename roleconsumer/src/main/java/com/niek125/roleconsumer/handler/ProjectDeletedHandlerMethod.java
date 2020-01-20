package com.niek125.roleconsumer.handler;

import com.niek125.roleconsumer.events.DataEditorEvent;
import com.niek125.roleconsumer.events.ProjectDeletedEvent;
import com.niek125.roleconsumer.repository.RoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectDeletedHandlerMethod extends HandlerMethod {
    private final Logger logger = LoggerFactory.getLogger(ProjectDeletedHandlerMethod.class);
    private final RoleRepo roleRepo;

    @Autowired
    public ProjectDeletedHandlerMethod(RoleRepo roleRepo) {
        super(ProjectDeletedEvent.class);
        this.roleRepo = roleRepo;
    }

    @Override
    public void handle(DataEditorEvent event) {
        logger.info("deleting roles");
        roleRepo.deleteRolesByProjectid(((ProjectDeletedEvent) event).getProjectid());
    }
}
