package com.gbf.auth.model;

import javax.persistence.Entity;

@Entity
public class Client extends User {

    public Client() {
    }

    public Client(String login, String password, String mail) {
        super(login, password, mail);
    }

    public String[] getTags(){
        return new String[]{"login", "mail", "createdDate"};
    }

    @Override
    public UserRole getRole() {
        return UserRole.CLIENT;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
