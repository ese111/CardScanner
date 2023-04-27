package com.example.cardinfoscanner.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NoteDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object PreferenceKeys {
        val NOTES = stringPreferencesKey("notes")
    }
    private val Context.notesDataStore by preferencesDataStore("notes")

    fun getNoteList(): Flow<Preferences> = context.notesDataStore.data

    suspend fun setNoteList(json: String) {
        context.notesDataStore.edit { pref ->
            pref[NOTES] = json
        }
    }
}