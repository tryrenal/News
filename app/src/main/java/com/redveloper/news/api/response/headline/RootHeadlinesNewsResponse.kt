package com.redveloper.news.api.response.headline

import com.google.gson.annotations.SerializedName
import com.redveloper.news.domain.model.RootHeadlineNews

data class RootHeadlinesNewsResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?,
    @SerializedName("articles")
    val articels:List<HeadlinesNewsResponse>
){
    fun toRootHeadlineNews(): RootHeadlineNews {
        return RootHeadlineNews(
            totalResults = totalResults ?: 0,
            articels = articels.map {
                it.toHeadlineNews()
            }
        )
    }
}