package com.redveloper.news.database.favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteNewsDao{
    @Query("SELECT * FROM favorite_news")
    fun getFavoritesNews(): Flow<List<FavoriteNewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserFavoriteNews(data: FavoriteNewsEntity)

    @Query("DELETE FROM favorite_news WHERE id = :newsId")
    suspend fun deleteFavoriteNews(newsId: Int)
}