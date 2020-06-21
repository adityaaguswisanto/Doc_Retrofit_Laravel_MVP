package com.adityaagusw.examplelaravelcrudmvp.Model;

public class UserResponse<T> {

    private boolean value;
    private String message;
    private T data;

    public UserResponse(boolean value, String message, T data) {
        this.value = value;
        this.message = message;
        this.data = data;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
