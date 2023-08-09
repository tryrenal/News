package com.redveloper.news.database.favorite

import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.domain.model.Item
import com.redveloper.news.domain.repository.database.FavoriteNewsLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteNewsLocalImpl @Inject constructor(
    private val favoriteNewsDao: FavoriteNewsDao
) : FavoriteNewsLocal {
    override fun getFavoritesNews(): Flow<List<HeadlineNews>> {
        return favoriteNewsDao.getFavoritesNews().map {
            it.map {
                HeadlineNews(
                    id = it.id  ?: 0,
                    source = Item(
                        id = it.sourceId,
                        name = it.sourceName
                    ),
                    author = it.author,
                    title = it.title,
                    description = it.description,
                    url = it.url,
                    urlToImage = it.urlToImage,
                    content = it.content
                )
            }
        }
    }

    override suspend fun insertFavoriteNews(data: HeadlineNews) {
        val entity = FavoriteNewsEntity(
            sourceId = data.source?.id ?: "",
            sourceName = data.source?.name ?: "",
            author = data.author ?: "",
            title = data.title ?: "",
            description = data.description ?: "",
            url = data.url ?: "",
            urlToImage = data.urlToImage ?: "",
            content = data.content ?: ""
        )
        return favoriteNewsDao.inserFavoriteNews(entity)
    }

    override suspend fun deleteFavoriteNews(newsId: Int) {
        return favoriteNewsDao.deleteFavoriteNews(newsId)
    }
}