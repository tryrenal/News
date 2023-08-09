package com.redveloper.news.ui.category

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.redveloper.news.MyApp
import com.redveloper.news.R
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

                    val categoryNews = remember { mutableListOf<NewsCategoryEnum>() }

                    categoryNewsEvent?.contentIfNotHaveBeenHandle?.let {
                        categoryNews.addAll(it)
                    }

                    CategoryNewsScreen(
                        categoryNews = categoryNews,
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
    categoryNews: List<NewsCategoryEnum>,
    modifier: Modifier = Modifier,
    onCategorySelected: (NewsCategoryEnum) -> Unit
) {



    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 10.dp, vertical = 20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.category),
                style = TextStyle(
                    fontWeight =  FontWeight.Bold,
                    fontSize = 24.sp
                )
            )


            LazyColumn(){
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
}

@Preview(showBackground = true)
@Composable
fun PreviewCategoryNewsScreen(){
    val categorys = listOf(
        NewsCategoryEnum.TECHNOLOGY,
        NewsCategoryEnum.SPORTS,
        NewsCategoryEnum.SCIENCE,
    )
    MaterialTheme{
        CategoryNewsScreen(
            categoryNews = categorys,
            onCategorySelected = {

            }
        )
    }
}