package com.redveloper.news.ui.source

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.redveloper.news.MyApp
import com.redveloper.news.domain.enums.NewsCategoryEnum
import com.redveloper.news.domain.model.SourceNews
import com.redveloper.news.ui.ViewModelFactory
import com.redveloper.news.ui.articel.NewsArticelActivity
import com.redveloper.news.ui.components.CardSourceNews
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
                    val sourceNewsEvent by viewModel.sourcesNewsEvent.observeAsState()
                    val errorNewsEvent by viewModel.errorNewsEvent.observeAsState()

                    val sources = remember { mutableListOf<SourceNews>() }

                    errorNewsEvent?.contentIfNotHaveBeenHandle?.let {
                        Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
                    }

                    sourceNewsEvent?.contentIfNotHaveBeenHandle?.let {
                        sources.addAll(it)
                    }

                    intent.extras?.let {
                        val strCategory = it.getString(KEY_CATEGORY, null)
                        strCategory?.let {
                            val category = NewsCategoryEnum.valueOf(it)

                            LaunchedEffect(true){
                                sources.clear()
                                viewModel.getSourcesNews(category)
                            }
                        }
                    }

                    MainScreen(
                        modifier = Modifier
                            .fillMaxSize(),
                        sources = sources,
                        onSourceNewsClick = {
                            NewsArticelActivity.navigate(this,it)
                        },
                        onBackPress = {
                            finish()
                        }
                    )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    sources: List<SourceNews>,
    onSourceNewsClick: (source: String) -> Unit,
    onBackPress: () -> Unit
){
    val listState = rememberLazyListState()

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        modifier = Modifier
                            .clickable {
                                onBackPress()
                            }
                    )
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .padding(innerPadding),
            state = listState
        ){
            items(sources){ data ->
                CardSourceNews(
                    data = data,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 10.dp,
                            start = 20.dp,
                            end = 20.dp
                        )
                        .clickable {
                            onSourceNewsClick.invoke(data.id ?: "")
                        }
                )
            }
        }
    }


}