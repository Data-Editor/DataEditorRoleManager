package com.niek125.roleconsumer.repository;

import com.niek125.roleconsumer.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepo extends JpaRepository<Role, String> {
    @Query("delete from role r where r.projectid = :projectid")
    void deleteRolesByProjectid(@Param("projectid") String projectid);

    @Query("delete from role r where r.userid = :userid")
    void deleteRolesByUserid(@Param("userid") String userid);
}
