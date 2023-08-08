package com.redveloper.news.domain.model

data class RootHeadlineNews(
    val totalResults: Int,
    val articels: List<HeadlineNews>
)