package com.example.dawid.visitwroclove.model;

public class LoggedUserDTO {
    private String accessToken;
    User UserObject;
    private String expiresIn;
    RefreshToken RefreshTokenObject;


    // Getter Methods

    public String getAccessToken() {
        return accessToken;
    }

    public User getUser() {
        return UserObject;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public RefreshToken getRefreshToken() {
        return RefreshTokenObject;
    }

    // Setter Methods

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setUser(User userObject) {
        this.UserObject = userObject;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setRefreshToken(RefreshToken refreshTokenObject) {
        this.RefreshTokenObject = refreshTokenObject;
    }
}
class RefreshToken {
    private String token;
    private String issuedUtc;
    private String expiresUtc;


    // Getter Methods

    public String getToken() {
        return token;
    }

    public String getIssuedUtc() {
        return issuedUtc;
    }

    public String getExpiresUtc() {
        return expiresUtc;
    }

    // Setter Methods

    public void setToken(String token) {
        this.token = token;
    }

    public void setIssuedUtc(String issuedUtc) {
        this.issuedUtc = issuedUtc;
    }

    public void setExpiresUtc(String expiresUtc) {
        this.expiresUtc = expiresUtc;
    }
}
class User {
    private float id;
    private String firstName;
    private String lastName;
    private String roles = null;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRoles() {
        return roles;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}