package com.redveloper.news.domain.usecase

import com.redveloper.news.domain.enums.NewsCategoryEnum
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetCategorysNewsUseCaseTest {
    lateinit var useCase: GetCategorysNewsUseCase

    @Before
    fun before(){
        useCase = GetCategorysNewsUseCase()
    }

    @Test
    fun testResultCategorys() {
        lateinit var result: List<NewsCategoryEnum>

        useCase.execute {
            result = it
        }

        val expected = listOf(
            NewsCategoryEnum.BUSINESS,
            NewsCategoryEnum.ENTERTAINMENT,
            NewsCategoryEnum.GENERAL,
            NewsCategoryEnum.HEALTH,
            NewsCategoryEnum.SCIENCE,
            NewsCategoryEnum.SPORTS,
            NewsCategoryEnum.TECHNOLOGY
        )

        assertEquals(expected, result)
    }
}