package com.redveloper.news.di

import androidx.lifecycle.ViewModelProvider
import com.redveloper.news.MyApp
import com.redveloper.news.ui.articel.NewsArticelActivity
import com.redveloper.news.ui.category.CategoryNewsActivity
import com.redveloper.news.ui.source.SourceNewsActivity
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        NetworkModule::class,
        ApplicationModule::class,
        ViewModelModule::class,
        DatabaseModule::class
    ]
)
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(myApp: MyApp): Builder

        fun build(): ApplicationComponent
    }

    fun retrofit(): Retrofit

    fun viewModelProviderFactory(): ViewModelProvider.Factory
    fun inject(categoryNewsActivity: CategoryNewsActivity)
    fun inject(sourcesNewsActivity: SourceNewsActivity)
    fun inject(newsArticelActivity: NewsArticelActivity)
}