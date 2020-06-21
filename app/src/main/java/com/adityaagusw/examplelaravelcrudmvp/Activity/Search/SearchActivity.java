package com.adityaagusw.examplelaravelcrudmvp.Activity.Search;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adityaagusw.examplelaravelcrudmvp.Adapter.AdapterNote;
import com.adityaagusw.examplelaravelcrudmvp.Model.Note;
import com.adityaagusw.examplelaravelcrudmvp.R;
import com.adityaagusw.examplelaravelcrudmvp.Storage.SharedPrefManager;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchVieww, SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private AdapterNote adapterNote;
    private SearchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerViewSearchNote);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        presenter = new SearchPresenter(this);

    }

    @Override
    public void loadingProgress(boolean state) {
        if (state){
            Toast.makeText(this, "Sedang Loading", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Tidak Loading", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetResult(ArrayList<Note> note) {
        adapterNote = new AdapterNote(this, note);
        adapterNote.setAdapterNote(note);
        recyclerView.setAdapter(adapterNote);
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText != null){
            presenter.getNoteSearch("Bearer " + SharedPrefManager.getInstance(this).getUser().getApi_token(), newText);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Cari title...");
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        return true;

    }
}