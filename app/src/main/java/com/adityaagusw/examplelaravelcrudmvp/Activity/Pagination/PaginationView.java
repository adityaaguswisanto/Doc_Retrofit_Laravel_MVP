package com.adityaagusw.examplelaravelcrudmvp.Activity.Pagination;

import com.adityaagusw.examplelaravelcrudmvp.Model.Note;

import java.util.ArrayList;

public interface PaginationView {

    void loadingProgress(boolean state);

    void loadingNextMore(boolean state);

    void onGetResult(ArrayList<Note> note);

    void onGetNext(ArrayList<Note> note);

    void onErrorLoading(String message);

}
