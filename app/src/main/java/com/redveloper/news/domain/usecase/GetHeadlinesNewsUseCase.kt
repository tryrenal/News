package com.redveloper.news.domain.usecase

import android.util.Log
import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.domain.repository.NewsRespository
import com.redveloper.news.domain.repository.Result
import javax.inject.Inject

class GetHeadlinesNewsUseCase @Inject constructor(
    private val newsRespository: NewsRespository
) {
    private var nextPage = 1
    private var isAbleToLoadMore = false
    private var lastInput: Input? = null
    var output: Output? = null

    suspend fun execute(source: String){
        nextPage = 1
        lastInput = Input(
            page = nextPage,
            source = source
        )

        lastInput?.let { input ->
            getHeadlines(input)
        }
    }

    suspend fun loadMore(){
        if (isAbleToLoadMore){
            lastInput?.let { input ->
                getHeadlines(Input(
                    page = nextPage,
                    source = input.source
                ))
            }
        }
    }

    private suspend fun getHeadlines(input: Input){
        newsRespository.getHeadlinesNews(
            source = input.source,
            page = input.page,
            pageSize = 20
        ).collect{ result ->
            when(result){
                is Result.Success -> {
                    nextPage += 1
                    val rootData = result.data
                    isAbleToLoadMore = rootData.totalResults >= (rootData.articels.size)
                    output?.success?.invoke(rootData.articels)
                }
                is Result.Error -> {
                    Log.i("errorGetHeadline", result.message)
                    output?.error?.invoke(result.message)
                }
            }
        }
    }

    data class Input(
        val page: Int,
        val source: String
    )

    data class Output(
        val success: ((List<HeadlineNews>) -> Unit)? = null,
        val error: ((String) -> Unit)? = null
    )
}