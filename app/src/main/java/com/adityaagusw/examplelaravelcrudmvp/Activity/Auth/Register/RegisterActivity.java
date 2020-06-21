package com.adityaagusw.examplelaravelcrudmvp.Activity.Auth.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adityaagusw.examplelaravelcrudmvp.R;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity implements RegisterView, View.OnClickListener {

    private TextInputEditText edtName, edtEmail, edtPassword;
    private Button btnRegister;
    private ProgressDialog progressDialog;
    private RegisterPresenter presenter;

    private String name, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = findViewById(R.id.edtNameReg);
        edtEmail = findViewById(R.id.edtEmailReg);
        edtPassword = findViewById(R.id.edtPasswordReg);
        btnRegister = findViewById(R.id.btnRegisterReg);

        presenter = new RegisterPresenter(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please waiting....");

        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegisterReg:
                name = edtName.getText().toString().trim();
                email = edtEmail.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                presenter.registerUser(name, email, password);
                break;
        }
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
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}