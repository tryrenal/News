package com.redveloper.news.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.redveloper.news.domain.repository.Result
import com.redveloper.news.domain.enums.NewsCategoryEnum
import com.redveloper.news.domain.model.SourceNews
import com.redveloper.news.domain.repository.NewsRespository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import net.bytebuddy.utility.RandomString
import org.junit.Before
import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertEquals

class GetSourcesNewsUseCaseTest {
    lateinit var newsRespository: NewsRespository
    lateinit var useCase: GetSourcesNewsUseCase

    @Before
    fun before(){
        newsRespository = mock()
        useCase = GetSourcesNewsUseCase(
            newsRespository
        )
    }

    @Test
    fun testErrorGetSourceNews() = runBlocking {
        lateinit var result: String
        val category = NewsCategoryEnum.GENERAL
        val errorMessage = "error get source news"

        whenever(newsRespository.getSourceNews(category)).thenReturn(flowOf(Result.Error(errorMessage)))

        useCase.execute(
            category = category,
            output = GetSourcesNewsUseCase.Output(
                success = {},
                error = {
                    result = it
                }
            )
        )

        assertEquals(errorMessage, result)
    }

    @Test
    fun testSuccessGetSourceNews() = runBlocking {
        val category = NewsCategoryEnum.GENERAL
        val sourcesNews = listOf(
            SourceNews(
                id = Random.nextInt(1, 10).toString(),
                name = RandomString.make(),
                description = RandomString.make(),
                url = RandomString.make(),
                category = category.value,
                languange = "en",
                country = "in"
            )
        )
        lateinit var result: List<SourceNews>

        whenever(newsRespository.getSourceNews(category)).thenReturn(flowOf(Result.Success(sourcesNews)))

        useCase.execute(
            category = category,
            output = GetSourcesNewsUseCase.Output(
                success = {
                    result = it
                },
                error = {}
            )
        )

        assertEquals(sourcesNews, result)
    }
}