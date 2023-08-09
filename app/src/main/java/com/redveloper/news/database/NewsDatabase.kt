package com.redveloper.news.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.redveloper.news.database.favorite.FavoriteNewsDao
import com.redveloper.news.database.favorite.FavoriteNewsEntity

@Database(
    entities = [FavoriteNewsEntity::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun favoriteNewsDao(): FavoriteNewsDao
}