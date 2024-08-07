package com.perrankana.marketup.stock.datasource

import com.perrankana.marketup.database.CategoryDao
import com.perrankana.marketup.database.FormatDao
import com.perrankana.marketup.database.ProductDao
import com.perrankana.marketup.database.getDatabaseBuilder

class StockDataSourceImpl : StockDataSource {
    private val appDatabase by lazy { getDatabaseBuilder().build() }

    override fun getProductDao(): ProductDao = appDatabase.getProductDao()

    override fun getFormatDao(): FormatDao = appDatabase.getFormatDao()

    override fun getCategoryDao(): CategoryDao = appDatabase.getCategoryDao()

}

actual fun getStockDataSource(any: Any): StockDataSource = StockDataSourceImpl()