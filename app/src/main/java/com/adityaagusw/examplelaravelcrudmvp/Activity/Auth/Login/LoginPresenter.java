package com.adityaagusw.examplelaravelcrudmvp.Activity.Auth.Login;

import android.content.Context;

import androidx.annotation.NonNull;

import com.adityaagusw.examplelaravelcrudmvp.Api.ApiClient;
import com.adityaagusw.examplelaravelcrudmvp.Api.ApiInterface;
import com.adityaagusw.examplelaravelcrudmvp.Model.User;
import com.adityaagusw.examplelaravelcrudmvp.Model.UserResponse;
import com.adityaagusw.examplelaravelcrudmvp.Storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {

    private LoginView view;
    private ApiInterface apiInterface;
    private Context context;

    public LoginPresenter(LoginView view) {
        this.view = view;
        this.apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    void loginUser(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            view.onRequestError("Is Empty Field !!!");
        } else {
            view.showProgress();
            Call<UserResponse<User>> call = apiInterface.loginUser(email, password);

            call.enqueue(new Callback<UserResponse<User>>() {
                @Override
                public void onResponse(@NonNull Call<UserResponse<User>> call, @NonNull Response<UserResponse<User>> response) {
                    view.hideProgress();

                    if (response.isSuccessful()) {

                        UserResponse<User> userResponse = response.body();
                        assert userResponse != null;

                        if (!userResponse.isValue()) {
                            SharedPrefManager.getInstance(context).saveUser(userResponse.getData());
                            view.onRequestAccess(userResponse.getMessage());
                        } else {
                            view.onRequestError(userResponse.getMessage());
                        }

                    } else {
                        view.onRequestError("Is Wrong...!");
                    }

                }

                @Override
                public void onFailure(@NonNull Call<UserResponse<User>> call, @NonNull Throwable t) {
                    view.hideProgress();
                    view.onRequestError(t.getMessage());
                }
            });

        }

    }

}
