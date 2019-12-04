package com.niek125.rolemanager.repository;

import com.niek125.rolemanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, String> {
    @Query("select u from user u left join role r on u.userid = r.userid where r.projectid = :projectid")
    List<User> findUsersByProject(@Param("projectid") String projectid);
}
