package com.perrankana.marketup.android.di

import android.content.Context
import com.perrankana.marketup.database.EventsDao
import com.perrankana.marketup.database.SoldItemDao
import com.perrankana.marketup.database.TpvEventDao
import com.perrankana.marketup.events.repositories.CurrentEventRepository
import com.perrankana.marketup.sale.datasource.SaleDataSource
import com.perrankana.marketup.sale.datasource.getSaleDataSource
import com.perrankana.marketup.sale.repositories.TpvEventRepository
import com.perrankana.marketup.sale.repositories.TpvEventRepositoryImpl
import com.perrankana.marketup.sale.usecases.EndEventUseCase
import com.perrankana.marketup.sale.usecases.EndEventUseCaseImpl
import com.perrankana.marketup.sale.usecases.GetFormatsUseCase
import com.perrankana.marketup.sale.usecases.GetFormatsUseCaseImpl
import com.perrankana.marketup.sale.usecases.GetProductsByCategoryAndFormatUseCase
import com.perrankana.marketup.sale.usecases.GetProductsByCategoryAndFormatUseCaseImpl
import com.perrankana.marketup.sale.usecases.GetTpvDataUseCase
import com.perrankana.marketup.sale.usecases.GetTpvDataUseCaseImpl
import com.perrankana.marketup.sale.usecases.SellItemUseCase
import com.perrankana.marketup.sale.usecases.SellItemUseCaseImpl
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
        tpvEventRepository: TpvEventRepository,
        categoryRepository: CategoryRepository
    ): GetTpvDataUseCase {
        return GetTpvDataUseCaseImpl(tpvEventRepository, categoryRepository)
    }

    @Provides
    fun provideGetFormatsUseCase(
        formatRepository: FormatRepository
    ): GetFormatsUseCase {
        return GetFormatsUseCaseImpl(formatRepository)
    }

    @Provides
    fun provideGetProductsByCategoryAndFormatUseCase(stockRepository: StockRepository): GetProductsByCategoryAndFormatUseCase {
        return GetProductsByCategoryAndFormatUseCaseImpl(stockRepository)
    }

    @Provides
    fun provideSellItemUseCase(tpvEventRepository: TpvEventRepository, stockRepository: StockRepository) : SellItemUseCase = SellItemUseCaseImpl(tpvEventRepository, stockRepository)

    @Provides
    fun provideEndEventUseCase(tpvEventRepository: TpvEventRepository, stockRepository: StockRepository, currentEventRepository: CurrentEventRepository): EndEventUseCase = EndEventUseCaseImpl(tpvEventRepository, stockRepository, currentEventRepository)

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