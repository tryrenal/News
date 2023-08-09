package com.redveloper.news.ui.articel

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.redveloper.news.MyApp
import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.domain.model.Item
import com.redveloper.news.ui.ViewModelFactory
import com.redveloper.news.ui.components.CardArticel
import com.redveloper.news.ui.theme.NewsTheme
import com.redveloper.news.ui.webview.WebviewActivity
import kotlinx.coroutines.launch
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
                    val searchArticelEvent by viewModel.searchResult.observeAsState()
                    val errorArticelEvent by viewModel.errorArticelEvent.observeAsState()
                    val loadingEvent by viewModel.loadingEvent.observeAsState()

                    val articels = remember { mutableListOf<HeadlineNews>() }

                    articelsEvent?.contentIfNotHaveBeenHandle?.let{
                        articels.addAll(it)
                    }

                    errorArticelEvent?.contentIfNotHaveBeenHandle?.let {
                        Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
                    }

                    searchArticelEvent?.contentIfNotHaveBeenHandle?.let { query ->
                        articels.clear()
                        viewModel.getListArticel(query = query)
                    }

                    LaunchedEffect(true){
                        articels.clear()

                        intent.extras?.getString(KEY_SOURCE)?.let { source ->
                            viewModel.source.value = source
                            viewModel.getListArticel()
                        }
                    }

                    NewsArticelScreen(
                        modifier = Modifier.fillMaxSize(),
                        articels = articels,
                        onLoadMore = {
                            viewModel.loadMore()
                        },
                        onSelectedArticel = { url ->
                            WebviewActivity.navigate(this, url)
                        },
                        onSearch = {
                            lifecycleScope.launch {
                                viewModel.searchQuery.value = it
                            }
                        },
                        onBackPress = {
                            finish()
                        }
                    )

                    loadingEvent?.contentIfNotHaveBeenHandle?.let { show ->
                        if (show){
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .wrapContentSize(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }

    companion object{
        private const val KEY_SOURCE = "KEY_SOURCE"

        fun navigate(activity: Activity, source: String){
            val intent = Intent(activity, NewsArticelActivity::class.java)
            intent.putExtra(KEY_SOURCE, source)
            activity.startActivity(intent)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsArticelScreen(
    modifier: Modifier = Modifier,
    articels: List<HeadlineNews>,
    onLoadMore: () -> Unit,
    onSelectedArticel: (url: String) -> Unit,
    onSearch: (String) -> Unit,
    onBackPress: () -> Unit
) {

    val listState = rememberLazyListState()
    val searchText = remember { mutableStateOf("") }

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
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = searchText.value,
                onValueChange = {
                    searchText.value = it
                    onSearch(it) // Call the search callback with the updated query
                },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 5.dp,
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 10.dp
                    )
            )

            LazyColumn(
                state = listState,
                content = {
                    items(articels){ data ->
                        CardArticel(
                            title = data.title ?: "",
                            author = data.author ?: "",
                            modifier = Modifier
                                .padding(
                                    bottom = 10.dp,
                                    start = 20.dp,
                                    end = 20.dp
                                )
                                .clickable {
                                    data.url?.let {
                                        onSelectedArticel.invoke(it)
                                    }
                                }
                        )
                    }
                }
            )

        }
    }

    listState.OnBottomReached() {
        onLoadMore.invoke()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsArticelScreen(){
    val articels = listOf(
        HeadlineNews(
            source = Item(
                "id-source", "source"
            ),
            author = "author",
            title = "title",
            description = "description",
            url = "url",
            urlToImage = "url to image",
            content = "content"
        )
    )
    MaterialTheme{
        NewsArticelScreen(
            articels = articels,
            onLoadMore = {

            },
            onSelectedArticel = {

            },
            onSearch = {

            },
            onBackPress = {

            }
        )
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