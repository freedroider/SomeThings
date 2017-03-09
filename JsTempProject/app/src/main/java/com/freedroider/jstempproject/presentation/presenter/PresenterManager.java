package com.freedroider.jstempproject.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;

import com.freedroider.jstempproject.presentation.view.BaseView;

public class PresenterManager {
    private static volatile PresenterManager instance;

    public static PresenterManager getInstance() {
        if(instance == null) {
            synchronized (PresenterManager.class) {
                if(instance == null) {
                    instance = new PresenterManager();
                }
            }
        }
        return instance;
    }

    private PresenterManager() {
    }

    private final LruCache<String, BasePresenter> cache = new LruCache<>(8);

    @SuppressWarnings("unchecked")
    public final <V extends BaseView, P extends BasePresenter<V>>
    P getPresenter(@NonNull String key, @NonNull PresenterFactory<P> factory) {
        P presenter = null;
        try {
            presenter = (P) cache.get(key);
        } catch (ClassCastException exception) {
            Log.w("PresenterManager", "Duplicate presenter with key: " + key);
        }
        if(presenter == null) {
            presenter = factory.providePresenter();
            cache.put(key, presenter);
        }
        return presenter;
    }

    public void remove(@NonNull String key) {
        cache.remove(key);
    }
}
