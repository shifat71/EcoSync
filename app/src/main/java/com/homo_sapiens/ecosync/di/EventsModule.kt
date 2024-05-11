package com.homo_sapiens.ecosync.di

import android.content.Context
import com.homo_sapiens.ecosync.data.repository.create.CreateEventRepositoryImpl
import com.homo_sapiens.ecosync.data.repository.events.EventDetailRepositoryImpl
import com.homo_sapiens.ecosync.data.repository.events.EventsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EventsModule {

    @Provides
    @Singleton
    fun provideEventsRepository(): EventsRepositoryImpl = EventsRepositoryImpl()

    @Provides
    @Singleton
    fun provideEventDetailRepository(): EventDetailRepositoryImpl = EventDetailRepositoryImpl()

   }