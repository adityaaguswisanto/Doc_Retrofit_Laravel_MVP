package com.adityaagusw.examplelaravelcrudmvp.Activity.Search;

import com.adityaagusw.examplelaravelcrudmvp.Model.Note;

import java.util.ArrayList;

public interface SearchVieww {

    void loadingProgress(boolean state);

    void onGetResult(ArrayList<Note> note);

    void onErrorLoading(String message);

}
