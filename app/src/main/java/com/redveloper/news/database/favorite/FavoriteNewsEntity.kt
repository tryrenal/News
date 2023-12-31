package com.redveloper.news.database.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_news")
data class FavoriteNewsEntity(
    @PrimaryKey
    @ColumnInfo(name ="url")
    val url: String,
    @ColumnInfo(name = "source_id")
    val sourceId: String,
    @ColumnInfo(name = "source_name")
    val sourceName: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "url_to_image")
    val urlToImage: String,
    @ColumnInfo(name = "content")
    val content: String
)