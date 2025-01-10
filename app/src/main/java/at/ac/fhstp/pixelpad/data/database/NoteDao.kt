package at.ac.fhstp.pixelpad.data.database

import androidx.room.*
import at.ac.fhstp.pixelpad.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAllNoteDatabase(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getSingleNoteDatabase(id: Int):Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteDatabase(note: Note)

    @Delete
    suspend fun deleteNoteDatabase(note: Note)
}