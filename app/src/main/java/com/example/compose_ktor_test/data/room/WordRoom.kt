package com.example.compose_ktor_test.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.compose_ktor_test.data.ui.Word

@Entity(tableName = "word")
data class WordRoom(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val type: String,
    val word: String,
    val wordExtended: String?
) {

    fun getUiWord() = Word(
        word = wordExtended ?: word,
        type = type,
        id = id
    )
}
