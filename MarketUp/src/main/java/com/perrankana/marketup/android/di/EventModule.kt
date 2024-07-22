package com.perrankana.marketup.android.di

import com.perrankana.marketup.events.repositories.CurrentEventRepository
import com.perrankana.marketup.events.repositories.CurrentEventRepositoryImpl
import com.perrankana.marketup.events.useCases.GetCurrentEventUseCase
import com.perrankana.marketup.events.useCases.GetCurrentEventUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class EventModule {

    @Provides
    fun provideGetCurrentEventUseCase(repository: CurrentEventRepository) : GetCurrentEventUseCase {
        return GetCurrentEventUseCaseImpl(repository)
    }

    @Provides
    fun provideCurrentEventRepository(): CurrentEventRepository = CurrentEventRepositoryImpl
}