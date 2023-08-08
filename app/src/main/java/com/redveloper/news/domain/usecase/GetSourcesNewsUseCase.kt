package com.redveloper.news.domain.usecase

import com.redveloper.news.domain.repository.Result
import com.redveloper.news.domain.enums.NewsCategoryEnum
import com.redveloper.news.domain.model.SourceNews
import com.redveloper.news.domain.repository.NewsRespository
import javax.inject.Inject

class GetSourcesNewsUseCase @Inject constructor(
    private val newsRespository: NewsRespository
) {

    suspend fun execute(category: NewsCategoryEnum, output: Output){
        newsRespository.getSourceNews(category)
            .collect { result ->
                when(result){
                    is Result.Success -> {
                        output.success.invoke(result.data)
                    }
                    is Result.Error -> {
                        output.error.invoke(result.message)
                    }
                }
            }
    }

    data class Output(
        val success: ((List<SourceNews>) -> Unit),
        val error: ((String) -> Unit)
    )
}