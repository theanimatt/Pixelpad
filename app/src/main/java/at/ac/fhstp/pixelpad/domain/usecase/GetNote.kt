package at.ac.fhstp.pixelpad.domain.usecase

import at.ac.fhstp.pixelpad.data.model.Note
import at.ac.fhstp.pixelpad.domain.repository.NoteRepository
import javax.inject.Inject

class GetNote @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getSingleNoteRepository(id)
    }
}