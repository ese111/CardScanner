package com.example.cardinfoscanner.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NoteDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object PreferenceKeys {
        val NOTES = stringPreferencesKey("notes")
    }
    private val Context.notesDataStore by preferencesDataStore("note")

    fun getNoteList() = context.notesDataStore.data

    suspend fun setNoteList(json: String) {
        context.notesDataStore.edit { pref ->
            pref[NOTES] = json
        }
    }
}