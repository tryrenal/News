package com.redveloper.news.di

import com.redveloper.news.api.NewsApiImpl
import com.redveloper.news.domain.repository.api.NewsApi
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ApiModule {
    @Binds
    @Singleton
    abstract fun bindSourceNewsApi(api: NewsApiImpl): NewsApi
}