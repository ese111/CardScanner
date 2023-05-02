package com.example.cardinfoscanner.data.base

import com.example.cardinfoscanner.stateholder.note.Note
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {
    fun getNoteList(): Flow<List<Note>>

    suspend fun setNoteList(note: Note)

    suspend fun removeNote(note: Note)

    suspend fun cancelRemove()
}