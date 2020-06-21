package com.adityaagusw.examplelaravelcrudmvp.Activity.Note;

import android.content.Context;

import androidx.annotation.NonNull;

import com.adityaagusw.examplelaravelcrudmvp.Api.ApiClient;
import com.adityaagusw.examplelaravelcrudmvp.Api.ApiInterface;
import com.adityaagusw.examplelaravelcrudmvp.Model.Note;
import com.adityaagusw.examplelaravelcrudmvp.Model.NoteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputNotePresenter {

    private InputNoteView view;
    private ApiInterface apiInterface;

    public InputNotePresenter(InputNoteView view) {
        this.view = view;
        this.apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    void postNote(String token, String title, String content) {

        if (title.isEmpty() || content.isEmpty()) {
            view.onRequestError("Field is Empty");
        } else {
            view.showProgress();
            Call<NoteResponse<Note>> call = apiInterface.createNote(token, title, content);

            call.enqueue(new Callback<NoteResponse<Note>>() {
                @Override
                public void onResponse(@NonNull Call<NoteResponse<Note>> call, @NonNull Response<NoteResponse<Note>> response) {
                    view.hideProgress();
                    if (response.isSuccessful()) {

                        NoteResponse<Note> note = response.body();

                        assert note != null;
                        if (!note.isValue()) {
                            view.onRequestAccess(note.getMessage());
                        } else {
                            view.onRequestError(note.getMessage());
                        }

                    } else {
                        view.onRequestError("Kesalahan Membuat Note");
                    }

                }

                @Override
                public void onFailure(@NonNull Call<NoteResponse<Note>> call, @NonNull Throwable t) {
                    view.hideProgress();
                    view.onRequestError(t.getMessage());
                }
            });
        }

    }

}
