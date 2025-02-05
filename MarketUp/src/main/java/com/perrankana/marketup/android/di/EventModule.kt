package com.perrankana.marketup.android.di

import android.content.Context
import com.perrankana.marketup.database.EventsDao
import com.perrankana.marketup.events.datasource.EventsDataSource
import com.perrankana.marketup.events.datasource.getEventsDataSource
import com.perrankana.marketup.events.repositories.CurrentEventRepository
import com.perrankana.marketup.events.repositories.CurrentEventRepositoryImpl
import com.perrankana.marketup.events.useCases.GetCurrentEventUseCase
import com.perrankana.marketup.events.useCases.GetCurrentEventUseCaseImpl
import com.perrankana.marketup.events.useCases.SaveCurrentEventUseCase
import com.perrankana.marketup.events.useCases.SaveCurrentEventUseCaseImpl
import com.perrankana.marketup.events.useCases.StartCurrentEventUseCase
import com.perrankana.marketup.events.useCases.StartCurrentEventUseCaseImpl
import com.perrankana.marketup.sale.repositories.TpvEventRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class EventModule {

    @Provides
    fun provideGetCurrentEventUseCase(repository: CurrentEventRepository, tpvEventRepository: TpvEventRepository) : GetCurrentEventUseCase {
        return GetCurrentEventUseCaseImpl(repository, tpvEventRepository)
    }

    @Provides
    fun provideSaveCurrentEventUseCase(repository: CurrentEventRepository) : SaveCurrentEventUseCase {
        return SaveCurrentEventUseCaseImpl(repository)
    }

    @Provides
    fun provideStartCurrentEventUseCase(repository: CurrentEventRepository, tpvEventRepository: TpvEventRepository): StartCurrentEventUseCase {
        return StartCurrentEventUseCaseImpl(repository, tpvEventRepository)
    }
    @Provides
    fun provideCurrentEventRepository(eventsDao: EventsDao): CurrentEventRepository = CurrentEventRepositoryImpl(eventsDao)

    @Provides
    fun provideEventsDao(dataSource: EventsDataSource): EventsDao = dataSource.getEventDao()

    @Singleton
    @Provides
    fun provideEventsDataSource(@ApplicationContext context: Context): EventsDataSource = getEventsDataSource(context)

}