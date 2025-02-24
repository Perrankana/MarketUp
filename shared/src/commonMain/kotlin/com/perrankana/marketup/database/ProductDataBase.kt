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
interface ProductDao {
    @Insert
    suspend fun insert(item: ProductEntity)

    @Update
    suspend fun update(item: ProductEntity)

    @Delete
    suspend fun delete(item: ProductEntity)

    @Query("SELECT count(*) FROM ProductEntity")
    suspend fun count(): Int

    @Query("SELECT * FROM ProductEntity")
    fun getAllAsFlow(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM ProductEntity WHERE id = :id")
    suspend fun select(id: Long): ProductEntity
}

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val image: String?,
    val categories: String,
    val format: String,
    val cost: Float,
    val price: Float,
    val offers: String,
    val stock: Int
)