package com.redveloper.news.ui.articel

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.redveloper.news.MyApp
import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.ui.ViewModelFactory
import com.redveloper.news.ui.components.CardArticel
import com.redveloper.news.ui.theme.NewsTheme
import javax.inject.Inject

class NewsArticelActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel: NewsArticelViewModel by viewModels { viewModelFactory }

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
                    val articelsEvent by viewModel.articelsEvent.observeAsState()
                    val errorArticelEvent by viewModel.errorArticelEvent.observeAsState()

                    val articels = remember { mutableListOf<HeadlineNews>() }

                    articelsEvent?.contentIfNotHaveBeenHandle?.let{
                        articels.addAll(it)
                    }

                    errorArticelEvent?.contentIfNotHaveBeenHandle?.let {
                        Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
                    }

                    LaunchedEffect(true){
                        articels.clear()
                        viewModel.getListArticel("australian-financial-review")
                    }

                    NewsArticelScreen(
                        modifier = Modifier.fillMaxSize(),
                        articels = articels
                    ) {
                        viewModel.loadMore()
                    }
                }
            }
        }
    }

    companion object{
        fun navigate(activity: Activity){
            val intent = Intent(activity, NewsArticelActivity::class.java)
            activity.startActivity(intent)
        }
    }
}

@Composable
fun NewsArticelScreen(
    modifier: Modifier = Modifier,
    articels: List<HeadlineNews>,
    onLoadMore: () -> Unit
) {

    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = listState,
        content = {
            items(articels){ data ->
                CardArticel(
                    title = data.title ?: "",
                    author = data.author ?: "",
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )
            }
        }
    )

    listState.OnBottomReached() {
        onLoadMore.invoke()
    }
}

@Composable
fun LazyListState.OnBottomReached(
    buffer : Int = 0,
    onLoadMore : () -> Unit
) {
    require(buffer >= 0) { "buffer cannot be negative, but was $buffer" }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?:
                return@derivedStateOf true

            lastVisibleItem.index >=  layoutInfo.totalItemsCount - 1 - buffer
        }
    }

    LaunchedEffect(shouldLoadMore){
        snapshotFlow { shouldLoadMore.value }
            .collect { if (it) onLoadMore() }
    }
}