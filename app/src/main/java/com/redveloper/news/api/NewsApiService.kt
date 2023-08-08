package com.redveloper.news.api

import com.redveloper.news.api.response.headline.RootHeadlinesNewsResponse
import com.redveloper.news.api.response.source.RootSourceNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("/v2/top-headlines/sources")
    suspend fun getSourcesNews(
        @Query("category") category: String
    ): RootSourceNewsResponse

    @GET("/v2/top-headlines")
    suspend fun getHeadlinesNews(
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): RootHeadlinesNewsResponse
}