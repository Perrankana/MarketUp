package com.perrankana.marketup.android.compose.stock

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.perrankana.marketup.android.MyApplicationTheme
import com.perrankana.marketup.android.R
import com.perrankana.marketup.android.compose.Background

@Composable
fun StockListView(
    onSearch: (String) -> Unit
) {
    Scaffold(
        topBar = {
            StockHeaderView(
                onSearch = onSearch
            )
        }
    ) { contentPadding ->
        contentPadding
        Background {

        }
    }
}

@Composable
fun StockHeaderView(
    onSearch: (String) -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    Row(  modifier = Modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier.weight(1f),
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onSearch(searchQuery)
            },
            placeholder = {
                Text(text = "Search")
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null
                )
            }

        )

        Row(modifier = Modifier.align(Alignment.CenterVertically)) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_filter_list_24),
                    contentDescription = null
                )
            }

            IconButton(
                onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_auto_fix_high_24),
                    contentDescription = null
                )
            }
        }
    }


}

@Preview
@Composable
fun StockListViewPreview() {
    MyApplicationTheme {
        StockListView(
            onSearch = {}
        )
    }
}