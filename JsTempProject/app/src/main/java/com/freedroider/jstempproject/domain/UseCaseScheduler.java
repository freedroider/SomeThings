package com.freedroider.jstempproject.domain;

import android.support.annotation.NonNull;

import com.freedroider.jstempproject.domain.usecase.UseCase;

public interface UseCaseScheduler {
    void execute(@NonNull Runnable command);

    <R extends UseCase.ResponseValues, E extends UseCase.ErrorValies>
    void notifySuccess(R response, UseCase.Callback<R, E> callback);

    <R extends UseCase.ResponseValues, E extends UseCase.ErrorValies>
    void notifyError(E error, UseCase.Callback<R, E> callback);
}
