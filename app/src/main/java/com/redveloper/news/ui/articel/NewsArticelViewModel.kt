package com.redveloper.news.ui.articel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.domain.usecase.GetHeadlinesNewsUseCase
import com.redveloper.news.utils.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
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

    val searchQuery = MutableStateFlow("")
    val searchResult = searchQuery
        .debounce(3000)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotBlank()
        }
        .mapLatest {
            Event(it)
        }.asLiveData()

    fun getListArticel(source: String, query: String = ""){
        viewModelScope.launch {
            getHeadlinesNewsUseCase.execute(source = source, query = query)
        }
    }

    fun loadMore(){
        viewModelScope.launch {
            getHeadlinesNewsUseCase.loadMore()
        }
    }
}