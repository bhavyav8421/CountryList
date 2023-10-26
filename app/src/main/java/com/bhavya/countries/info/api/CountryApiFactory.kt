package com.bhavya.countries.info.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountryApiFactory {
    fun create(okHttpClient: OkHttpClient? = null): CountryApi = run {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())

        okHttpClient?.let {
            retrofitBuilder.client(okHttpClient)
        }

        retrofitBuilder.build().create(CountryApi::class.java)
    }

    companion object {
        const val baseUrl = "https://gist.githubusercontent.com/peymano-wmt/"
    }
}