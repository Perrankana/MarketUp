package com.perrankana.marketup.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(item: CategoryEntity)

    @Query("SELECT count(*) FROM CategoryEntity")
    suspend fun count(): Int

    @Query("SELECT * FROM CategoryEntity")
    fun getAllAsFlow(): Flow<List<CategoryEntity>>
}

@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)