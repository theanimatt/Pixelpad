package at.ac.fhstp.pixelpad.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import at.ac.fhstp.pixelpad.domain.util.NoteOrder
import at.ac.fhstp.pixelpad.ui.theme.*

@Entity(tableName = "note")
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(Priority1, Priority2, Priority3, Priority4)
    }
}

class InvalidNoteException(message: String): Exception(message)