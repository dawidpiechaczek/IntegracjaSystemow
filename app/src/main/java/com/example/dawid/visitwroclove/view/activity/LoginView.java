package com.example.dawid.visitwroclove.view.activity;

public interface LoginView extends BaseView{
    void showLoadingScreen(String accessToken, int id);
    void showError(String errorMessage);
    void saveTokenToSharedPrefs(String token);
    String getToken();
    void savePremium(boolean isPremium);
    void saveEmail(String email);
    void savePassword(String password);
}
