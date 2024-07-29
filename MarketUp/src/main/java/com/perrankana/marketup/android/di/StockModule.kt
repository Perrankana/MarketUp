package com.perrankana.marketup.android.di

import com.perrankana.marketup.stock.repositories.CategoryRepository
import com.perrankana.marketup.stock.repositories.CategoryRepositoryImpl
import com.perrankana.marketup.stock.repositories.FormatRepository
import com.perrankana.marketup.stock.repositories.FormatRepositoryImpl
import com.perrankana.marketup.stock.repositories.StockRepository
import com.perrankana.marketup.stock.repositories.StockRepositoryImpl
import com.perrankana.marketup.stock.usecases.GetNewProductDataUseCase
import com.perrankana.marketup.stock.usecases.GetNewProductDataUseCaseImpl
import com.perrankana.marketup.stock.usecases.GetStockUseCase
import com.perrankana.marketup.stock.usecases.GetStockUseCaseImpl
import com.perrankana.marketup.stock.usecases.SaveNewCategoryUseCase
import com.perrankana.marketup.stock.usecases.SaveNewCategoryUseCaseImpl
import com.perrankana.marketup.stock.usecases.SaveNewFormatUseCase
import com.perrankana.marketup.stock.usecases.SaveNewFormatUseCaseImpl
import com.perrankana.marketup.stock.usecases.SaveProductUseCase
import com.perrankana.marketup.stock.usecases.SaveProductUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.text.Normalizer.Form

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
    fun provideSaveNewCategoryUseCase(repository: CategoryRepository): SaveNewCategoryUseCase{
        return SaveNewCategoryUseCaseImpl(repository)
    }

    @Provides
    fun provideSaveNewFormatUseCase(repository: FormatRepository): SaveNewFormatUseCase{
        return SaveNewFormatUseCaseImpl(repository)
    }

    @Provides
    fun provideGetNewProductDataUseCase(categoryRepository: CategoryRepository, formatRepository: FormatRepository): GetNewProductDataUseCase {
        return GetNewProductDataUseCaseImpl(categoryRepository, formatRepository)
    }

    @Provides
    fun provideStockRepository(): StockRepository = StockRepositoryImpl

    @Provides
    fun provideCategoryRepository(): CategoryRepository = CategoryRepositoryImpl

    @Provides
    fun provideFormatRepository(): FormatRepository = FormatRepositoryImpl
}