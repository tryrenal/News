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
import kotlinx.coroutines.runBlocking
import net.bytebuddy.utility.RandomString
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class SetFavoriteNewsUseCaseTest {
    lateinit var newsRespository: NewsRespository
    lateinit var useCase: SetFavoriteNewsUseCase

    @Before
    fun before(){
        newsRespository = mock()
        useCase = SetFavoriteNewsUseCase(newsRespository)
    }


    @Test
    fun testErrorClearFavoriteNews() = runBlocking{
        val input = SetFavoriteNewsUseCase.Input(
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
        val errorMessage = "error clear data"
        val output = SetFavoriteNewsUseCase.Output(
            mock(), mock()
        )

        whenever(newsRespository.insertFavoriteNews(input.data)).thenReturn(Result.Error(errorMessage))

        useCase.execute(input, output)

        verify(output.error).invoke(errorMessage)
        verify(output.success, never()).invoke()
    }

    @Test
    fun testSuccessClearFavoriteNews() = runBlocking {
        val input = SetFavoriteNewsUseCase.Input(
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
        val output = SetFavoriteNewsUseCase.Output(
            mock(), mock()
        )

        whenever(newsRespository.insertFavoriteNews(input.data)).thenReturn(Result.Success(Unit))

        useCase.execute(input, output)

        verify(output.error, never()).invoke(any())
        verify(output.success).invoke()
    }
}