package com.redveloper.news.ui.articel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.domain.usecase.GetHeadlinesNewsUseCase
import com.redveloper.news.utils.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsArticelViewModel @Inject constructor(
    private val getHeadlinesNewsUseCase: GetHeadlinesNewsUseCase
): ViewModel() {

    init {
        getHeadlinesNewsUseCase.output = GetHeadlinesNewsUseCase.Output(
            success = {
                articelsEvent.value = Event(it)
            },
            error = {
                errorArticelEvent.value = Event(it)
            }
        )
    }

    val articelsEvent = MutableLiveData<Event<List<HeadlineNews>>>()
    val errorArticelEvent = MutableLiveData<Event<String>>()

    fun getListArticel(source: String){
        viewModelScope.launch {
            getHeadlinesNewsUseCase.execute(source)
        }
    }

    fun loadMore(){
        viewModelScope.launch {
            getHeadlinesNewsUseCase.loadMore()
        }
    }
}