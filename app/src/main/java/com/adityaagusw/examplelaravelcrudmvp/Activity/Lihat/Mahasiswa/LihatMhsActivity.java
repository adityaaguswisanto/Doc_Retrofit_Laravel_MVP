package com.adityaagusw.examplelaravelcrudmvp.Activity.Lihat.Mahasiswa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import com.adityaagusw.examplelaravelcrudmvp.Adapter.AdapterMhs;
import com.adityaagusw.examplelaravelcrudmvp.Model.Mahasiswa;
import com.adityaagusw.examplelaravelcrudmvp.R;
import com.adityaagusw.examplelaravelcrudmvp.Storage.SharedPrefManager;

import java.util.ArrayList;

public class LihatMhsActivity extends AppCompatActivity implements LihatMhsView {

    private RecyclerView recyclerView;
    private LihatMhsPresenter presenter;
    private SwipeRefreshLayout swipeRefreshLayoutMhs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_mhs);

        swipeRefreshLayoutMhs = findViewById(R.id.swipeRefreshMhs);
        recyclerView = findViewById(R.id.recyclerViewMhs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        presenter = new LihatMhsPresenter(this);
        presenter.readMhs("Bearer " + SharedPrefManager.getInstance(this).getUser().getApi_token());

        swipeRefreshLayoutMhs.setOnRefreshListener(() -> {
            presenter.readMhs("Bearer " + SharedPrefManager.getInstance(this).getUser().getApi_token());
        });

    }

    @Override
    public void loadingProgress(boolean state) {
        if (state){
            swipeRefreshLayoutMhs.setRefreshing(true);
        }else{
            swipeRefreshLayoutMhs.setRefreshing(false);
        }
    }

    @Override
    public void onGetResult(ArrayList<Mahasiswa> mahasiswa) {
        AdapterMhs adapter = new AdapterMhs(this, mahasiswa);
        adapter.setAdapterMhs(mahasiswa);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}