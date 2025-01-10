package at.ac.fhstp.pixelpad.domain.usecase

import at.ac.fhstp.pixelpad.data.model.InvalidNoteException
import at.ac.fhstp.pixelpad.data.model.Note
import at.ac.fhstp.pixelpad.domain.repository.NoteRepository
import javax.inject.Inject

class AddNote @Inject constructor(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("The title of the note cannot be empty.")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("The content of the note cannot be empty.")
        }
        repository.insertNoteRepository(note)
    }
}