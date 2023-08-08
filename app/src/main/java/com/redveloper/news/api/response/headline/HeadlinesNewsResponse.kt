package com.redveloper.news.api.response.headline

import com.google.gson.annotations.SerializedName
import com.redveloper.news.api.response.ItemResponse
import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.utils.convertStrDateToDate
import java.util.Date

data class HeadlinesNewsResponse(
    @SerializedName("source")
    val source: ItemResponse?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("content")
    val content: String?
){
    fun toHeadlineNews(): HeadlineNews{
        return HeadlineNews(
            source = source?.toItem(),
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = if (!publishedAt.isNullOrBlank())
                convertStrDateToDate(publishedAt) else Date(),
            content = content
        )
    }
}