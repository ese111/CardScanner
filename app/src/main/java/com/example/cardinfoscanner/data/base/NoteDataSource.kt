package com.example.cardinfoscanner.data.base

import com.example.cardinfoscanner.data.local.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {
    fun getNoteList(): Flow<List<Note>>

    suspend fun setNoteList(note: Note)

    suspend fun removeNote(note: Note)

    suspend fun cancelRemove()

    fun getNoteDetail(id: Long): Note
}