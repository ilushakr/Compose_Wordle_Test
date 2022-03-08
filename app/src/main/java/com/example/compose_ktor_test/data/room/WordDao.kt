package com.example.compose_ktor_test.data.room

import androidx.room.*

@Dao
interface WordDao {

    @Query("SELECT * FROM word WHERE word = :word")
    suspend fun getWord(word: String): WordRoom?

    @Query("SELECT * FROM word WHERE type = :type ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomWord(type: String = "noun"): WordRoom
}