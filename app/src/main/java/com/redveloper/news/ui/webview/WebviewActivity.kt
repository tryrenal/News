package com.redveloper.news.ui.webview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.redveloper.news.ui.theme.NewsTheme

class WebviewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    intent.extras?.let {
                        val url = it.getString(KEY_URL, "")
                        WebviewScreen(url = url)
                    }

                }
            }
        }
    }

    companion object{
        private const val KEY_URL = "KEY_URL"

        fun navigate(activity: Activity, url: String){
            val intent = Intent(activity, WebviewActivity::class.java)
            intent.putExtra(KEY_URL, url)
            activity.startActivity(intent)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebviewScreen(
    modifier: Modifier = Modifier,
    url: String
){
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

                            }
                    )
                }
            )
        }
    ) { innerPadding ->
        AndroidView(
            modifier = Modifier
                .padding(innerPadding),
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams (
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    loadUrl(url)
                }
            },
            update = {
                it.loadUrl(url)
            }
        )
    }
}