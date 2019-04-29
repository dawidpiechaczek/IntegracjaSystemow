package com.example.dawid.visitwroclove.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.presenter.RegistrationPresenter;
import com.example.dawid.visitwroclove.utils.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends BaseActivity implements RegistrationView {

    public static final String USER_EMAIL = "Shared.User.UserEmail";
    public static final String USER_PASSWORD = "Shared.User.UserPassword";
    public static final String USER_ID = "Shared.User.UserID";
    public static final String USER_ACCESS_TOKEN = "Shared.User.AccesToken";
    private static final String MY_PREFS_NAME = "token";
    private static final String PREMIUM = "premium";

    @BindView(R.id.registration_button)
    Button registrBtn;
    @BindView(R.id.registration_email_edittext)
    EditText email;
    @BindView(R.id.ragistration_password_first_edittext)
    EditText firstPassword;
    @BindView(R.id.ragistration_password_second_edittext)
    EditText secondPassword;

    private RegistrationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        getComponent().inject(this);
        presenter = new RegistrationPresenter();
        presenter.init(getContext());
        registrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Validation.isNetworkAvailable(view.getContext())) {
                    showError("brak Internetu");
                } else {
                    presenter.registration(email.getText().toString(),firstPassword.getText().toString(),secondPassword.getText().toString());
                }
            }
        });
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
    public void showLoadingScreen() {
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, "Błąd podczas logowania: " + errorMessage, Toast.LENGTH_LONG).show();
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
    public void saveId(int id) {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(USER_ID, id);
        editor.apply();
    }

    @Override
    public void saveToken(String token) {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(USER_ACCESS_TOKEN, token);
        editor.apply();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
