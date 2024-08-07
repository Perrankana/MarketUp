package com.perrankana.marketup.stock.repositories

import com.perrankana.marketup.database.CategoryDao
import com.perrankana.marketup.database.CategoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface CategoryRepository {
    suspend fun saveCategory(category: String)
    suspend fun getCategories(): Flow<List<String>>
}

class CategoryRepositoryImpl(private val categoryDao: CategoryDao): CategoryRepository{
    override suspend fun saveCategory(category: String) {
        categoryDao.insert(CategoryEntity(name = category))
    }

    override suspend fun getCategories(): Flow<List<String>> = categoryDao.getAllAsFlow().map {
        it.map { it.name }
    }
}