package com.example.compose_ktor_test.data

import com.example.compose_ktor_test.data.ui.Word

interface WordRepository {

    suspend fun getWord(word: String): Word?

    suspend fun getRandomWord(): Word

}