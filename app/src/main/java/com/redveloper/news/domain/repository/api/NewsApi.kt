package com.redveloper.news.domain.repository.api

import com.redveloper.news.domain.enums.NewsCategoryEnum
import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.domain.model.RootHeadlineNews
import com.redveloper.news.domain.model.SourceNews

interface NewsApi {
    suspend fun getSourceNews(
        category: NewsCategoryEnum
    ): List<SourceNews>

    suspend fun getHeadlinesNews(
        source: String,
        page: Int,
        pageSize: Int
    ): RootHeadlineNews
}