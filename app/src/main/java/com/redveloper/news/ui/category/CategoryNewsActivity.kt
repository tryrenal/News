package com.redveloper.news.ui.category

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.redveloper.news.MyApp
import com.redveloper.news.domain.enums.NewsCategoryEnum
import com.redveloper.news.ui.ViewModelFactory
import com.redveloper.news.ui.articel.NewsArticelActivity
import com.redveloper.news.ui.components.CardCategory
import com.redveloper.news.ui.source.SourceNewsActivity
import com.redveloper.news.ui.theme.NewsTheme
import com.redveloper.news.utils.Event
import javax.inject.Inject

class CategoryNewsActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel: CategoryNewsViewModel by viewModels { viewModelFactory }

    fun inject(){
        (application as MyApp).applicationComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inject()

        setContent {
            NewsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val categoryNewsEvent by viewModel.categoryNewsEvent.observeAsState()

                    CategoryNewsScreen(
                        categoryNewsEvent = categoryNewsEvent,
                        modifier = Modifier
                            .fillMaxSize(),
                        onCategorySelected = {
                            SourceNewsActivity.navigate(this, it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryNewsScreen(
    categoryNewsEvent: Event<List<NewsCategoryEnum>>?,
    modifier: Modifier = Modifier,
    onCategorySelected: (NewsCategoryEnum) -> Unit
) {

    val categoryNews = remember { mutableListOf<NewsCategoryEnum>() }

    categoryNewsEvent?.contentIfNotHaveBeenHandle?.let {
        categoryNews.addAll(it)
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 10.dp, vertical = 20.dp)
        ){
            items(categoryNews){ data ->
                CardCategory(
                    data = data,
                    modifier = Modifier
                        .clickable {
                            onCategorySelected.invoke(data)
                        }
                )
            }
        }
    }
}