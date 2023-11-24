package com.pexelsapi.imcollection.network

import android.content.Context
import com.pexelsapi.imcollection.BuildConfig
import com.pexelsapi.imcollection.network.ApiClient.Companion.API_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        private const val BASE_URL = BuildConfig.BASE_URL
        const val API_KEY = BuildConfig.API_KEY

        fun create(context: Context): ApiService {
            val client = OkHttpClient.Builder().apply {
                addInterceptor(BaseHeadersInterceptor())
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }.build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}

class BaseHeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request().newBuilder().apply {
            header(NetworkConstants.HEADER_KEY_AUTH, API_KEY)
        }.build()
        return chain.proceed(request)
    }

}