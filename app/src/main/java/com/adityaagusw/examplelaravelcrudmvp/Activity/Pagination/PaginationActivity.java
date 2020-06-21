package com.adityaagusw.examplelaravelcrudmvp.Activity.Pagination;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adityaagusw.examplelaravelcrudmvp.Adapter.AdapterNote;
import com.adityaagusw.examplelaravelcrudmvp.Model.Note;
import com.adityaagusw.examplelaravelcrudmvp.R;
import com.adityaagusw.examplelaravelcrudmvp.Storage.SharedPrefManager;

import java.util.ArrayList;

public class PaginationActivity extends AppCompatActivity implements PaginationView{

    private RecyclerView recyclerView;
    private AdapterNote adapterNote;
    private ProgressBar progressBar;

    private PaginationPresenter presenter;

    //for pagination
    private static final int START_PAGE = 1;
    private int TOTAL_PAGES = 10;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int CURRENT_PAGE = START_PAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagination);

        recyclerView = findViewById(R.id.recyclerViewPagi);
        progressBar = findViewById(R.id.progressBarPagi);
        presenter = new PaginationPresenter(this);

        LinearLayoutManager linear = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linear);
        recyclerView.setHasFixedSize(true);

        presenter.getNote("Bearer " + SharedPrefManager.getInstance(this).getUser().getApi_token(), CURRENT_PAGE);

        recyclerView.addOnScrollListener(new Pagination(linear) {
            @Override
            protected void loadMoreItem() {
                isLoading = true;
                CURRENT_PAGE += 1;
                //here
                presenter.loadNextMore(CURRENT_PAGE);
            }

            @Override
            protected int getTotalPages() {
                return TOTAL_PAGES;
            }

            @Override
            protected boolean isLastPage() {
                return isLastPage;
            }

            @Override
            protected boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Override
    public void loadingProgress(boolean state) {
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadingNextMore(boolean state) {
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            isLoading = false;
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGetResult(ArrayList<Note> note) {
        adapterNote = new AdapterNote(this, note);
        recyclerView.setAdapter(adapterNote);
    }

    @Override
    public void onGetNext(ArrayList<Note> note) {
        adapterNote.addAll(note);
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}