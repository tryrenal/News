package com.redveloper.news.api.response

import com.google.gson.annotations.SerializedName

data class RootSourceNewsResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("sources")
    val sources: List<SourcesNewsResponse>
)