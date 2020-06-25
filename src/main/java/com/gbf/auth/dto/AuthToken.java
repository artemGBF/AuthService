package com.gbf.auth.dto;

public class AuthToken implements Token{
    private String authToken;

    public AuthToken() {
    }

    public AuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
