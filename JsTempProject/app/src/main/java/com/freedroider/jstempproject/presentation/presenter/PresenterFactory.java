package com.freedroider.jstempproject.presentation.presenter;

public interface PresenterFactory<P extends BasePresenter> {
    P providePresenter();
}
