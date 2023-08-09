package com.redveloper.news.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.redveloper.news.ui.ViewModelFactory
import com.redveloper.news.ui.articel.NewsArticelViewModel
import com.redveloper.news.ui.category.CategoryNewsViewModel
import com.redveloper.news.ui.favorite.FavoriteNewsViewModel
import com.redveloper.news.ui.source.SourceNewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SourceNewsViewModel::class)
    abstract fun bindSourceNewsViewModel(viewmodel: SourceNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoryNewsViewModel::class)
    abstract fun bindCategoryNewsViewModel(viewModel: CategoryNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsArticelViewModel::class)
    abstract fun bindNewsArticelViewModel(viewModel: NewsArticelViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteNewsViewModel::class)
    abstract fun bindFavoriteNewsViewModel(viewModel: FavoriteNewsViewModel): ViewModel
}