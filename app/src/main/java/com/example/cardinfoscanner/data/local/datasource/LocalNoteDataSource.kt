package com.example.cardinfoscanner.data.local.datasource

import com.example.cardinfoscanner.data.base.NoteDataSource
import com.example.cardinfoscanner.data.local.model.Note
import com.example.cardinfoscanner.data.local.storage.NoteStorage
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalNoteDataSource @Inject constructor(
    private val noteStorage: NoteStorage
) : NoteDataSource {
    override fun getNoteList() = noteStorage.noteMap.map { it.values.toList() }

    override suspend fun setNoteList(note: Note) = noteStorage.setNoteList(note)
    override suspend fun removeNote(note: Note) = noteStorage.removeNote(note)

    override suspend fun cancelRemove() = noteStorage.cancelRemove()

    override fun getNoteDetail(id: Long) = noteStorage.getNoteDetail(id)
    override suspend fun saveNote(note: Note) = noteStorage.saveNote(note)

}
