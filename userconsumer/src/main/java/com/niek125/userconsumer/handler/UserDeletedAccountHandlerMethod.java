package com.niek125.userconsumer.handler;

import com.niek125.userconsumer.events.DataEditorEvent;
import com.niek125.userconsumer.events.UserDeletedAccountEvent;
import com.niek125.userconsumer.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDeletedAccountHandlerMethod extends HandlerMethod<UserDeletedAccountEvent> {
    private final Logger logger = LoggerFactory.getLogger(UserDeletedAccountHandlerMethod.class);
    private final UserRepo userRepo;

    @Autowired
    public UserDeletedAccountHandlerMethod(UserRepo userRepo) {
        super(UserDeletedAccountEvent.class);
        this.userRepo = userRepo;
    }

    @Override
    public void handle(DataEditorEvent event) {
        logger.info("deleting user");
        userRepo.deleteById(((UserDeletedAccountEvent) event).getUserid());
    }
}
