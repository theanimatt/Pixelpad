package at.ac.fhstp.pixelpad.data.repository

import at.ac.fhstp.pixelpad.data.model.Note
import at.ac.fhstp.pixelpad.data.database.NoteDao
import at.ac.fhstp.pixelpad.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getAllNoteRepository(): Flow<List<Note>> {
        return noteDao.getAllNoteDatabase()
    }

    override suspend fun getSingleNoteRepository(id: Int):Note? {
        return noteDao.getSingleNoteDatabase(id)
    }

    override suspend fun insertNoteRepository(note: Note) {
        noteDao.insertNoteDatabase(note)
    }

    override suspend fun deleteNoteRepository(note:Note) {
        noteDao.deleteNoteDatabase(note)
    }
}