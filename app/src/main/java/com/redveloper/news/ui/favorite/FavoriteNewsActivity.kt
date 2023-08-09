package com.redveloper.news.ui.favorite

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.redveloper.news.MyApp
import com.redveloper.news.domain.model.HeadlineNews
import com.redveloper.news.ui.ViewModelFactory
import com.redveloper.news.ui.components.CardArticel
import com.redveloper.news.ui.theme.NewsTheme
import com.redveloper.news.ui.webview.WebviewActivity
import javax.inject.Inject

class FavoriteNewsActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel: FavoriteNewsViewModel by viewModels { viewModelFactory }

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
                    val favoriteEvent by viewModel.favoriteNewsEvent.observeAsState()
                    val clearFavoriteNewsUseCase by viewModel.clearFavoriteEVent.observeAsState()
                    val errorEvent by viewModel.errorEvent.observeAsState()

                    val favorites = remember {
                        mutableListOf<HeadlineNews>()
                    }

                    favoriteEvent?.contentIfNotHaveBeenHandle?.let {
                        favorites.addAll(it)
                    }

                    errorEvent?.contentIfNotHaveBeenHandle?.let {
                        Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
                    }

                    clearFavoriteNewsUseCase?.contentIfNotHaveBeenHandle?.let {
                        favorites.clear()
                        viewModel.getFavoritesNews()

                        Toast.makeText(LocalContext.current, "Success Clear", Toast.LENGTH_SHORT).show()
                    }

                    LaunchedEffect(true){
                        favorites.clear()

                        viewModel.getFavoritesNews()
                    }

                    FavoriteNewsScreen(
                        modifier = Modifier
                            .fillMaxSize(),
                        articels = favorites,
                        onSelectedArticel = { url ->
                            WebviewActivity.navigate(this, url)
                        },
                        onBackPress = {
                            finish()
                        },
                        onClearFavorite = { url ->
                            viewModel.clearFavoriteNews(url)
                        }
                    )
                }
            }
        }
    }

    companion object{
        fun navigate(activity: Activity){
            val intent = Intent(activity, FavoriteNewsActivity::class.java)
            activity.startActivity(intent)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteNewsScreen(
    modifier: Modifier = Modifier,
    articels: List<HeadlineNews>,
    onSelectedArticel: (url: String) -> Unit,
    onBackPress: () -> Unit,
    onClearFavorite: (url: String) -> Unit
){
    val listState = rememberLazyListState()

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "favorite news")
                },
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
                .fillMaxSize()
                .padding(innerPadding),
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
                            },
                        textFavorite = "hapus favorite",
                        favoriteClick = {
                            data.url?.let {
                                onClearFavorite.invoke(data.url)
                            }
                        }
                    )
                }
            }
        )
    }
}
