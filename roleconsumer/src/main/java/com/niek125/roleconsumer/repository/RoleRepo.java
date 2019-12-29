package com.niek125.roleconsumer.repository;

import com.niek125.roleconsumer.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, String> {
}
