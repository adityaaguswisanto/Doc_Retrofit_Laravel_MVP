package com.adityaagusw.examplelaravelcrudmvp.Activity.Auth.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adityaagusw.examplelaravelcrudmvp.Activity.Auth.Register.RegisterActivity;
import com.adityaagusw.examplelaravelcrudmvp.Activity.Main.MainActivity;
import com.adityaagusw.examplelaravelcrudmvp.R;
import com.adityaagusw.examplelaravelcrudmvp.Storage.SharedPrefManager;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private TextInputEditText edtEmail, edtPassword;
    private Button btnLogin, btnRegister;
    private ProgressDialog progressDialog;
    private LoginPresenter presenter;

    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        presenter = new LoginPresenter(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void onRequestAccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                email = edtEmail.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                presenter.loginUser(email, password);
                break;
            case R.id.btnRegister:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

    }
}