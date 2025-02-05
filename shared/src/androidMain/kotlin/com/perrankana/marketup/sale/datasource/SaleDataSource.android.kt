package com.perrankana.marketup.sale.datasource

import android.content.Context
import com.perrankana.marketup.database.SoldItemDao
import com.perrankana.marketup.database.TpvEventDao
import com.perrankana.marketup.database.getDatabaseBuilder

class SaleDataSourceImpl(private val context: Context): SaleDataSource {
    private val appDatabase by lazy { getDatabaseBuilder(context).build() }

    override fun getSoldItemDao(): SoldItemDao {
        return appDatabase.getSoldItemDao()
    }

    override fun getTpvDao(): TpvEventDao {
        return appDatabase.getTpvEventDao()
    }
}

actual fun getSaleDataSource(any: Any): SaleDataSource = SaleDataSourceImpl(any as Context)