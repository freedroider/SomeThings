package com.freedroider.jstempproject.domain;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.freedroider.jstempproject.domain.usecase.UseCase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SuppressWarnings("WeakerAccess")
public class ThreadPoolScheduler implements UseCaseScheduler {
    private static final int MIN_POOL_SIZE = 4;
    private static int poolSize;

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        poolSize = availableProcessors < MIN_POOL_SIZE ? MIN_POOL_SIZE : availableProcessors;
    }

    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private final Executor executor;

    public ThreadPoolScheduler() {
        executor = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public void execute(@NonNull Runnable command) {
        executor.execute(command);
    }

    @Override
    public <R extends UseCase.ResponseValues, E extends UseCase.ErrorValies>
    void notifySuccess(final R response, final UseCase.Callback<R, E> callback) {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response);
            }
        });
    }

    @Override
    public <R extends UseCase.ResponseValues, E extends UseCase.ErrorValies>
    void notifyError(final E error, final UseCase.Callback<R, E> callback) {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(error);
            }
        });
    }
}
