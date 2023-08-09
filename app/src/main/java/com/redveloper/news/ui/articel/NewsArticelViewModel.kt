package com.redveloper.news.ui.articel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.domain.usecase.GetHeadlinesNewsUseCase
import com.redveloper.news.domain.usecase.favorite.SetFavoriteNewsUseCase
import com.redveloper.news.utils.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsArticelViewModel @Inject constructor(
    private val getHeadlinesNewsUseCase: GetHeadlinesNewsUseCase,
    private val setFavoriteNewsUseCase: SetFavoriteNewsUseCase
): ViewModel() {

    init {
        getHeadlinesNewsUseCase.output = GetHeadlinesNewsUseCase.Output(
            success = {
                setLoading(false)
                articelsEvent.value = Event(it)
            },
            error = {
                setLoading(false)
                errorArticelEvent.value = Event(it)
            }
        )
    }

    val source = MutableStateFlow("")

    val articelsEvent = MutableLiveData<Event<List<HeadlineNews>>>()
    val errorArticelEvent = MutableLiveData<Event<String>>()
    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val addFavoriteEvent = MutableLiveData<Event<Any>>()

    val searchQuery = MutableStateFlow("")
    val searchResult = searchQuery
        .debounce(500)
        .distinctUntilChanged()
        .mapLatest {
            Event(it)
        }
        .asLiveData()

    fun getListArticel(query: String = ""){
        viewModelScope.launch {
            source.collectLatest {
                setLoading(true)
                getHeadlinesNewsUseCase.execute(source = it, query = query)
            }
        }
    }

    fun loadMore(){
        viewModelScope.launch {
            setLoading(true)
            getHeadlinesNewsUseCase.loadMore()
        }
    }

    fun addFavoriteNews(data: HeadlineNews){
        viewModelScope.launch {
            setFavoriteNewsUseCase.execute(
                input = SetFavoriteNewsUseCase.Input(data),
                output = SetFavoriteNewsUseCase.Output(
                    success = {
                        addFavoriteEvent.value = Event(Any())
                    },
                    error = {
                        errorArticelEvent.value = Event(it)
                    }
                )
            )
        }
    }

    private fun setLoading(show: Boolean){
        loadingEvent.value = Event(show)
    }
}