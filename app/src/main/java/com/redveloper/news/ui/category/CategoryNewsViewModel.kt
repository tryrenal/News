package com.redveloper.news.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redveloper.news.domain.enums.NewsCategoryEnum
import com.redveloper.news.domain.usecase.GetCategorysNewsUseCase
import com.redveloper.news.utils.Event
import javax.inject.Inject

class CategoryNewsViewModel @Inject constructor(
    private val getCategorysNewsUseCase: GetCategorysNewsUseCase
): ViewModel() {

    val categoryNewsEvent = MutableLiveData<Event<List<NewsCategoryEnum>>>()

    init {
        getCategorysNewsUseCase.execute {
            categoryNewsEvent.value = Event(it)
        }
    }
}