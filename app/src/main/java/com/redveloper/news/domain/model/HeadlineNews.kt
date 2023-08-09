package com.redveloper.news.domain.model

data class HeadlineNews(
    val id: Int = 0,
    val source: Item?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val content: String?
)