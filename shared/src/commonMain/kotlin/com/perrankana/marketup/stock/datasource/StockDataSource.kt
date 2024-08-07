package com.perrankana.marketup.stock.datasource

import com.perrankana.marketup.database.CategoryDao
import com.perrankana.marketup.database.FormatDao
import com.perrankana.marketup.database.ProductDao

interface StockDataSource {
    fun getProductDao(): ProductDao
    fun getFormatDao(): FormatDao
    fun getCategoryDao(): CategoryDao
}

expect fun getStockDataSource(any: Any): StockDataSource