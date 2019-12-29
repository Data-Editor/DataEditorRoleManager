package com.niek125.userconsumer.repository;

import com.niek125.userconsumer.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
}
