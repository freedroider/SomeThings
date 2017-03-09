package com.freedroider.jstempproject.data.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.freedroider.jstempproject.data.LocalDataSource;

public class SqliteDataSource implements LocalDataSource {
    private final Context context;

    public SqliteDataSource(@NonNull Context context) {
        this.context = context;
    }
}
