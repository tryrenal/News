package com.redveloper.news.ui.source

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.news.domain.enums.NewsCategoryEnum
import com.redveloper.news.domain.model.SourceNews
import com.redveloper.news.domain.usecase.GetSourcesNewsUseCase
import com.redveloper.news.utils.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class SourceNewsViewModel @Inject constructor(
  private val getSourcesNewsUseCase: GetSourcesNewsUseCase
): ViewModel() {

  val sourcesNewsEvent = MutableLiveData<Event<List<SourceNews>>>()
  val errorNewsEvent = MutableLiveData<Event<String>>()

  fun getSourcesNews(category: NewsCategoryEnum){
    viewModelScope.launch {
      getSourcesNewsUseCase.execute(category = category,
        output = GetSourcesNewsUseCase.Output(
          success = {
            sourcesNewsEvent.value = Event(it)
          },
          error = {
            errorNewsEvent.value = Event(it)
          }
        )
      )
    }
  }
}