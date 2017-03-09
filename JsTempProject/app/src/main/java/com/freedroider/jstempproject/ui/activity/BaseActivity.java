package com.freedroider.jstempproject.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.freedroider.jstempproject.presentation.presenter.BasePresenter;
import com.freedroider.jstempproject.presentation.presenter.PresenterFactory;
import com.freedroider.jstempproject.presentation.presenter.PresenterManager;
import com.freedroider.jstempproject.presentation.view.BaseView;

import java.util.UUID;

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>>
        extends AppCompatActivity implements PresenterFactory<P> {
    private static final String BUNDLE_PRESENTER_IDENTIFIER = "bundle.PresenterIdentifier";

    private String identifier;
    private P presenter;

    @NonNull
    protected abstract V provideView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            identifier = savedInstanceState.getString(BUNDLE_PRESENTER_IDENTIFIER);
        } else {
            identifier = createIdentifier();
        }
        presenter = PresenterManager.getInstance().getPresenter(identifier, this);
        presenter.attachView(provideView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        if(isFinishing()) {
            PresenterManager.getInstance().remove(identifier);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(BUNDLE_PRESENTER_IDENTIFIER, identifier);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @NonNull
    protected String getIdentifier() {
        return identifier;
    }

    @NonNull
    protected String createIdentifier() {
        if (TextUtils.isEmpty(identifier)) {
            return UUID.randomUUID().toString();
        } else {
            return identifier;
        }
    }
}
