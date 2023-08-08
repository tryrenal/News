package com.redveloper.news.api

import com.redveloper.news.domain.enums.NewsCategoryEnum
import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.domain.model.RootHeadlineNews
import com.redveloper.news.domain.model.SourceNews
import com.redveloper.news.domain.repository.api.NewsApi
import javax.inject.Inject

class NewsApiImpl @Inject constructor(
    private val apiService: NewsApiService
): NewsApi {
    override suspend fun getSourceNews(category: NewsCategoryEnum): List<SourceNews> {
        return apiService.getSourcesNews(
            category.value
        ).sources.map {
            it.toSourceNews()
        }
    }

    override suspend fun getHeadlinesNews(
        source: String,
        query: String,
        page: Int,
        pageSize: Int
    ): RootHeadlineNews {
        return apiService.getHeadlinesNews(
            sources = source, page = page, pageSize = pageSize, query = query
        ).toRootHeadlineNews()
    }
}