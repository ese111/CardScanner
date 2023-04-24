package com.example.cardinfoscanner.di

import com.example.cardinfoscanner.data.local.datasource.LocalNoteDataSource
import com.example.cardinfoscanner.data.local.storage.NoteStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun providesNoteDataSource(
        noteStorage: NoteStorage
    ) = LocalNoteDataSource(noteStorage)
}