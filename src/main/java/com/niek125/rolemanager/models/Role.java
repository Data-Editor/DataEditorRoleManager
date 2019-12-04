package com.niek125.rolemanager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity(name = "role")
public class Role {
    @Id
    @JsonIgnore
    private int roleid;
    @JsonIgnore
    private String userid;
    private String projectid;
    @Enumerated(EnumType.STRING)
    private RoleType role;
}
