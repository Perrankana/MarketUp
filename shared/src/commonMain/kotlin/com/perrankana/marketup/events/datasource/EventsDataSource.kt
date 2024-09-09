package com.perrankana.marketup.events.datasource

import com.perrankana.marketup.database.EventsDao


interface EventsDataSource {
    fun getEventDao(): EventsDao
}

expect fun getEventsDataSource(any: Any): EventsDataSource