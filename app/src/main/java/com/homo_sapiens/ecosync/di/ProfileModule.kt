package com.homo_sapiens.ecosync.di

import android.content.Context
import com.homo_sapiens.ecosync.data.repository.profile.*
import com.homo_sapiens.ecosync.feature.issue_report.IssuesScreenRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Singleton
    @Provides
    fun provideProfileRepository(): ProfileRepositoryImpl = ProfileRepositoryImpl()

    @Singleton
    @Provides
    fun provideSettingsRepository(): SettingsRepositoryImpl = SettingsRepositoryImpl()

    @Singleton
    @Provides
    fun provideEditProfileRepository(
        @ApplicationContext context: Context
    ): EditProfileRepositoryImpl = EditProfileRepositoryImpl(context = context)

    @Singleton
    @Provides
    fun provideDepartmentRepository(): DepartmentRepositoryImpl = DepartmentRepositoryImpl()

    @Singleton
    @Provides
    fun provideUpdatePasswordRepository(): UpdatePasswordRepositoryImpl = UpdatePasswordRepositoryImpl()

    @Singleton
    @Provides
    fun provideDeleteAccountRepository(): DeleteAccountRepositoryImpl = DeleteAccountRepositoryImpl()

    @Singleton
    @Provides
    fun provideVerifyAccountRepository(): VerifyAccountRepositoryImpl = VerifyAccountRepositoryImpl()

    @Singleton
    @Provides
    fun provideProfilePostsRepository(): ProfilePostsRepositoryImpl = ProfilePostsRepositoryImpl()

    @Singleton
    @Provides
    fun provideProfilePollsRepository(): ProfilePollsRepositoryImpl = ProfilePollsRepositoryImpl()

    @Singleton
    @Provides
    fun provideProfileLikedEventsRepository(): ProfileLikedEventsRepositoryImpl = ProfileLikedEventsRepositoryImpl()

    @Singleton
    @Provides
    fun provideProfileAttendedEventsRepository(): ProfileAttendedEventsRepositoryImpl = ProfileAttendedEventsRepositoryImpl()
}