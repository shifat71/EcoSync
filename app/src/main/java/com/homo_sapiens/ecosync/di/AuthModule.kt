package com.homo_sapiens.ecosync.di

import com.homo_sapiens.ecosync.data.repository.auth.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideSignupRepository(): SignupRepositoryImpl = SignupRepositoryImpl()

    @Singleton
    @Provides
    fun provideSplashRepository(): SplashRepositoryImpl = SplashRepositoryImpl()

    @Singleton
    @Provides
    fun provideLoginRepository(): LoginRepositoryImpl = LoginRepositoryImpl()

    @Singleton
    @Provides
    fun provideIntroRepository(): IntroRepositoryImpl = IntroRepositoryImpl()

    @Singleton
    @Provides
    fun provideVerifyEmailRepository(): VerifyEmailRepositoryImpl = VerifyEmailRepositoryImpl()
}