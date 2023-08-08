package com.redveloper.news.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [RetrofitApiServiceModule::class])
class NetworkModule {

    private val API_KEY = "8eacc946ac5a47bbbb3db718227daae8"

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val referrerInterceptor = Interceptor { chain ->
            val chainBuilder = chain.request()

            val urlWithApiKey = chainBuilder.url.newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = chainBuilder.newBuilder()
                .url(urlWithApiKey)
                .build()

            chain.proceed(request)
        }
        builder.addInterceptor(referrerInterceptor)
        builder.addInterceptor(ChuckerInterceptor.Builder(context).build())
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}