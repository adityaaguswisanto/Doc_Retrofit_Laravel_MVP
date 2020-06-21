package com.adityaagusw.examplelaravelcrudmvp.Activity.Note;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adityaagusw.examplelaravelcrudmvp.R;
import com.adityaagusw.examplelaravelcrudmvp.Storage.SharedPrefManager;
import com.google.android.material.textfield.TextInputEditText;

public class InputNoteActivity extends AppCompatActivity implements InputNoteView, View.OnClickListener {

    private TextInputEditText edtTitle, edtContent;
    private Button btnPostNote;
    private InputNotePresenter presenter;
    private ProgressDialog progressDialog;

    private String title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_note);

        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        btnPostNote = findViewById(R.id.btnPostNote);

        presenter = new InputNotePresenter(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Waiting...");

        btnPostNote.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPostNote:

                title = edtTitle.getText().toString().trim();
                content = edtContent.getText().toString().trim();

                presenter.postNote("Bearer " + SharedPrefManager.getInstance(this).getUser().getApi_token(), title, content);

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