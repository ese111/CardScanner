package com.example.cardinfoscanner.data.base

import com.example.cardinfoscanner.stateholder.note.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotList(): Flow<List<Note>>

    suspend fun setNoteList(list: List<Note>)

}