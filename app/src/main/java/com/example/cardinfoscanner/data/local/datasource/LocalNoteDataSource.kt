package com.example.cardinfoscanner.data.local.datasource

import com.example.cardinfoscanner.data.base.NoteDataSource
import com.example.cardinfoscanner.data.local.storage.NoteStorage
import javax.inject.Inject

class LocalNoteDataSource @Inject constructor(
    private val noteStorage: NoteStorage
): NoteDataSource {
    override fun getNoteList() = noteStorage.getNoteList()
}