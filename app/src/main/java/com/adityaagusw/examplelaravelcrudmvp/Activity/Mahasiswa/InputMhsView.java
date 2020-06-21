package com.adityaagusw.examplelaravelcrudmvp.Activity.Mahasiswa;

public interface InputMhsView {
    void showProgress();

    void hideProgress();

    void onRequestAccess(String message);

    void onRequestError(String message);
}
