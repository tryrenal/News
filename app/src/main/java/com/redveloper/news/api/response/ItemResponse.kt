package com.redveloper.news.api.response

import com.google.gson.annotations.SerializedName
import com.redveloper.news.domain.model.Item

data class ItemResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
){
    fun toItem(): Item{
        return Item(
            id = id,
            name = name
        )
    }
}