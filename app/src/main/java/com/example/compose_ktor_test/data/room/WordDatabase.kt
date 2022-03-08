package com.example.compose_ktor_test.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WordRoom::class],
    version = 1
)
abstract class WordDatabase: RoomDatabase() {

    abstract val wordDao: WordDao

    companion object{
        const val DATABASE_NAME = "word.db"
    }
}