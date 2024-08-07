package com.perrankana.marketup.stock.repositories

import com.perrankana.marketup.database.FormatDao
import com.perrankana.marketup.database.FormatEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface FormatRepository {
    suspend fun getFormats(): Flow<List<String>>
    suspend fun saveFormat(format: String)
}

class FormatRepositoryImpl(private val formatDao: FormatDao): FormatRepository{

    override suspend fun getFormats(): Flow<List<String>> = formatDao.getAllAsFlow().map {
        it.map { it.name }
    }

    override suspend fun saveFormat(format: String) {
        formatDao.insert(FormatEntity(name = format))
    }
}