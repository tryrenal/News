package com.redveloper.news.domain.repository

import com.redveloper.news.domain.enums.NewsCategoryEnum
import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.domain.model.RootHeadlineNews
import com.redveloper.news.domain.model.SourceNews
import com.redveloper.news.domain.repository.api.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRespository @Inject constructor(
    private val newsApi: NewsApi
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
        page: Int,
        pageSize: Int
    ): Flow<Result<RootHeadlineNews>>{
        return flow {
            try {
                val data = newsApi.getHeadlinesNews(
                    source = source, page = page, pageSize = pageSize)
                emit(Result.Success(data))
            }catch (e: Exception){
                emit(Result.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}