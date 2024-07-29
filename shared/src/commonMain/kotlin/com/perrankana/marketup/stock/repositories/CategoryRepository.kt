package com.perrankana.marketup.stock.repositories

interface CategoryRepository {
    fun saveCategory(category: String) : List<String>
    fun getCategories(): List<String>
}

object CategoryRepositoryImpl: CategoryRepository{
    private val categories = mutableListOf<String>("anime", "cosecha")
    override fun saveCategory(category: String): List<String> {
        categories.add(category)
        return categories
    }

    override fun getCategories(): List<String> = categories
}