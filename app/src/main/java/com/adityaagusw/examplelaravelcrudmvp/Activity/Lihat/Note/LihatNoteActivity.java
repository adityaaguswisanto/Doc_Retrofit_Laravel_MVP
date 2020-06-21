package com.adityaagusw.examplelaravelcrudmvp.Activity.Lihat.Note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.adityaagusw.examplelaravelcrudmvp.Adapter.AdapterNote;
import com.adityaagusw.examplelaravelcrudmvp.Model.Note;
import com.adityaagusw.examplelaravelcrudmvp.R;
import com.adityaagusw.examplelaravelcrudmvp.Storage.SharedPrefManager;

import java.util.ArrayList;

public class LihatNoteActivity extends AppCompatActivity implements LihatNoteView {

    private RecyclerView recyclerView;
    private LihatNotePresenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_note);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshNote);
        recyclerView = findViewById(R.id.recyclerViewNote);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        presenter = new LihatNotePresenter(this);
        presenter.readNote("Bearer " + SharedPrefManager.getInstance(this).getUser().getApi_token());

        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.readNote("Bearer " + SharedPrefManager.getInstance(this).getUser().getApi_token());
        });

    }

    @Override
    public void loadingProgress(boolean state) {
        if (state){
            swipeRefreshLayout.setRefreshing(true);
        }else{
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onGetResult(ArrayList<Note> note) {
        AdapterNote adapter = new AdapterNote(this, note);
        adapter.setAdapterNote(note);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}