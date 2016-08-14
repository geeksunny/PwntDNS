package com.radicalninja.pwntdns.rest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.annotation.Nullable;

public abstract class ResponseStateCallback<T> implements Callback<T> {

    public abstract void onSuccess(@Nullable T responseObject);

    public abstract void onError(Response<T> response);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!response.isSuccessful()) {
            onError(response);
        } else {
            onSuccess(response.body());
        }
    }

}
