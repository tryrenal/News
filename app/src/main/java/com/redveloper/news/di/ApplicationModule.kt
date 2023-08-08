package com.redveloper.news.di

import android.content.Context
import com.redveloper.news.MyApp
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideAppContext(myApp: MyApp): Context = myApp.applicationContext


}