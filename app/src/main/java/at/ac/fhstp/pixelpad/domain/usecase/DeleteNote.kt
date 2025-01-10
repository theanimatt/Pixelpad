package at.ac.fhstp.pixelpad.domain.usecase

import at.ac.fhstp.pixelpad.data.model.Note
import at.ac.fhstp.pixelpad.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNote @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNoteRepository(note)
    }
}