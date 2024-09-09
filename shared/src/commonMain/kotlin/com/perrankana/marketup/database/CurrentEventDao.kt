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
interface EventsDao {
    @Insert
    suspend fun insert(item: EventEntity)

    @Update
    suspend fun update(item: EventEntity)

    @Delete
    suspend fun delete(item: EventEntity)

    @Query("SELECT * FROM EventEntity WHERE status != 2")
    suspend fun getCurrentEvent(): EventEntity

    @Query("SELECT * FROM EventEntity")
    fun getAllAsFlow(): Flow<List<EventEntity>>
}

@Entity
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val standExpenses: Float,
    val travelExpenses: Float,
    val otherExpenses: Float,
    val status: Int
)
/**
 * status:
 * NOT_STARTED = 0
 * STARTED = 1
 * ENDED = 2
 * */