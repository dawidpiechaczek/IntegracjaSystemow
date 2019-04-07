package com.example.dawid.visitwroclove.view.activity;

import android.content.Intent;
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
}
