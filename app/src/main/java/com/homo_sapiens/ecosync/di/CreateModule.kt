package com.homo_sapiens.ecosync.di

import android.content.Context
import com.homo_sapiens.ecosync.data.repository.create.CreateEventRepositoryImpl
import com.homo_sapiens.ecosync.data.repository.polls.CreatePollRepositoryImpl
import com.homo_sapiens.ecosync.data.repository.polls.PollsRepositoryImpl
import com.homo_sapiens.ecosync.data.repository.share.ShareRepositoryImpl
import com.homo_sapiens.ecosync.feature.issue_report.IssuesScreenRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CreateModule {

    @Singleton
    @Provides
    fun provideShareRepository(
        @ApplicationContext context: Context
    ): ShareRepositoryImpl = ShareRepositoryImpl(context = context)

    @Singleton
    @Provides
    fun provideCreatePollRepository(): CreatePollRepositoryImpl = CreatePollRepositoryImpl()

    @Singleton
    @Provides
    fun provideCreateEventRepository(
        @ApplicationContext context: Context
    ): CreateEventRepositoryImpl = CreateEventRepositoryImpl(context = context)

    @Singleton
    @Provides
    fun providePollsRepository(): PollsRepositoryImpl = PollsRepositoryImpl()

    @Singleton
    @Provides
    fun provideIssuesScreenRepository( @ApplicationContext context: Context): IssuesScreenRepositoryImpl = IssuesScreenRepositoryImpl(context=context)
}