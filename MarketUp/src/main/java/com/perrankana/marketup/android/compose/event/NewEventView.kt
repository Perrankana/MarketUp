package com.perrankana.marketup.android.compose.event

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perrankana.marketup.android.MyApplicationTheme
import com.perrankana.marketup.android.R
import com.perrankana.marketup.android.compose.BackgroundCard
import com.perrankana.marketup.events.models.Event
import com.perrankana.marketup.events.models.EventExpenses

@Composable
fun NewEventView(
    event: Event? = null,
    saveEvent: (Event) -> Unit,
    startEvent: (Event) -> Unit,
){

    var name by rememberSaveable { mutableStateOf(event?.name.orEmpty()) }
    var nameError by rememberSaveable { mutableStateOf(false) }
    var standPrice by rememberSaveable { mutableStateOf(event?.expenses?.stand?.toString() ?: "" )}
    var standPriceError by rememberSaveable { mutableStateOf(false) }
    var travelExpenses by rememberSaveable { mutableStateOf(event?.expenses?.travel?.toString() ?: "") }
    var otherExpenses by rememberSaveable { mutableStateOf(event?.expenses?.others?.toString() ?: "") }

    Box {
        BackgroundCard {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 20.dp)
                    .verticalScroll(ScrollState(0)),
            ) {
                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.name),
                    style = MaterialTheme.typography.body1
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = {
                        nameError = false
                        name = it
                    },
                    placeholder = { Text(text = "Fleamarket") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    isError = nameError
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.expenses),
                    style = MaterialTheme.typography.h6
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.stand),
                    style = MaterialTheme.typography.body1
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = standPrice,
                    onValueChange = { value ->
                        if (value.isEmpty()) {
                            standPrice = value
                        } else {
                            value.toFloatOrNull()?.let {
                                standPrice = value
                            }
                        }
                        standPriceError = false
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    isError = standPriceError
                )
                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.travel),
                    style = MaterialTheme.typography.body1
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = travelExpenses,
                    onValueChange = { value ->
                        if (value.isEmpty()) {
                            travelExpenses = value
                        } else {
                            value.toFloatOrNull()?.let {
                                travelExpenses = value
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.other),
                    style = MaterialTheme.typography.body1
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = otherExpenses,
                    onValueChange = { value ->
                        if (value.isEmpty()) {
                            otherExpenses = value
                        } else {
                            value.toFloatOrNull()?.let {
                                otherExpenses = value
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.padding(40.dp))
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(20.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (name.isEmpty()) {
                            nameError = true
                        }
                        if (standPrice.isEmpty()) {
                            standPriceError = true
                        }
                        if (
                            name.isNotEmpty() &&
                            standPrice.isNotEmpty()
                        ) {
                            saveEvent(
                                Event(
                                    id = event?.id ?: 0,
                                    name = name,
                                    expenses = EventExpenses(
                                      stand = standPrice.toFloatOrNull() ?: 0f,
                                        travel = travelExpenses.toFloatOrNull() ?: 0f,
                                        others = otherExpenses.toFloatOrNull() ?: 0f
                                    ),
                                )
                            )
                        }
                    }) {
                    Text(text = stringResource(id = R.string.save))
                }
                if (event != null) {
                    Spacer(modifier = Modifier.padding(4.dp))
                    Button(
                        modifier = Modifier,
                        onClick = {
                            startEvent(Event(
                                name = name,
                                expenses = EventExpenses(
                                    stand = standPrice.toFloat(),
                                    travel = travelExpenses.toFloat(),
                                    others = otherExpenses.toFloat()
                                ),
                            ))
                        }) {
                        Text(text = stringResource(id = R.string.start))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun NewEventPreview() {
    MyApplicationTheme {
        NewEventView(
            saveEvent = {},
            startEvent = {}
        )
    }
}