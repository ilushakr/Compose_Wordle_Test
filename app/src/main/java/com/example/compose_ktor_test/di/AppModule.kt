package com.example.compose_ktor_test.di

import com.example.compose_ktor_test.data.WordRepository
import com.example.compose_ktor_test.data.WordRepositoryImpl
import com.example.compose_ktor_test.data.room.WordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(wordDao: WordDao): WordRepository {
        return WordRepositoryImpl(wordDao = wordDao)
    }

}