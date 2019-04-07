package com.example.dawid.visitwroclove.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RegistrationDTO {
    @SerializedName("email")
    private String email;
    @SerializedName("normalizedEmail")
    private String normalizedEmail;
    @SerializedName("password")
    private String password;
    @SerializedName("creationDate")
    private Date creationDate;
    @SerializedName("refreshToken")
    private String refreshToken;
    @SerializedName("login")
    private String login;
    @SerializedName("isAdmin")
    private Boolean isAdmin;
    @SerializedName("isPremium")
    private Boolean isPremium;
    @SerializedName("refreshTokenCreatedDate")
    private Date refreshTokenCreatedDate;
    @SerializedName("refreshTokenExpiryDate")
    private Date refreshTokenExpiryDate;
    @SerializedName("id")
    private int id;
    @SerializedName("userName")
    private String userName;

    public RegistrationDTO(String email, String password){
        this.email = email;
        this.normalizedEmail = email;
        this.userName = email;
        this.login = email;
        this.creationDate = new Date();
        this.refreshToken = "test";
        this.isAdmin = true;
        this.isPremium = false;
        this.refreshTokenCreatedDate = new Date();
        this.refreshTokenExpiryDate = new Date();
        this.password = password;
        this.id = 0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNormalizedEmail() {
        return normalizedEmail;
    }

    public void setNormalizedEmail(String normalizedEmail) {
        this.normalizedEmail = normalizedEmail;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getPremium() {
        return isPremium;
    }

    public void setPremium(Boolean premium) {
        isPremium = premium;
    }

    public Date getRefreshTokenCreatedDate() {
        return refreshTokenCreatedDate;
    }

    public void setRefreshTokenCreatedDate(Date refreshTokenCreatedDate) {
        this.refreshTokenCreatedDate = refreshTokenCreatedDate;
    }

    public Date getRefreshTokenExpiryDate() {
        return refreshTokenExpiryDate;
    }

    public void setRefreshTokenExpiryDate(Date refreshTokenExpiryDate) {
        this.refreshTokenExpiryDate = refreshTokenExpiryDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
