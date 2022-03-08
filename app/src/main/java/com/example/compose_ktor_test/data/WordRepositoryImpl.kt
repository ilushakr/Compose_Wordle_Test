package com.example.compose_ktor_test.data

import com.example.compose_ktor_test.data.room.WordDao
import com.example.compose_ktor_test.data.ui.Word
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(private val wordDao: WordDao) : WordRepository {

    override suspend fun getWord(word: String): Word? {
        return wordDao.getWord(word = word)?.getUiWord()
    }

    override suspend fun getRandomWord(): Word {
        val t = wordDao.getRandomWord()
        return t.getUiWord()
    }

}