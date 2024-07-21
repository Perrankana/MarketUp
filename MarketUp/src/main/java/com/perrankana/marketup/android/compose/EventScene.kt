package com.perrankana.marketup.android.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.perrankana.marketup.android.MyApplicationTheme
import com.perrankana.marketup.android.viewmodels.CurrentEventViewModel

@Composable
fun EventScene(
    currentEventViewModel: CurrentEventViewModel = hiltViewModel()) {

    val eventData by currentEventViewModel.currentEventData.observeAsState()

    EventView()

}

@Composable
fun EventView(){
    BackgroundCard {
        Text(
            modifier = Modifier.padding(20.dp),
            text = "Event"
        )
    }
}

@Preview
@Composable
fun EventPreview() {
    MyApplicationTheme {
        EventView()
    }
}