package com.gbf.auth.dto;

/*@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)*/
public class AuthCredentionals {
    private String login;
    private String password;

    public AuthCredentionals() {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthCredentionals{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
