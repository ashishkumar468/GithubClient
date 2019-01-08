package com.android.githubpoc.presenters.interfaces;

public interface BasePresenter<T> {
    void attachView(T view);

    void detachView();
}
