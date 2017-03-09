package com.freedroider.jstempproject.presentation.presenter;

import android.support.annotation.NonNull;

import com.freedroider.jstempproject.presentation.view.BaseView;

public interface BasePresenter<V extends BaseView> {
    void attachView(@NonNull V view);

    void detachView();
}
