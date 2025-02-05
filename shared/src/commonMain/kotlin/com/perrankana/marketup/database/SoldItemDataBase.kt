package com.perrankana.marketup.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface SoldItemDao {
    @Insert
    suspend fun insert(item: SoldItemEntity)

    @Update
    suspend fun update(item: SoldItemEntity)

    @Delete
    suspend fun delete(item: SoldItemEntity)

    @Query("SELECT * FROM SoldItemEntity WHERE tpvEventId = :eventId")
    suspend fun getSoldItemsInEvent(eventId: Long): List<SoldItemEntity>

    @Query("SELECT * FROM SoldItemEntity WHERE tpvEventId = :eventId")
    fun getSoldItemsInEventAsFLow(eventId: Long): Flow<List<SoldItemEntity>>

    @Query("SELECT * FROM EventEntity")
    fun getAllAsFlow(): Flow<List<EventEntity>>
}

@Entity
data class SoldItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tpvEventId: Long,
    val price: Float,
    val offer: String,
    val products: String,
)