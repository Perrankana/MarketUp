package com.perrankana.marketup.android.compose.event

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perrankana.marketup.android.MyApplicationTheme
import com.perrankana.marketup.android.R
import com.perrankana.marketup.android.compose.BackgroundCard
import com.perrankana.marketup.events.models.Event
import com.perrankana.marketup.events.models.EventExpenses
import com.perrankana.marketup.events.models.Status

@Composable
fun StartEventView(
    event: Event,
    startEvent: (Event) -> Unit
) {
    Box {
        BackgroundCard {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 20.dp)
                    .verticalScroll(ScrollState(0)),
            ) {
                Text (
                    text = stringResource(id = R.string.startEvent, event.name),
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.secondary
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Button(
                    modifier = Modifier.align(Alignment.End),
                    onClick = {
                        startEvent(event)
                    }) {
                    androidx.compose.material.Text(text = stringResource(id = R.string.start))
                }
            }
        }
    }
}

@Preview
@Composable
fun StartEventViewPreview(){
    MyApplicationTheme {
        StartEventView(
            event = Event(
                name = "Fleamarket",
                expenses = EventExpenses(
                    stand = 0f,
                    travel = 0f,
                    others = 0f
                ),
                status = Status.NotStarted
            ),
            startEvent = {}
        )
    }
}