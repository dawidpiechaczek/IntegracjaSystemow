package com.example.dawid.visitwroclove.view.activity;

public interface LoginView extends BaseView{
    void showLoadingScreen(String accessToken, int id);
    void showError(String errorMessage);
}
