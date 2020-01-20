package com.niek125.userconsumer.handler;

import com.niek125.userconsumer.events.DataEditorEvent;
import com.niek125.userconsumer.events.UserLoggedInEvent;
import com.niek125.userconsumer.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserLoggedInHandlerMethod extends HandlerMethod<UserLoggedInEvent> {
    private final Logger logger = LoggerFactory.getLogger(UserLoggedInHandlerMethod.class);
    private final UserRepo userRepo;

    @Autowired
    public UserLoggedInHandlerMethod(UserRepo userRepo) {
        super(UserLoggedInEvent.class);
        this.userRepo = userRepo;
    }

    @Override
    public void handle(DataEditorEvent event) {
        logger.info("saving user");
        userRepo.save(((UserLoggedInEvent) event).getUser());
    }
}
