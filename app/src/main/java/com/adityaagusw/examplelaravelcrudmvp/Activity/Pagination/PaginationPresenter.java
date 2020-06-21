package com.adityaagusw.examplelaravelcrudmvp.Activity.Pagination;

import android.content.Context;

import androidx.annotation.NonNull;

import com.adityaagusw.examplelaravelcrudmvp.Api.ApiClient;
import com.adityaagusw.examplelaravelcrudmvp.Api.ApiInterface;
import com.adityaagusw.examplelaravelcrudmvp.Model.Note;
import com.adityaagusw.examplelaravelcrudmvp.Model.PaginationResponse;
import com.adityaagusw.examplelaravelcrudmvp.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaginationPresenter {

    private PaginationView view;
    private ApiInterface apiInterface;
    private Context context;

    public PaginationPresenter(PaginationView view) {
        this.view = view;
        this.apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    void getNote(String token, int page){
        view.loadingProgress(true);

        Call<PaginationResponse<Note>> call = apiInterface.getNotePagination(token, page);

        call.enqueue(new Callback<PaginationResponse<Note>>() {
            @Override
            public void onResponse(@NonNull Call<PaginationResponse<Note>> call, @NonNull Response<PaginationResponse<Note>> response) {
                view.loadingProgress(false);
                if (response.isSuccessful()){

                    assert response.body() != null;
                    ArrayList<Note> list = response.body().getData();

                    if (list.isEmpty()){
                        view.onErrorLoading("Data Kosong");
                    }else{
                        view.onGetResult(list);
                    }

                }else{
                    view.onErrorLoading("Terjadi Kesalahan...");
                }

            }

            @Override
            public void onFailure(@NonNull Call<PaginationResponse<Note>> call,@NonNull Throwable t) {
                view.loadingProgress(false);
                view.onErrorLoading(t.getMessage());
            }
        });
    }

    void loadNextMore(int page){
        view.loadingNextMore(true);

        Call<PaginationResponse<Note>> call = apiInterface.getNotePagination("Bearer " + SharedPrefManager.getInstance(context).getUser().getApi_token(), page);

        call.enqueue(new Callback<PaginationResponse<Note>>() {
            @Override
            public void onResponse(@NonNull Call<PaginationResponse<Note>> call, @NonNull Response<PaginationResponse<Note>> response) {
                view.loadingNextMore(false);
                if (response.isSuccessful()){

                    assert response.body() != null;
                    ArrayList<Note> list = response.body().getData();

                    if (list.isEmpty()){
                        view.onErrorLoading("Data Kosong");
                    }else{
                        view.onGetNext(list);
                    }

                }else{
                    view.onErrorLoading("Terjadi Kesalahan...");
                }

            }

            @Override
            public void onFailure(@NonNull Call<PaginationResponse<Note>> call,@NonNull Throwable t) {
                view.loadingNextMore(false);
                view.onErrorLoading(t.getMessage());
            }
        });
    }

}
