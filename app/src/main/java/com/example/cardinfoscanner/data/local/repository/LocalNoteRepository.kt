package com.example.cardinfoscanner.data.local.repository

import com.example.cardinfoscanner.data.base.NoteDataSource
import com.example.cardinfoscanner.data.base.NoteRepository
import com.example.cardinfoscanner.stateholder.note.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalNoteRepository @Inject constructor(
    private val noteDataSource: NoteDataSource
): NoteRepository {
    override fun getNotList(): Flow<List<Note>> = noteDataSource.getNoteList()
}