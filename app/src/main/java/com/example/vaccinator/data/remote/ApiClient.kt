package com.example.vaccinator.data.remote

import android.os.Build
import android.os.LocaleList
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.vaccinator.VaccinatorApp
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

object ApiClient {
    class AcceptLanguageHeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest: Request = chain.request()
            val requestWithHeaders: Request = originalRequest.newBuilder()
                .header("Accept-Language", language)
                .header("Accept", "application/json")
                .build()
            return chain.proceed(requestWithHeaders)
        }

        private val language: String
            get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                LocaleList.getDefault().toLanguageTags()
            } else {
                Locale.getDefault().language
            }
    }

    private val okHttpBuilder = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(6, TimeUnit.SECONDS)


    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl("https://cdn-api.co-vin.in/")
        .addConverterFactory(GsonConverterFactory.create())

    val api = retrofitBuilder
        .client(okHttpBuilder.addNetworkInterceptor(AcceptLanguageHeaderInterceptor()).build())
        .build()
        .create(Api::class.java)
}