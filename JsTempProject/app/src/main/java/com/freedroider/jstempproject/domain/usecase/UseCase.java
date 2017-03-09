package com.freedroider.jstempproject.domain.usecase;

public abstract class UseCase<Q extends UseCase.QueryValues, R extends UseCase.ResponseValues,
        E extends UseCase.ErrorValies> implements Runnable {
    private Q queryValues;
    private Callback<R, E> callback;

    protected abstract void executeUseCase(Q queryValues);

    public void setQueryValues(Q queryValues) {
        this.queryValues = queryValues;
    }

    public Q getQueryValues() {
        return queryValues;
    }

    public void setCallback(Callback<R, E> callback) {
        this.callback = callback;
    }

    public Callback<R, E> getCallback() {
        return callback;
    }

    @Override
    public final void run() {
        executeUseCase(queryValues);
    }

    public interface QueryValues  {
    }

    public interface ResponseValues {
    }

    public interface ErrorValies {
    }

    public interface Callback<R, E> {
        void onSuccess(R reponse);

        void onError(E error);
    }
}
