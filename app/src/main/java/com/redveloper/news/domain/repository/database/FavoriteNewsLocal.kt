package com.redveloper.news.domain.repository.database

import com.redveloper.news.domain.model.HeadlineNews
import kotlinx.coroutines.flow.Flow

interface FavoriteNewsLocal {
    fun getFavoritesNews(): Flow<List<HeadlineNews>>
    suspend fun insertFavoriteNews(data: HeadlineNews)
    suspend fun deleteFavoriteNews(newsId: Int)
}