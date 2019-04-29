package com.example.dawid.visitwroclove.view.activity;

public interface RegistrationView extends BaseView {
    void showLoadingScreen();
    void showError(String errorMessage);
    void savePremium(boolean isPremium);
    void saveEmail(String email);
    void savePassword(String password);
    void saveId(int id);
    void saveToken(String token);
}
