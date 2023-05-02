package ru.units.database;

import jakarta.persistence.*;

@Entity
@Table(name = User.TABLE_NAME)
public class User {
    public static final String TABLE_NAME = "users";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long uid;

    @Column(unique = true)
    String name;

    String info;

    String email;

    protected User() {
    }

    public User(String name, String info, String email) {
        this.name = name;
        this.info = info;
        this.email = email;
    }


    @Override
    public String toString() {
        return String.format("[name=`%s`, info=`%s`]", name, info);
    }

    public long getUid() {
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
}


