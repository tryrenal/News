package com.redveloper.news.ui.source

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.redveloper.news.MyApp
import com.redveloper.news.domain.enums.NewsCategoryEnum
import com.redveloper.news.ui.ViewModelFactory
import com.redveloper.news.ui.source.SourceNewsViewModel
import com.redveloper.news.ui.theme.NewsTheme
import javax.inject.Inject

class SourceNewsActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel: SourceNewsViewModel by viewModels { viewModelFactory }

    fun inject(){
        (application as MyApp).applicationComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        setContent {
            NewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    intent.extras?.let {
                        val strCategory = it.getString(KEY_CATEGORY, null)
                        strCategory?.let {
                            val category = NewsCategoryEnum.valueOf(it)
                            LaunchedEffect(true){
                                viewModel.getSourcesNews(category)
                            }
                        }
                    }

                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }

    companion object{
        private const val KEY_CATEGORY = "KEY_CATEGORY"

        fun navigate(activity: Activity, category: NewsCategoryEnum){
            val intent = Intent(activity, SourceNewsActivity::class.java)
            intent.putExtra(KEY_CATEGORY, category.name)
            activity.startActivity(intent)
        }
    }
}

@Composable
fun MainScreen(
    viewModel: SourceNewsViewModel
){
    LaunchedEffect(true){
        viewModel.getSourcesNews(NewsCategoryEnum.GENERAL)
    }

    val sourceNewsEvent by viewModel.sourcesNewsEvent.observeAsState()
    val errorNewsEvent by viewModel.errorNewsEvent.observeAsState()

    sourceNewsEvent?.contentIfNotHaveBeenHandle?.let {
        Log.i("dataSourceNews", it.toString())
    }

    errorNewsEvent?.contentIfNotHaveBeenHandle?.let {
        Log.i("dataSourceNews", "error: $it")
    }
}