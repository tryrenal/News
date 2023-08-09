package com.redveloper.news.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.domain.usecase.favorite.ClearFavoriteNewsUseCase
import com.redveloper.news.domain.usecase.favorite.GetFavoritesNewsUseCase
import com.redveloper.news.utils.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteNewsViewModel @Inject constructor(
    private val getFavoritesNewsUseCase: GetFavoritesNewsUseCase,
    private val clearFavoriteNewsUseCase: ClearFavoriteNewsUseCase,
): ViewModel() {

    val favoriteNewsEvent = MutableLiveData<Event<List<HeadlineNews>>>()
    val clearFavoriteEVent = MutableLiveData<Event<Any>>()
    val errorEvent = MutableLiveData<Event<String>>()

    fun getFavoritesNews(){
        viewModelScope.launch {
            getFavoritesNewsUseCase.execute(
                GetFavoritesNewsUseCase.Output(
                    success = {
                        favoriteNewsEvent.value = Event(it)
                    },
                    error = {
                        errorEvent.value = Event(it)
                    }
                )
            )
        }
    }

    fun clearFavoriteNews(newsId: Int){
        viewModelScope.launch {
            clearFavoriteNewsUseCase.execute(
                newsId = newsId,
                output = ClearFavoriteNewsUseCase.Output(
                    success = {
                        clearFavoriteEVent.value = Event(Any())
                    },
                    error = {
                        errorEvent.value = Event(it)
                    }
                )
            )
        }
    }
}