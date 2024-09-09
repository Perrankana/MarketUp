package com.perrankana.marketup.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        ProductEntity::class,
        CategoryEntity::class,
        FormatEntity::class,
        EventEntity::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getFormatDao(): FormatDao
    abstract fun getEventsDao(): EventsDao
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
//        .addMigrations(MIGRATIONS)
        .fallbackToDestructiveMigrationOnDowngrade(false)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}