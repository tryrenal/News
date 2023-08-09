package com.redveloper.news.domain.usecase.favorite

import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.domain.repository.NewsRespository
import com.redveloper.news.domain.repository.Result
import javax.inject.Inject

class GetFavoritesNewsUseCase @Inject constructor(
    private val newsRespository: NewsRespository
) {

    suspend fun execute(output: Output){
        newsRespository.getFavoritesNews()
            .collect{ result ->
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
        val success: ((List<HeadlineNews>) -> Unit),
        val error: ((String) -> Unit)
    )
}