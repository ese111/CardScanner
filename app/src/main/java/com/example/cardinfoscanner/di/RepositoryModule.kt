package com.example.cardinfoscanner.di

import com.example.cardinfoscanner.data.base.NoteDataSource
import com.example.cardinfoscanner.data.local.datasource.LocalNoteDataSource
import com.example.cardinfoscanner.data.local.repository.LocalNoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(
        dataSource: LocalNoteDataSource
    ) = LocalNoteRepository(dataSource)
}