package com.adityaagusw.examplelaravelcrudmvp.Activity.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adityaagusw.examplelaravelcrudmvp.Activity.Auth.Login.LoginActivity;
import com.adityaagusw.examplelaravelcrudmvp.Activity.Lihat.Mahasiswa.LihatMhsActivity;
import com.adityaagusw.examplelaravelcrudmvp.Activity.Lihat.Note.LihatNoteActivity;
import com.adityaagusw.examplelaravelcrudmvp.Activity.Mahasiswa.InputMhsActivity;
import com.adityaagusw.examplelaravelcrudmvp.Activity.Note.InputNoteActivity;
import com.adityaagusw.examplelaravelcrudmvp.Activity.Pagination.PaginationActivity;
import com.adityaagusw.examplelaravelcrudmvp.Activity.Search.SearchActivity;
import com.adityaagusw.examplelaravelcrudmvp.R;
import com.adityaagusw.examplelaravelcrudmvp.Storage.SharedPrefManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnInputNote, btnInputMhs, btnLihatNote, btnLihatMhs, btnPagination, btnSearch, btnLogout;
    private TextView txtNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInputNote = findViewById(R.id.btnInputNote);
        btnInputMhs = findViewById(R.id.btnInputMhs);
        btnLihatNote = findViewById(R.id.btnLihatNote);
        btnLihatMhs = findViewById(R.id.btnLihatMahasiswa);
        btnPagination = findViewById(R.id.btnPagination);
        btnSearch = findViewById(R.id.btnSearch);
        btnLogout = findViewById(R.id.btnLogout);
        txtNama = findViewById(R.id.txtNama);

        txtNama.setText(SharedPrefManager.getInstance(MainActivity.this).getUser().getName());

        btnInputNote.setOnClickListener(this);
        btnInputMhs.setOnClickListener(this);
        btnLihatNote.setOnClickListener(this);
        btnLihatMhs.setOnClickListener(this);
        btnPagination.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInputNote:
                startActivity(new Intent(getApplicationContext(), InputNoteActivity.class));
                break;
            case R.id.btnInputMhs:
                startActivity(new Intent(getApplicationContext(), InputMhsActivity.class));
                break;
            case R.id.btnLihatNote:
                startActivity(new Intent(getApplicationContext(), LihatNoteActivity.class));
                break;
            case R.id.btnLihatMahasiswa:
                startActivity(new Intent(getApplicationContext(), LihatMhsActivity.class));
                break;
            case R.id.btnPagination:
                startActivity(new Intent(getApplicationContext(), PaginationActivity.class));
                break;
            case R.id.btnSearch:
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                break;
            case R.id.btnLogout:
                SharedPrefManager.getInstance(MainActivity.this).clear();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }
}