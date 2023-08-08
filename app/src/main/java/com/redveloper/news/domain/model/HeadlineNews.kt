package com.redveloper.news.domain.model

import java.util.Date

data class HeadlineNews(
    val source: Item?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: Date?,
    val content: String?
)