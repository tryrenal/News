package com.redveloper.news.domain.usecase.favorite

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.domain.model.Item
import com.redveloper.news.domain.repository.NewsRespository
import com.redveloper.news.domain.repository.Result
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import net.bytebuddy.utility.RandomString
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class GetFavoritesNewsUseCaseTest {
    lateinit var newsRespository: NewsRespository
    lateinit var useCase: GetFavoritesNewsUseCase

    @Before
    fun before(){
        newsRespository = mock()
        useCase = GetFavoritesNewsUseCase(newsRespository)
    }

    @Test
    fun testErrorGetFavoriteNews() = runBlocking{
        val errorMessage = "error get favorite news"
        val output = GetFavoritesNewsUseCase.Output(
            mock(), mock()
        )

        whenever(newsRespository.getFavoritesNews()).thenReturn(flowOf(Result.Error(errorMessage)))

        useCase.execute(output)

        verify(output.error).invoke(errorMessage)
        verify(output.success, never()).invoke(any())
    }

    @Test
    fun testSuccessGetFavoriteNews() = runBlocking {
        val data = listOf(
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

        val output = GetFavoritesNewsUseCase.Output(
            mock(), mock()
        )
        whenever(newsRespository.getFavoritesNews()).thenReturn(flowOf(Result.Success(data)))

        useCase.execute(output)

        verify(output.error, never()).invoke(any())
        verify(output.success).invoke(data)
    }
}