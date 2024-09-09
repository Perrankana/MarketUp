package com.perrankana.marketup.events.repositories

import com.perrankana.marketup.database.EventEntity
import com.perrankana.marketup.database.EventsDao
import com.perrankana.marketup.events.models.Event
import com.perrankana.marketup.events.models.EventExpenses
import com.perrankana.marketup.events.models.Status

interface CurrentEventRepository {
    suspend fun getCurrentEvent(): Event

    suspend fun saveEvent(event: Event) : Event
}

class CurrentEventRepositoryImpl(private val eventDao: EventsDao): CurrentEventRepository {
    override suspend fun getCurrentEvent(): Event = eventDao.getCurrentEvent().toData()

    override suspend fun saveEvent(event: Event): Event {
        if (event.id == 0L){
            eventDao.insert(event.toEntity())
        } else {
            eventDao.update(event.toEntity())
        }
        return getCurrentEvent()
    }
}

fun EventEntity.toData(): Event {
    return Event(
        id = id,
        name = name,
        expenses = EventExpenses(
            stand = standExpenses,
            travel = travelExpenses,
            others = otherExpenses
        ),
        status = when(status){
            0 -> Status.NotStarted
            1 -> Status.Started
            else -> Status.Ended
        }
    )
}

fun Event.toEntity(): EventEntity = EventEntity(
    id = id,
    name = name,
    standExpenses = expenses.stand,
    travelExpenses = expenses.travel,
    otherExpenses = expenses.others,
    status = when(status){
        Status.NotStarted -> 0
        Status.Started -> 1
        Status.Ended -> 2
    }
)