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

    protected User() {
    }

    public User(String name, String info) {
        this.name = name;
        this.info = info;
    }


    @Override
    public String toString() {
        return String.format("[User UID=%d, name=`%s`, info=`%s`]", uid, name, info);
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
}


