package com.perrankana.marketup.events.datasource

import android.content.Context
import com.perrankana.marketup.database.EventsDao
import com.perrankana.marketup.database.getDatabaseBuilder

class EventsDataSourceImpl(private val context: Context): EventsDataSource {
    private val appDatabase by lazy { getDatabaseBuilder(context).build() }

    override fun getEventDao(): EventsDao {
        return appDatabase.getEventsDao()
    }

}

actual fun getEventsDataSource(any: Any): EventsDataSource = EventsDataSourceImpl(any as Context)