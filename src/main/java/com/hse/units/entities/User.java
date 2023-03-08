package com.hse.units.entities;

import java.util.Collection;

public class User {
    private Long id;
    private String username;
    private String password;

    Collection<Role> roles;

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }
}
