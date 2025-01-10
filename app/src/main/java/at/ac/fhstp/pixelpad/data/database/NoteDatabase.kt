package at.ac.fhstp.pixelpad.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import at.ac.fhstp.pixelpad.data.model.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}