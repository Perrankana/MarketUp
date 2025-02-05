package com.perrankana.marketup.sale.repositories

import com.perrankana.marketup.database.EventsDao
import com.perrankana.marketup.database.SoldItemDao
import com.perrankana.marketup.database.SoldItemEntity
import com.perrankana.marketup.database.TpvEventDao
import com.perrankana.marketup.database.TpvEventEntity
import com.perrankana.marketup.events.models.Event
import com.perrankana.marketup.events.repositories.toData
import com.perrankana.marketup.sale.models.SoldItem
import com.perrankana.marketup.sale.models.TpvEvent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.serializersModule
import kotlinx.serialization.serializer

interface TpvEventRepository {
    suspend fun getTpvEvent(): TpvEvent
    suspend fun saveTpvEvent(event: TpvEvent): TpvEvent
    suspend fun saveSoldItem(soldItem: SoldItem): SoldItem
}

class TpvEventRepositoryImpl(
    private val tpvEventDao: TpvEventDao,
    private val eventDao: EventsDao,
    private val soldItemDao: SoldItemDao
) : TpvEventRepository {
    override suspend fun getTpvEvent(): TpvEvent {
        val event = eventDao.getCurrentEvent()
        val tpvEvent = tpvEventDao.getTpvEvent(event.id)
        val soldItems = soldItemDao.getSoldItemsInEvent(tpvEvent.id)
        return tpvEvent.toData(event.toData(), soldItems.map { it.toData() })
    }

    override suspend fun saveTpvEvent(event: TpvEvent): TpvEvent {
        if (event.id == 0L) {
            tpvEventDao.insert(event.toEntity())
        } else {
            tpvEventDao.update(event.toEntity())
        }
        return event
    }

    override suspend fun saveSoldItem(soldItem: SoldItem): SoldItem {
        if (soldItem.id == 0L) {
            soldItemDao.insert(soldItem.toEntity())
        } else {
            soldItemDao.update(soldItem.toEntity())
        }
        return soldItem
    }
}

fun TpvEventEntity.toData(event: Event, soldItems: List<SoldItem>): TpvEvent = TpvEvent(
    id = id,
    event = event,
    totalSold = totalSold,
    soldItem = soldItems
)

fun SoldItemEntity.toData(): SoldItem = SoldItem(
    id = id,
    tpvEventId = tpvEventId,
    products = Json.decodeFromString<List<Long>>(products),
    offer = offer,
    price = price
)

fun TpvEvent.toEntity(): TpvEventEntity {
    return TpvEventEntity(
        id = id,
        eventId = event.id,
        totalSold = totalSold
    )
}

fun SoldItem.toEntity(): SoldItemEntity {
    return SoldItemEntity(
        id = id,
        tpvEventId = tpvEventId,
        price = price,
        offer = offer,
        products = Json.encodeToString(serializersModule.serializer(), products)
    )
}