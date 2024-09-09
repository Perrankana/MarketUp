package com.perrankana.marketup.android.compose.event

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.livedata.observeAsState
import com.perrankana.marketup.android.MyApplicationTheme
import com.perrankana.marketup.android.viewmodels.CurrentEventViewModel
import com.perrankana.marketup.events.AskStartEvent
import com.perrankana.marketup.events.EditEvent
import com.perrankana.marketup.events.StartEvent
import com.perrankana.marketup.events.models.Event

@Composable
fun EventScene(
    onStartEvent: (Event) -> Unit,
    currentEventViewModel: CurrentEventViewModel = hiltViewModel()
) {

    val eventData by currentEventViewModel.currentEventData.observeAsState()

    when(val data = eventData){
        is EditEvent -> NewEventView(
            event = data.event,
            saveEvent = { currentEventViewModel.saveEvent(it) },
            startEvent = {
                currentEventViewModel.startEvent(it)
                onStartEvent(it) }
        )
        is StartEvent -> {
            onStartEvent(data.event)
        }
        is AskStartEvent -> StartEventView(
            event = data.event,
            startEvent = {
                currentEventViewModel.startEvent(it)
                onStartEvent(it) }
        )
        else -> NewEventView(
            saveEvent = { currentEventViewModel.saveEvent(it) },
            startEvent = {
                currentEventViewModel.startEvent(it)
                onStartEvent(it)
            }
        )
    }
}

@Preview
@Composable
fun EventPreview() {
    MyApplicationTheme {
        NewEventView(
            saveEvent = {},
            startEvent = {}
        )
    }
}