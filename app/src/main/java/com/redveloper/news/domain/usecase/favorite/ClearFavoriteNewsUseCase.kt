package com.redveloper.news.domain.usecase.favorite

import com.redveloper.news.domain.repository.NewsRespository
import com.redveloper.news.domain.repository.Result
import javax.inject.Inject

class ClearFavoriteNewsUseCase @Inject constructor(
    private val newsRespository: NewsRespository
) {

    suspend fun execute(newsId: Int, output: Output){
        val result = newsRespository.deleteFavoriteNews(newsId)

        when(result){
            is Result.Success -> {
                output.success.invoke()
            }
            is Result.Error -> {
                output.error.invoke(result.message)
            }
        }
    }

    data class Output(
        val success: (() -> Unit),
        val error: ((String) -> Unit)
    )
}