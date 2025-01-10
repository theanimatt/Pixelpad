package at.ac.fhstp.pixelpad.di

import at.ac.fhstp.pixelpad.data.database.NoteDatabase
import at.ac.fhstp.pixelpad.data.repository.NoteRepositoryImpl
import at.ac.fhstp.pixelpad.domain.repository.NoteRepository
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
    fun provideNoteRepository(noteDatabase:NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(noteDatabase.noteDao)
    }
}