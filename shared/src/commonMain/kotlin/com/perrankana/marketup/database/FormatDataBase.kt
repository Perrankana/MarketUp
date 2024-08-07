package com.perrankana.marketup.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FormatDao {
    @Insert
    suspend fun insert(item: FormatEntity)

    @Query("SELECT count(*) FROM FormatEntity")
    suspend fun count(): Int

    @Query("SELECT * FROM FormatEntity")
    fun getAllAsFlow(): Flow<List<FormatEntity>>
}

@Entity
data class FormatEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)