package com.example.compose_ktor_test.utils

object Utils {

    const val orderedCyrillicCharacters = "ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ"

    fun String.getOrEmpty(index: Int): String {
        return if (this.length - 1 < index) return ""
        else this[index].toString()
    }
}