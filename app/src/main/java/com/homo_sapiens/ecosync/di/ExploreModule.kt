package com.homo_sapiens.ecosync.di

import com.homo_sapiens.ecosync.data.repository.explore.ExploreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExploreModule {

    @Provides
    @Singleton
    fun provideExploreRepository(): ExploreRepositoryImpl = ExploreRepositoryImpl()
}