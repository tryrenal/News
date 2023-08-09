package com.redveloper.news.domain.usecase.favorite

import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.domain.repository.NewsRespository
import com.redveloper.news.domain.repository.Result
import javax.inject.Inject

class SetFavoriteNewsUseCase @Inject constructor(
    private val newsRespository: NewsRespository
) {

    suspend fun execute(input: Input, output: Output){
        val result = newsRespository.insertFavoriteNews(
            input.data
        )
        when(result){
            is Result.Success -> {
                output.success.invoke()
            }
            is Result.Error -> {
                output.error.invoke(result.message)
            }
        }
    }

    data class Input(
        val data: HeadlineNews
    )

    data class Output(
        val success: (() -> Unit),
        val error: ((String) -> Unit)
    )
}