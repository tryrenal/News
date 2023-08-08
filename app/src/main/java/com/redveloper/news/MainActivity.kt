package com.redveloper.news

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.redveloper.news.domain.enums.NewsCategoryEnum
import com.redveloper.news.ui.ViewModelFactory
import com.redveloper.news.ui.source.SourceNewsViewModel
import com.redveloper.news.ui.theme.NewsTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

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

                    MainScreen(viewModel = viewModel)
                }
            }
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