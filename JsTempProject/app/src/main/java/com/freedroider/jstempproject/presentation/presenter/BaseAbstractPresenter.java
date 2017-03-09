package com.freedroider.jstempproject.presentation.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.freedroider.jstempproject.presentation.view.BaseView;

public abstract class BaseAbstractPresenter<V extends BaseView> implements BasePresenter<V>{
    private V view;

    @Override
    public void attachView(@NonNull V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Nullable
    public V getView() {
        return view;
    }

    protected boolean isDetached() {
        return view == null;
    }

    protected boolean isAttached() {
        return view != null;
    }
}
