package com.adityaagusw.examplelaravelcrudmvp.Activity.Lihat.Note;

import androidx.annotation.NonNull;

import com.adityaagusw.examplelaravelcrudmvp.Activity.Note.InputNoteView;
import com.adityaagusw.examplelaravelcrudmvp.Api.ApiClient;
import com.adityaagusw.examplelaravelcrudmvp.Api.ApiInterface;
import com.adityaagusw.examplelaravelcrudmvp.Model.GetNote;
import com.adityaagusw.examplelaravelcrudmvp.Model.Note;
import com.adityaagusw.examplelaravelcrudmvp.Model.PaginationResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LihatNotePresenter {

    private LihatNoteView view;
    private ApiInterface apiInterface;

    public LihatNotePresenter(LihatNoteView view) {
        this.view = view;
        this.apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    void readNote(String token) {

        view.loadingProgress(true);

        Call<GetNote<Note>> call = apiInterface.getNote(token);

        call.enqueue(new Callback<GetNote<Note>>() {
            @Override
            public void onResponse(@NonNull Call<GetNote<Note>> call, @NonNull Response<GetNote<Note>> response) {
                view.loadingProgress(false);
                if (response.isSuccessful()) {

                    GetNote<Note> note = response.body();

                    assert note != null;
                    if (!note.isValue()) {

                        assert response.body() != null;
                        ArrayList<Note> list = response.body().getData();

                        if (list.isEmpty()) {
                            view.onErrorLoading("Data Kosong");
                        } else {
                            view.onGetResult(list);
                        }

                    }
                } else {
                    view.onErrorLoading("Terjadi Kesalahan");
                }

            }

            @Override
            public void onFailure(@NonNull Call<GetNote<Note>> call, @NonNull Throwable t) {
                view.loadingProgress(false);
                view.onErrorLoading(t.getMessage());
            }
        });

    }

}
