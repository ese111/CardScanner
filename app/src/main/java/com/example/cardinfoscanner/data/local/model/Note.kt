package com.example.cardinfoscanner.data.local.model

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Long = -1,
    val title: String = "",
    val content: String = "",
    val date: String = ""
): java.io.Serializable