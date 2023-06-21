package com.hse.units.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = User.TABLE_NAME)
public class User implements UserDetails {
    public static final String TABLE_NAME = "users";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long uid;


    @Column(unique = true)
    private String name;

    private String info;

    private String firstName;

    private String lastName;

    private String email;

    private String activationCode;

    private String password;

    private String availableForms;

    public User(String name, String info, String email) {
        this.name = name;
        this.info = info;
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("[name=`%s`, info=`%s`]", name, info);
    }

    public Long getUid() {
        return uid;
    }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public String getAvailableForms() {
        return availableForms;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public void addForm(Long id) {
        if (availableForms == null) {
            availableForms = "";
        }
        this.availableForms += id.toString() + " ";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
