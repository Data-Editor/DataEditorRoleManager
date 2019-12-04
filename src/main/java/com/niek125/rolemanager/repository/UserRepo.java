package com.niek125.rolemanager.repository;

import com.niek125.rolemanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
}
