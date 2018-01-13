package com.petarvelikov.currencytracker.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ApiResponse<T> {

    @Nullable
    private T response;
    @Nullable
    private Throwable error;

    public ApiResponse(@NonNull T response) {
        this.response = response;
        this.error = null;
    }

    public ApiResponse(@NonNull Throwable error) {
        this.response = null;
        this.error = error;
    }

    @Nullable
    public T getResponse() {
        return this.response;
    }

    @Nullable
    public Throwable getError() {
        return this.error;
    }
}
