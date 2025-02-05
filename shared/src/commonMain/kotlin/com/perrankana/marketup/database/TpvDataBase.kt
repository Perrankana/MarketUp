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
interface TpvEventDao {
    @Insert
    suspend fun insert(item: TpvEventEntity)

    @Update
    suspend fun update(item: TpvEventEntity)

    @Delete
    suspend fun delete(item: TpvEventEntity)

    @Query("SELECT * FROM TpvEventEntity WHERE eventId = :id")
    suspend fun getTpvEvent(id: Long): TpvEventEntity

    @Query("SELECT * FROM TpvEventEntity")
    fun getAllAsFlow(): Flow<List<TpvEventEntity>>
}

@Entity
data class TpvEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val eventId: Long,
    val totalSold: Float,
)