package com.redveloper.news.di

import com.redveloper.news.database.NewsDatabase
import com.redveloper.news.database.favorite.FavoriteNewsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {

    @Provides
    @Singleton
    fun provideFavoriteNewsDao(db: NewsDatabase): FavoriteNewsDao{
        return db.favoriteNewsDao()
    }
}