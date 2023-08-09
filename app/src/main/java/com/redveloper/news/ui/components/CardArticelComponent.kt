package com.redveloper.news.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardArticel(
    modifier: Modifier = Modifier,
    title: String,
    author: String,
    textFavorite: String,
    favoriteClick: () -> Unit
){
    Column(
        horizontalAlignment = Alignment.End
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 24.sp
                    ),
                    color = Color.Black
                )

                Text(
                    text = author,
                    style = TextStyle(
                        fontWeight = FontWeight.Light,
                        fontSize = 18.sp
                    ),
                    color = Color.DarkGray,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
            }
        }

        Button(
            onClick = {
                favoriteClick()
            },
        ) {
            Text(text = textFavorite)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardArticel(){
    MaterialTheme{
        CardArticel(
            title = "Title",
            author = "Author",
            textFavorite = "hapus favorite",
            favoriteClick = {

            }
        )
    }
}