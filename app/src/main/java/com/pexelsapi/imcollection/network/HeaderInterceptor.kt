package com.pexelsapi.imcollection.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            header(NetworkConstants.HEADER_KEY_AUTH, apiKey)
        }.build()
        return chain.proceed(request)
    }
}