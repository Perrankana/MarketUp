package com.perrankana.marketup.android.di

import com.perrankana.marketup.stock.repositories.StockRepository
import com.perrankana.marketup.stock.repositories.StockRepositoryImpl
import com.perrankana.marketup.stock.usecases.GetStockUseCase
import com.perrankana.marketup.stock.usecases.GetStockUseCaseImpl
import com.perrankana.marketup.stock.usecases.SaveProductUseCase
import com.perrankana.marketup.stock.usecases.SaveProductUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class StockModule {

    @Provides
    fun provideGetStockUseCase(repository: StockRepository): GetStockUseCase {
        return GetStockUseCaseImpl(repository)
    }

    @Provides
    fun provideSaveProductUseCase(repository: StockRepository): SaveProductUseCase {
        return SaveProductUseCaseImpl(repository)
    }

    @Provides
    fun provideStockRepository(): StockRepository = StockRepositoryImpl
}