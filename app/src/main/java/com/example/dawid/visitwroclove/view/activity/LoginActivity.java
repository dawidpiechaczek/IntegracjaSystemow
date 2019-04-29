package com.example.dawid.visitwroclove.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.UserDTO;
import com.example.dawid.visitwroclove.presenter.LoginPresenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.dawid.visitwroclove.utils.Validation.validate;

public class LoginActivity extends BaseActivity implements LoginView {

    public static final String USER_ACCESS_TOKEN = "Shared.User.AccesToken";
    public static final String USER_ID = "Shared.User.UserID";
    public static final String USER_EMAIL = "Shared.User.UserEmail";
    public static final String USER_PASSWORD = "Shared.User.UserPassword";
    private static final String MY_PREFS_NAME = "token";
    private static final String PREMIUM = "premium";

    @BindView(R.id.login_button)
    Button loginBtn;
    @BindView(R.id.email_edittext)
    EditText emailEt;
    @BindView(R.id.password_edittext)
    EditText passwordEt;

    @OnClick(R.id.register_button)
    public void onRegistrationClick() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private LoginPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        SharedPreferences sharedPref = getSharedPreferences("token", Context.MODE_PRIVATE);
        String accessToken = sharedPref.getString(USER_ACCESS_TOKEN, "");

        if (accessToken.equals("")) {
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
            presenter = new LoginPresenter();
            presenter.init(getContext());

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validate(emailEt.getText().toString())) {
                        presenter.login(new UserDTO(emailEt.getText().toString(), passwordEt.getText().toString()));
                    } else {
                        showError("Błędny adres email, przykładowy email: przyklad@gmail.com");
                    }
                }
            });
        } else {
            Intent intent = new Intent(this, SplashScreenActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onResume() {
        presenter.attachView(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        presenter.detachView();
        super.onPause();
    }

    @Override
    public void showLoadingScreen(String accessToken, int id) {
        SharedPreferences sharedPref = getSharedPreferences("token",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_ACCESS_TOKEN, accessToken);
        editor.putInt(USER_ID, id);
        editor.apply();

        Intent intent = new Intent(this, SplashScreenActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, "Błąd podczas logowania: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void saveTokenToSharedPrefs(String token) {
        SharedPreferences.Editor editor = getSharedPreferences("token", MODE_PRIVATE).edit();
        editor.putString(USER_ACCESS_TOKEN, token);
        editor.apply();
    }

    @Override
    public String getToken() {
        SharedPreferences prefs = getSharedPreferences("token", MODE_PRIVATE);
        return prefs.getString(USER_ACCESS_TOKEN, "");
    }

    @Override
    public void savePremium(boolean isPremium) {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREMIUM, isPremium);
        editor.apply();
    }

    @Override
    public void saveEmail(String email) {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(USER_EMAIL, email);
        editor.apply();
    }

    @Override
    public void savePassword(String password) {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(USER_PASSWORD, password);
        editor.apply();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
