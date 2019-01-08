package com.android.githubpoc.repository.communucations;

import com.android.githubpoc.model.response.RestResponse;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class ResponseSingleObserverHelper<T> implements SingleObserver<T> {
    private final RestResponseCallback callback;
    private T t;
    private RestResponse<T> restResponse;
    private Disposable disposable;

    public ResponseSingleObserverHelper(RestResponseCallback callback) {
        this.callback = callback;
        restResponse = new RestResponse();
    }

    @Override public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override public void onSuccess(T t) {
        restResponse.setData(t);
        restResponse.setStatus(true);
        restResponse.setMessage("Success");
        callback.onApiResponse(restResponse);
    }

    @Override public void onError(Throwable t) {
        restResponse.setStatus(false);
        restResponse.setMessage(t.getMessage());
        callback.onApiResponse(restResponse);
    }

    public void dispose() {
        this.disposable.dispose();
    }
}