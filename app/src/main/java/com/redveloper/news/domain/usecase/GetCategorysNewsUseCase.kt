package com.redveloper.news.domain.usecase

import com.redveloper.news.domain.enums.NewsCategoryEnum
import javax.inject.Inject

class GetCategorysNewsUseCase @Inject constructor() {

    fun execute(callback: (List<NewsCategoryEnum>) -> Unit){
        val items = listOf(
            NewsCategoryEnum.BUSINESS,
            NewsCategoryEnum.ENTERTAINMENT,
            NewsCategoryEnum.GENERAL,
            NewsCategoryEnum.HEALTH,
            NewsCategoryEnum.SCIENCE,
            NewsCategoryEnum.SPORTS,
            NewsCategoryEnum.TECHNOLOGY
        )

        callback.invoke(items)
    }
}