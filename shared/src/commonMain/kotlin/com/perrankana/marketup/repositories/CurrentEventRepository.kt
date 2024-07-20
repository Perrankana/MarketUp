package com.perrankana.marketup.repositories

import com.perrankana.marketup.models.Event

interface CurrentEventRepository {
    fun getCurrentEvent(): Event

    fun saveEvent(event: Event)
}

object CurrentEventRepositoryImpl: CurrentEventRepository{
    private var event: Event? = null
    override fun getCurrentEvent(): Event {
        return event!!
    }

    override fun saveEvent(event: Event) {
        this.event = event
    }


}