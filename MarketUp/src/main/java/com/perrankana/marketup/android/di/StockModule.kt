package com.perrankana.marketup.android.di

import android.content.Context
import com.perrankana.marketup.database.AppDatabase
import com.perrankana.marketup.database.CategoryDao
import com.perrankana.marketup.database.FormatDao
import com.perrankana.marketup.database.ProductDao
import com.perrankana.marketup.database.getDatabaseBuilder
import com.perrankana.marketup.stock.datasource.StockDataSource
import com.perrankana.marketup.stock.datasource.getStockDataSource
import com.perrankana.marketup.stock.repositories.CategoryRepository
import com.perrankana.marketup.stock.repositories.CategoryRepositoryImpl
import com.perrankana.marketup.stock.repositories.FormatRepository
import com.perrankana.marketup.stock.repositories.FormatRepositoryImpl
import com.perrankana.marketup.stock.repositories.StockRepository
import com.perrankana.marketup.stock.repositories.StockRepositoryImpl
import com.perrankana.marketup.stock.usecases.DeleteProductUseCase
import com.perrankana.marketup.stock.usecases.DeleteProductUseCaseImpl
import com.perrankana.marketup.stock.usecases.FilterProductsUseCase
import com.perrankana.marketup.stock.usecases.FilterProductsUseCaseImpl
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
import com.perrankana.marketup.stock.usecases.SearchProductsUseCase
import com.perrankana.marketup.stock.usecases.SearchProductsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.text.Normalizer.Form
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class StockModule {

    @Provides
    fun provideGetStockUseCase(repository: StockRepository, categoryRepository: CategoryRepository, formatRepository: FormatRepository): GetStockUseCase {
        return GetStockUseCaseImpl(repository, categoryRepository, formatRepository)
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
    fun provideDeleteProductUseCase(repository: StockRepository): DeleteProductUseCase {
        return DeleteProductUseCaseImpl(repository)
    }

    @Provides
    fun provideFilterProductsUseCase(repository: StockRepository): FilterProductsUseCase {
        return FilterProductsUseCaseImpl(repository)
    }

    @Provides
    fun provideSearchProductsUseCase(repository: StockRepository): SearchProductsUseCase {
        return SearchProductsUseCaseImpl(repository)
    }

    @Provides
    fun provideStockRepository(productDao: ProductDao): StockRepository = StockRepositoryImpl(productDao)

    @Provides
    fun provideProductDao(stockDataSource: StockDataSource): ProductDao = stockDataSource.getProductDao()

    @Provides
    fun provideCategoryDao(stockDataSource: StockDataSource): CategoryDao = stockDataSource.getCategoryDao()

    @Provides
    fun provideFormatDao(stockDataSource: StockDataSource): FormatDao = stockDataSource.getFormatDao()


    @Singleton
    @Provides
    fun provideStockDataSource(@ApplicationContext context: Context): StockDataSource = getStockDataSource(context)

    @Provides
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository = CategoryRepositoryImpl(categoryDao)

    @Provides
    fun provideFormatRepository(formatDao: FormatDao): FormatRepository = FormatRepositoryImpl(formatDao)
}