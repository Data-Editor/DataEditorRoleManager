package com.niek125.rolemanager.repository;

import com.niek125.rolemanager.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role, Long> {
    @Query("select r from role r where r.userid = :userid")
    List<Role> findRolesByUserid(@Param("userid") String userid);
}
