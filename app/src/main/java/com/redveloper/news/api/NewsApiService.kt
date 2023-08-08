package com.redveloper.news.api

import com.redveloper.news.api.response.RootSourceNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("/v2/top-headlines/sources")
    suspend fun getSourcesNews(
        @Query("category") category: String
    ): RootSourceNewsResponse
}