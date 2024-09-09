package com.perrankana.marketup.events.datasource

import com.perrankana.marketup.database.EventsDao
import com.perrankana.marketup.database.getDatabaseBuilder

class EventsDataSourceImpl : EventsDataSource {
    private val appDatabase by lazy { getDatabaseBuilder().build() }

    override fun getEventDao(): EventsDao = appDatabase.getEventsDao()

}

actual fun getEventsDataSource(any: Any): EventsDataSource = EventsDataSourceImpl()