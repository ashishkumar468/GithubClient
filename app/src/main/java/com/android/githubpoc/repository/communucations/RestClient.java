package com.android.githubpoc.repository.communucations;

import com.android.githubpoc.model.PullRequest;
import com.android.githubpoc.repository.communucations.interfaces.IData;
import com.android.githubpoc.utils.MiscUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.Single;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient implements IData {
    private Retrofit retrofit;
    private OkHttpClient.Builder httpClient;
    private static RestClient instance;

    public static RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    private RestClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(MiscUtils.isProduction() ? HttpLoggingInterceptor.Level.NONE
            : HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(ApiConstants.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        httpClient.readTimeout(ApiConstants.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        httpClient.addInterceptor(loggingInterceptor);
        retrofit = new Retrofit.Builder().baseUrl(ApiConstants.REST_API.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build();
    }

    @Override
    public Single<List<PullRequest>> getIssues(String username, String reponame,
        HashMap<String,String> queryMap) {
        IData service = retrofit.create(IData.class);
        return service.getIssues(username, reponame,queryMap);
    }
}