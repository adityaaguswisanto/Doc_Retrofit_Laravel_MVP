package com.adityaagusw.examplelaravelcrudmvp.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.adityaagusw.examplelaravelcrudmvp.Model.User;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    public SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    public void saveUser(User user) {
        SharedPreferences sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefManager.edit();

        editor.putInt("id", user.getId());
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("api_token", user.getApi_token());
        editor.putString("created_at", user.getCreated_at());
        editor.putString("updated_at", user.getUpdated_at());

        editor.apply();
    }

    public User getUser() {
        SharedPreferences sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPrefManager.getInt("id", -1),
                sharedPrefManager.getString("name", null),
                sharedPrefManager.getString("email", null),
                sharedPrefManager.getString("api_token", null),
                sharedPrefManager.getString("created_at", null),
                sharedPrefManager.getString("updated_at", null)
        );

    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPrefManager.getInt("id", -1) != -1;
    }

    public void clear(){
        SharedPreferences sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefManager.edit();
        editor.clear();
        editor.apply();
    }

}
