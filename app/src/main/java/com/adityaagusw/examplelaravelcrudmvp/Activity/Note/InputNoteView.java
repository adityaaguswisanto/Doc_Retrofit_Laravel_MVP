package com.adityaagusw.examplelaravelcrudmvp.Activity.Note;

public interface InputNoteView {
    void showProgress();

    void hideProgress();

    void onRequestAccess(String message);

    void onRequestError(String message);
}
