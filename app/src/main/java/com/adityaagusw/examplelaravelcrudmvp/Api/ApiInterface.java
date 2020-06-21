package com.adityaagusw.examplelaravelcrudmvp.Api;

import com.adityaagusw.examplelaravelcrudmvp.Model.GetMhs;
import com.adityaagusw.examplelaravelcrudmvp.Model.GetNote;
import com.adityaagusw.examplelaravelcrudmvp.Model.Mahasiswa;
import com.adityaagusw.examplelaravelcrudmvp.Model.MahasiswaResponse;
import com.adityaagusw.examplelaravelcrudmvp.Model.Note;
import com.adityaagusw.examplelaravelcrudmvp.Model.NoteResponse;
import com.adityaagusw.examplelaravelcrudmvp.Model.PaginationResponse;
import com.adityaagusw.examplelaravelcrudmvp.Model.User;
import com.adityaagusw.examplelaravelcrudmvp.Model.UserResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("api/auth/register")
    Call<UserResponse<User>> registUser(@Field("name") String name,
                                        @Field("email") String email,
                                        @Field("password") String password);

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<UserResponse<User>> loginUser(@Field("email") String email,
                                       @Field("password") String password);

    @FormUrlEncoded
    @POST("api/note/create")
    Call<NoteResponse<Note>> createNote(@Header("Authorization") String token,
                                        @Field("title") String title,
                                        @Field("content") String content);

    @Multipart
    @POST("api/mahasiswa/create")
    Call<MahasiswaResponse<Mahasiswa>> createMahasiswa(@Header("Authorization") String token,
                                                       @Part("nim") RequestBody nim,
                                                       @Part("nama") RequestBody nama,
                                                       @Part MultipartBody.Part foto);

    @GET("api/note/read")
    Call<GetNote<Note>> getNote(@Header("Authorization") String token);

    @GET("api/mahasiswa/read")
    Call<GetMhs<Mahasiswa>> getMahasiswa(@Header("Authorization") String token);

    //pagination
    @GET("api/note/paginate")
    Call<PaginationResponse<Note>> getNotePagination(@Header("Authorization") String token,
                                                     @Query("page") int page);

    //search
    @GET("api/note/search")
    Call<GetNote<Note>> getNoteSearch(@Header("Authorization") String token,
                                      @Query("search") String search);

}
