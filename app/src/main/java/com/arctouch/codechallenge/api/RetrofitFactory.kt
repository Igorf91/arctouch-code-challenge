package com.arctouch.codechallenge.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitFactory {

    private var client = OkHttpClient.Builder().addInterceptor { chain ->
        var request = chain.request()
        val url = request.url()
            .newBuilder()
            .addQueryParameter("api_key", TmdbApi.API_KEY)
            .build()

        request = request
            .newBuilder()
            .url(url)
            .build()

        chain.proceed(request)
    }.build()

    fun getApi() : TmdbApi = Retrofit.Builder()
        .baseUrl(TmdbApi.URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(TmdbApi::class.java)
}