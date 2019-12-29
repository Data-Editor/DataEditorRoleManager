package com.niek125.rolemanagerservice.repository;

import com.niek125.rolemanagerservice.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;

public interface UserRepo extends JpaRepository<User, String> {
    @Query("select u as user, r as role from user u left join role r on u.userid = r.userid where r.projectid = :projectid")
    List<Tuple> findUsersByProject(@Param("projectid") String projectid);

    @Query("select u from user u where u.email like :email")
    List<User> findFiveByEmail(@Param("email") String email, Pageable pageable);
}
