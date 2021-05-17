package com.rdani.fortnightly.api

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    companion object {
        const val API_KEY = "f71af7261c434b5d8be60816ed910d8b"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        request = request
            .newBuilder()
            .addHeader("X-Api-Key", API_KEY)
            .build()

        return chain.proceed(request)
    }
}