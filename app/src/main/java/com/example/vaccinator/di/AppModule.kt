package com.example.vaccinator.di

import android.os.Build
import android.os.LocaleList
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.vaccinator.VaccinatorApp
import com.example.vaccinator.data.remote.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun language(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList.getDefault().toLanguageTags()
        } else {
            Locale.getDefault().language
        }
    }

    @Provides
    @Singleton
    fun providesOkhttpInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val original: Request = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("Accept-Language", language())
                .header("Accept", "application/json")

            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideHttpClientBuilder(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(ChuckerInterceptor(VaccinatorApp.applicationContext()))
            .addInterceptor(loggingInterceptor)
            .build()


    @Provides
    @Singleton
    fun provideRetrofitForAuth(
        okHttpClient: OkHttpClient,
        interceptor: Interceptor
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://cdn-api.co-vin.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.newBuilder().addInterceptor(interceptor).build())
            .build()


    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api =
        retrofit
            .create(Api::class.java)


}