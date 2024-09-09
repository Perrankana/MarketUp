package com.perrankana.marketup.events.models

data class Event(
    val id: Long = 0,
    val status: Status = Status.NotStarted,
    val name: String,
    val expenses: EventExpenses,
    )

data class EventExpenses(
    val stand: Float,
    val travel: Float,
    val others: Float,
)

sealed class Status {
    data object NotStarted : Status()
    data object Started : Status()
    data object Ended : Status()
}