package com.redveloper.news.di

import android.content.Context
import androidx.room.Room
import com.redveloper.news.database.NewsDatabase
import com.redveloper.news.database.favorite.FavoriteNewsDao
import com.redveloper.news.database.favorite.FavoriteNewsLocalImpl
import com.redveloper.news.domain.repository.database.FavoriteNewsLocal
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DaoModule::class])
class DatabaseModule {

    @Provides
    @Singleton
    fun provideNewsDatabase(context: Context): NewsDatabase{
        return Room.databaseBuilder(
            context, NewsDatabase::class.java, "News-DB"
        ).build()
    }

    @Provides
    @Singleton
    fun bindFavoriteNewsDatabase(dao: FavoriteNewsDao): FavoriteNewsLocal {
        return FavoriteNewsLocalImpl(dao)
    }
}