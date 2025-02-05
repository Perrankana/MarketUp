package com.perrankana.marketup.android.di

import android.content.Context
import com.perrankana.marketup.database.EventsDao
import com.perrankana.marketup.database.SoldItemDao
import com.perrankana.marketup.database.TpvEventDao
import com.perrankana.marketup.sale.datasource.SaleDataSource
import com.perrankana.marketup.sale.datasource.getSaleDataSource
import com.perrankana.marketup.sale.repositories.TpvEventRepository
import com.perrankana.marketup.sale.repositories.TpvEventRepositoryImpl
import com.perrankana.marketup.sale.usecases.GetTpvDataUseCase
import com.perrankana.marketup.sale.usecases.GetTpvDataUseCaseImpl
import com.perrankana.marketup.stock.datasource.StockDataSource
import com.perrankana.marketup.stock.datasource.getStockDataSource
import com.perrankana.marketup.stock.repositories.CategoryRepository
import com.perrankana.marketup.stock.repositories.FormatRepository
import com.perrankana.marketup.stock.repositories.StockRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SaleModule {

    @Provides
    fun provideGetTpvDataUseCase(
        stockRepository: StockRepository,
        categoryRepository: CategoryRepository,
        formatRepository: FormatRepository,
        tpvEventRepository: TpvEventRepository
    ): GetTpvDataUseCase {
        return GetTpvDataUseCaseImpl(stockRepository, categoryRepository, formatRepository, tpvEventRepository)
    }

    @Provides
    fun provideTpvEventRepository(
        tpvEventDao: TpvEventDao,
        eventDao: EventsDao,
        soldItemDao: SoldItemDao
    ): TpvEventRepository {
        return TpvEventRepositoryImpl(tpvEventDao, eventDao, soldItemDao)
    }

    @Provides
    fun provideTpvEventDao(saleDataSource: SaleDataSource): TpvEventDao = saleDataSource.getTpvDao()

    @Provides
    fun provideSoldItemDao(saleDataSource: SaleDataSource): SoldItemDao = saleDataSource.getSoldItemDao()

    @Singleton
    @Provides
    fun provideSaleDataSource(@ApplicationContext context: Context): SaleDataSource = getSaleDataSource(context)

}