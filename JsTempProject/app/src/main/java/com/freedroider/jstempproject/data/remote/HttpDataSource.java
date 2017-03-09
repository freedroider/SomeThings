package com.freedroider.jstempproject.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.freedroider.jstempproject.data.RemoteDataSource;

public class HttpDataSource implements RemoteDataSource {
    private final Context context;

    public HttpDataSource(@NonNull Context context) {
        this.context = context;
    }
}
