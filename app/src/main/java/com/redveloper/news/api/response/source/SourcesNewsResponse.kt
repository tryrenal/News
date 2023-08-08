package com.redveloper.news.api.response.source

import com.google.gson.annotations.SerializedName
import com.redveloper.news.domain.model.SourceNews

data class SourcesNewsResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("languange")
    val languange: String?,
    @SerializedName("country")
    val country: String?
){
    fun toSourceNews(): SourceNews {
        return SourceNews(
            id = id,
            name = name,
            description = description,
            url = url,
            category = category,
            languange = languange,
            country = country
        )
    }
}