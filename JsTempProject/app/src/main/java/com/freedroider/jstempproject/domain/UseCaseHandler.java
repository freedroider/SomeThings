package com.freedroider.jstempproject.domain;

import android.support.annotation.NonNull;

import com.freedroider.jstempproject.domain.usecase.UseCase;

public class UseCaseHandler {
    private static volatile UseCaseHandler instance;
    private final UseCaseScheduler scheduler;

    @NonNull
    public static UseCaseHandler getInstance() {
        if(instance == null) {
            synchronized (UseCaseHandler.class) {
                if(instance == null) {
                    instance = new UseCaseHandler(new ThreadPoolScheduler());
                }
            }
        }
        return instance;
    }

    @NonNull
    public static UseCaseHandler getInstance(UseCaseScheduler scheduler) {
        return new UseCaseHandler(scheduler);
    }


    private UseCaseHandler(UseCaseScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public <Q extends UseCase.QueryValues, R extends UseCase.ResponseValues,
            E extends UseCase.ErrorValies> void execute(final UseCase<Q, R, E> useCase,
                                                        Q values,
                                                        UseCase.Callback<R, E> callback) {
        useCase.setQueryValues(values);
        useCase.setCallback(new UiCallbackWrapper<>(callback, scheduler));
        scheduler.execute(useCase);
    }


    private static class UiCallbackWrapper<R extends UseCase.ResponseValues,
            E extends UseCase.ErrorValies> implements UseCase.Callback<R, E> {
        private final UseCase.Callback<R, E> callback;
        private final UseCaseScheduler scheduler;

        UiCallbackWrapper(UseCase.Callback<R, E> callback, UseCaseScheduler scheduler) {
            this.callback = callback;
            this.scheduler = scheduler;
        }

        @Override
        public void onSuccess(R reponse) {
            scheduler.notifySuccess(reponse, callback);
        }

        @Override
        public void onError(E error) {
            scheduler.notifyError(error, callback);
        }
    }
}
