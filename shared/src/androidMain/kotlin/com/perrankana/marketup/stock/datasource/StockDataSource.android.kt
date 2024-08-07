package com.perrankana.marketup.stock.datasource

import android.content.Context
import com.perrankana.marketup.database.CategoryDao
import com.perrankana.marketup.database.FormatDao
import com.perrankana.marketup.database.ProductDao
import com.perrankana.marketup.database.getDatabaseBuilder

class StockDataSourceImpl(private val context: Context): StockDataSource {
    private val appDatabase by lazy { getDatabaseBuilder(context).build() }
    override fun getProductDao(): ProductDao {
        return appDatabase.getProductDao()
    }

    override fun getFormatDao(): FormatDao {
        return appDatabase.getFormatDao()
    }

    override fun getCategoryDao(): CategoryDao {
        return appDatabase.getCategoryDao()
    }

}

actual fun getStockDataSource(any: Any): StockDataSource = StockDataSourceImpl(any as Context)