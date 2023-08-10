package com.redveloper.news.domain.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.redveloper.news.domain.enums.NewsCategoryEnum
import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.domain.model.Item
import com.redveloper.news.domain.model.RootHeadlineNews
import com.redveloper.news.domain.model.SourceNews
import com.redveloper.news.domain.repository.api.NewsApi
import com.redveloper.news.domain.repository.database.FavoriteNewsLocal
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import net.bytebuddy.utility.RandomString
import org.junit.Before
import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertEquals

class NewsRespositoryTest {
    lateinit var newsApi: NewsApi
    lateinit var favoriteNewsLocal: FavoriteNewsLocal
    lateinit var newsRespository: NewsRespository

    @Before
    fun before(){
        newsApi = mock()
        favoriteNewsLocal = mock()
        newsRespository = NewsRespository(
            newsApi, favoriteNewsLocal
        )
    }

    @Test
    fun testSuccessGetSourceNews() = runBlocking{
        val categoryEnum = NewsCategoryEnum.SCIENCE
        val fakeData = listOf(
            SourceNews(
                id = Random.nextInt(1, 10).toString(),
                name = RandomString.make(),
                description = RandomString.make(),
                url = RandomString.make(),
                category = RandomString.make(),
                languange = RandomString.make(),
                country = RandomString.make()
            )
        )

        whenever(newsApi.getSourceNews(categoryEnum)).thenReturn(fakeData)

        val result = newsRespository.getSourceNews(categoryEnum).single()

        assert(result is Result.Success)
        assertEquals(fakeData, (result as Result.Success).data)
    }

    @Test
    fun testErrorGetSourceNews() = runBlocking {
        val categoryEnum = NewsCategoryEnum.SCIENCE

        val error = "error get source news"

        whenever(newsApi.getSourceNews(categoryEnum)).thenThrow(RuntimeException(error))

        val result = newsRespository.getSourceNews(categoryEnum).single()

        assert(result is Result.Error)
        assertEquals(error, (result as Result.Error).message)
    }

    @Test
    fun testSuccessGetHeadlinesNews() = runBlocking{
        val source = RandomString.make()
        val query = RandomString.make()
        val page = 1
        val pageSize = 20

        val fakeData = RootHeadlineNews(
            totalResults = Random.nextInt(10, 20),
            articels = listOf(
                HeadlineNews(
                    source = Item(
                        id = RandomString.make(),
                        name = RandomString.make()
                    ),
                    author = RandomString.make(),
                    title = RandomString.make(),
                    description = RandomString.make(),
                    url = RandomString.make(),
                    urlToImage = RandomString.make(),
                    content = RandomString.make()
                )
            )
        )

        whenever(newsApi.getHeadlinesNews(
            source, query, page, pageSize
        )).thenReturn(fakeData)

        val result = newsRespository.getHeadlinesNews(
            source, query, page, pageSize
        ).single()

        assert(result is Result.Success)
        assertEquals(fakeData, (result as Result.Success).data)
    }

    @Test
    fun testErrorGetHeadlines() = runBlocking{
        val error = "error get headlines"
        val source = RandomString.make()
        val query = RandomString.make()
        val page = 1
        val pageSize = 20

        whenever(newsApi.getHeadlinesNews(
            source, query, page, pageSize
        )).thenThrow(RuntimeException(error))

        val result = newsRespository.getHeadlinesNews(
            source, query, page, pageSize
        ).single()

        assert(result is Result.Error)
        assertEquals(error, (result as Result.Error).message)
    }

    @Test
    fun testSuccessGetFavoriteNews() = runBlocking {
        val fakeData = listOf(
            HeadlineNews(
                source = Item(
                    id = RandomString.make(),
                    name = RandomString.make()
                ),
                author = RandomString.make(),
                title = RandomString.make(),
                description = RandomString.make(),
                url = RandomString.make(),
                urlToImage = RandomString.make(),
                content = RandomString.make()
            )
        )

        whenever(favoriteNewsLocal.getFavoritesNews()).thenReturn(flowOf(fakeData))

        val result = newsRespository.getFavoritesNews().single()

        assert(result is Result.Success)
        assertEquals(fakeData, (result as Result.Success).data)
    }

    @Test
    fun testErrorGetFavoriteNews() = runBlocking {
        val error = "error get favorite news"

        whenever(favoriteNewsLocal.getFavoritesNews()).thenThrow(RuntimeException(error))

        val result = newsRespository.getFavoritesNews().single()

        assert(result is Result.Error)
        assertEquals(error, (result as Result.Error).message)
    }


    @Test
    fun testSuccessInsertFavoriteNews() = runBlocking {
        val fakeData = HeadlineNews(
            source = Item(
                id = RandomString.make(),
                name = RandomString.make()
            ),
            author = RandomString.make(),
            title = RandomString.make(),
            description = RandomString.make(),
            url = RandomString.make(),
            urlToImage = RandomString.make(),
            content = RandomString.make()
        )

        whenever(favoriteNewsLocal.insertFavoriteNews(fakeData)).thenReturn(Unit)

        val result = newsRespository.insertFavoriteNews(fakeData)

        assert(result is Result.Success)
    }

    @Test
    fun testErrorInsertFavoriteNews() = runBlocking {
        val fakeData = HeadlineNews(
            source = Item(
                id = RandomString.make(),
                name = RandomString.make()
            ),
            author = RandomString.make(),
            title = RandomString.make(),
            description = RandomString.make(),
            url = RandomString.make(),
            urlToImage = RandomString.make(),
            content = RandomString.make()
        )
        val error = "error insert data"

        whenever(favoriteNewsLocal.insertFavoriteNews(fakeData)).thenThrow(RuntimeException(error))

        val result = newsRespository.insertFavoriteNews(fakeData)

        assert(result is Result.Error)
        assertEquals(error, (result as Result.Error).message)
    }

    @Test
    fun testSuccessDeleteFavoriteNews() = runBlocking {
        val url = RandomString.make()

        whenever(favoriteNewsLocal.deleteFavoriteNews(url)).thenReturn(Unit)

        val result = newsRespository.deleteFavoriteNews(url)

        assert(result is Result.Success)
    }

    @Test
    fun testErrorDeleteFavoriteNews() = runBlocking {
        val url = RandomString.make()
        val error = "error delete data"

        whenever(favoriteNewsLocal.deleteFavoriteNews(url)).thenThrow(RuntimeException(error))

        val result = newsRespository.deleteFavoriteNews(url)

        assert(result is Result.Error)
        assertEquals(error, (result as Result.Error).message)
    }
}