package com.redveloper.news.domain.repository

import com.redveloper.news.domain.enums.NewsCategoryEnum
import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.domain.model.RootHeadlineNews
import com.redveloper.news.domain.model.SourceNews
import com.redveloper.news.domain.repository.api.NewsApi
import com.redveloper.news.domain.repository.database.FavoriteNewsLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRespository @Inject constructor(
    private val newsApi: NewsApi,
    private val favoriteNewsLocal: FavoriteNewsLocal
) {

    fun getSourceNews(categoryEnum: NewsCategoryEnum): Flow<Result<List<SourceNews>>>{
        return flow {
            try {
                val data = newsApi.getSourceNews(categoryEnum)
                emit(Result.Success(data))
            }catch (e: Exception){
                emit(Result.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getHeadlinesNews(
        source: String,
        query: String,
        page: Int,
        pageSize: Int
    ): Flow<Result<RootHeadlineNews>>{
        return flow {
            try {
                val data = newsApi.getHeadlinesNews(
                    query = query, source = source, page = page, pageSize = pageSize)
                emit(Result.Success(data))
            }catch (e: Exception){
                emit(Result.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getFavoritesNews(): Flow<Result<List<HeadlineNews>>>{
        return flow {
            try {
                val favorites = mutableListOf<HeadlineNews>()
                favoriteNewsLocal.getFavoritesNews().collect {
                    favorites.addAll(it)
                }
                emit(Result.Success(favorites))
            }catch (e: Exception){
                emit(Result.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertFavoriteNews(data: HeadlineNews): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                favoriteNewsLocal.insertFavoriteNews(data)
            }
            Result.Success(Unit)
        } catch (e: Exception){
            Result.Error(e.message.toString())
        }
    }

    suspend fun deleteFavoriteNews(url: String): Result<Unit>{
        return try {
            withContext(Dispatchers.IO){
                favoriteNewsLocal.deleteFavoriteNews(url)
            }
            Result.Success(Unit)
        } catch (e: Exception){
            Result.Error(e.message.toString())
        }
    }
}