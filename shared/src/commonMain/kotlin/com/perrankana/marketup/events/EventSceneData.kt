package com.perrankana.marketup.events

import com.perrankana.marketup.events.models.Event
import com.perrankana.marketup.sale.models.TpvEvent

sealed class EventSceneData

data object NewEvent : EventSceneData()
data class EditEvent(val event: Event): EventSceneData()
data class AskStartEvent(val event: Event): EventSceneData()
data class StartEvent(val event: TpvEvent): EventSceneData()