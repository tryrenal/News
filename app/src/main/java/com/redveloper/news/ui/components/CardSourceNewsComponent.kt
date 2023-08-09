package com.redveloper.news.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.redveloper.news.domain.model.SourceNews

@Composable
fun CardSourceNews (
    modifier: Modifier = Modifier,
    data: SourceNews
){
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {

            Text(
                text = data.name ?: "",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                )
            )

            Text(
                text = data.description ?: "",
                style = TextStyle(
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardSourceNews(){
    val data = SourceNews(
        id = "bloomberg",
        name = "Bloomberg",
        description = "Bloomberg delivers business and markets news, data, analysis, and video to the world, featuring stories from Businessweek and Bloomberg News.",
        url = "http://www.bloomberg.com",
        category = "business",
        languange = "en",
        country = "us"
    )
    MaterialTheme{
        CardSourceNews(
            data = data
        )
    }
}