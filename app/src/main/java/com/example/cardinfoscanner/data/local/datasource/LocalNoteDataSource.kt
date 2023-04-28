package com.example.cardinfoscanner.data.local.datasource

import com.example.cardinfoscanner.data.base.NoteDataSource
import com.example.cardinfoscanner.data.local.storage.NoteStorage
import com.example.cardinfoscanner.stateholder.note.Note
import javax.inject.Inject

class LocalNoteDataSource @Inject constructor(
    private val noteStorage: NoteStorage
) : NoteDataSource {
    override fun getNoteList() = noteStorage.noteList

    override suspend fun setNoteList(note: Note) = noteStorage.setNoteList(note)
}
