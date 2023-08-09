package com.redveloper.news.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.redveloper.news.ui.category.CategoryNewsActivity
import com.redveloper.news.ui.favorite.FavoriteNewsActivity
import com.redveloper.news.ui.theme.NewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        toCategory = {
                            CategoryNewsActivity.navigate(this)
                        },
                        toFavorite = {
                            FavoriteNewsActivity.navigate(this)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    toCategory: () -> Unit,
    toFavorite: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Button(
            onClick = {
                toCategory()
            },
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
        ) {
            Text(text = "to category")
        }

        Button(
            onClick = {
                toFavorite()
            },
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
        ) {
            Text(text = "to favorite")
        }
    }
}