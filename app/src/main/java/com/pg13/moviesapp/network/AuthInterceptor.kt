package com.pg13.moviesapp.network

import com.pg13.moviesapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor():Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader(API_KEY_HEADER, BuildConfig.API_KEY)
                .build()
        )
    }

    private companion object {

        private const val API_KEY_HEADER = "X-Api-Key"
    }
}