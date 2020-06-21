package com.adityaagusw.examplelaravelcrudmvp.Activity.Auth.Register;

public interface RegisterView {

    void showProgress();

    void hideProgress();

    void onRequestAccess(String message);

    void onRequestError(String message);

}
