package com.niek125.rolemanager.models;

public class Role {
    private String projectid;
    private String role;

    public Role() {
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
