package com.example.cardinfoscanner.data.base

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {
    fun getNoteList(): Flow<Preferences>

    suspend fun setNoteList(json: String)
}