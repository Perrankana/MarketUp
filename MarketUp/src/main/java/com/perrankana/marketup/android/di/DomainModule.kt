package com.perrankana.marketup.android.di

import com.perrankana.marketup.repositories.CurrentEventRepository
import com.perrankana.marketup.repositories.CurrentEventRepositoryImpl
import com.perrankana.marketup.useCases.GetCurrentEventUseCase
import com.perrankana.marketup.useCases.GetCurrentEventUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DomainModule {

    @Provides
    fun provideGetCurrentEventUseCase(repository: CurrentEventRepository) : GetCurrentEventUseCase{
        return GetCurrentEventUseCaseImpl(repository)
    }

    @Provides
    fun provideCurrentEventRepository(): CurrentEventRepository = CurrentEventRepositoryImpl
}