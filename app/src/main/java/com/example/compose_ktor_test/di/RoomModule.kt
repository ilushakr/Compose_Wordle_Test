package com.example.compose_ktor_test.di

import android.app.Application
import androidx.room.Room
import com.example.compose_ktor_test.data.room.WordDao
import com.example.compose_ktor_test.data.room.WordDatabase
import com.example.compose_ktor_test.data.room.WordDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): WordDatabase {
        return Room.databaseBuilder(
            app,
            WordDatabase::class.java,
            DATABASE_NAME
        )
            .createFromAsset("databases/words.db")
            .build()
    }

    @Provides
    @Singleton
    fun providePostDao(wordDatabase: WordDatabase): WordDao {
        return wordDatabase.wordDao
    }
}