package com.niek125.rolemanager.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity(name = "user")
public class User {
    @Id
    private String userid;
    private String profilepicture;
    private String username;
    private String email;
}
