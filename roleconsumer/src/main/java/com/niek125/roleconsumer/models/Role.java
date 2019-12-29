package com.niek125.roleconsumer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity(name = "role")
public class Role {
    @Id
    private String roleid;
    private String userid;
    private String projectid;
    @Enumerated(EnumType.STRING)
    private RoleType role;
}
