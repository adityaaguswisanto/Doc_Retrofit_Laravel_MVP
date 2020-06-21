package com.adityaagusw.examplelaravelcrudmvp.Activity.Lihat.Note;

import com.adityaagusw.examplelaravelcrudmvp.Model.Note;

import java.util.ArrayList;

public interface LihatNoteView {

    void loadingProgress(boolean state);

    void onGetResult(ArrayList<Note> note);

    void onErrorLoading(String message);
}
