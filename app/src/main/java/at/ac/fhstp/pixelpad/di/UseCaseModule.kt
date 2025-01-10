package at.ac.fhstp.pixelpad.di

import at.ac.fhstp.pixelpad.domain.repository.NoteRepository
import at.ac.fhstp.pixelpad.domain.usecase.AddNote
import at.ac.fhstp.pixelpad.domain.usecase.DeleteNote
import at.ac.fhstp.pixelpad.domain.usecase.GetNote
import at.ac.fhstp.pixelpad.domain.usecase.GetNotes
import at.ac.fhstp.pixelpad.domain.usecase.NoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCase {
        return NoteUseCase(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}