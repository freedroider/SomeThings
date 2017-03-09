package com.freedroider.jstempproject.core.di;


import android.content.Context;
import android.support.annotation.NonNull;

import com.freedroider.jstempproject.data.local.SqliteDataSource;
import com.freedroider.jstempproject.data.remote.HttpDataSource;
import com.freedroider.jstempproject.data.LocalDataSource;
import com.freedroider.jstempproject.data.RemoteDataSource;
import com.freedroider.jstempproject.domain.UseCaseHandler;

public final class Injection {
    @NonNull
    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    @NonNull
    public static LocalDataSource provideLocalDataSource(Context context) {
        return new SqliteDataSource(context);
    }

    @NonNull
    public static RemoteDataSource provideRemoteDataSource(Context context) {
        return new HttpDataSource(context);
    }
}
