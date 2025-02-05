package com.perrankana.marketup.sale.datasource

import com.perrankana.marketup.database.SoldItemDao
import com.perrankana.marketup.database.TpvEventDao

interface SaleDataSource {
    fun getSoldItemDao(): SoldItemDao
    fun getTpvDao(): TpvEventDao
}

expect fun getSaleDataSource(any: Any): SaleDataSource