package com.gbf.auth.model;


import javax.persistence.Entity;

@Entity
public class Admin extends User{
    public Admin() {
    }

    public Admin(String login, String password, String mail) {
        super(login, password, mail);
    }

    @Override
    public UserRole getRole() {
        return UserRole.ADMIN;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
