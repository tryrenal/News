package com.redveloper.news.domain.usecase.favorite

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.redveloper.news.domain.repository.NewsRespository
import com.redveloper.news.domain.repository.Result
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ClearFavoriteNewsUseCaseTest {
    lateinit var newsRespository: NewsRespository
    lateinit var useCase: ClearFavoriteNewsUseCase

    @Before
    fun before(){
        newsRespository = mock()
        useCase = ClearFavoriteNewsUseCase(
            newsRespository
        )
    }

    @Test
    fun testErrorClearFavoriteNews() = runBlocking{
        val errorMessage = "error clear data"
        val newsId = 10
        val output = ClearFavoriteNewsUseCase.Output(
            mock(), mock()
        )

        whenever(newsRespository.deleteFavoriteNews(newsId)).thenReturn(Result.Error(errorMessage))

        useCase.execute(newsId, output)

        verify(output.error).invoke(errorMessage)
        verify(output.success, never()).invoke()
    }

    @Test
    fun testSuccessClearFavoriteNews() = runBlocking {
        val output = ClearFavoriteNewsUseCase.Output(
            mock(), mock()
        )
        val newsId = 10

        whenever(newsRespository.deleteFavoriteNews(newsId)).thenReturn(Result.Success(Unit))

        useCase.execute(newsId, output)

        verify(output.error, never()).invoke(any())
        verify(output.success).invoke()
    }
}