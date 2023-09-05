package com.pg13.moviesapp.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor():Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader("X-API-KEY", "04b1f88b-77ac-4aa4-acbd-1b05279f655a")
                .build()
        )
    }
}