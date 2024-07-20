package com.perrankana.marketup.models

data class Event(
    val name: String,
    val expenses: EventExpenses,
    )

data class EventExpenses(
    val stand: Float,
    val travel: Float,
    val others: Float,
)