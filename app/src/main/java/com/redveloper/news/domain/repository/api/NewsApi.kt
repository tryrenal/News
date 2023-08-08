package com.redveloper.news.domain.repository.api

import com.redveloper.news.domain.enums.NewsCategoryEnum
import com.redveloper.news.domain.model.SourceNews

interface NewsApi {
    suspend fun getSourceNews(
        category: NewsCategoryEnum
    ): List<SourceNews>
}