package at.ac.fhstp.pixelpad.data.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import at.ac.fhstp.pixelpad.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Locale


@Entity(tableName = "note")
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        // List of note colors
        val noteColors = listOf(
            Color(Priority1),
            Color(Priority2),
            Color(Priority3),
            Color(Priority4)
        )

        // Mapping of color values (as Int) to their corresponding names for sorting
        val colorNameMap = mapOf(
            Priority1 to "Priority1",
            Priority2 to "Priority2",
            Priority3 to "Priority3",
            Priority4 to "Priority4"
        )
    }
    fun getFormattedDate(): String {
        val formatter = SimpleDateFormat("dd MM yyyy\n HH:mm", Locale.getDefault())
        return formatter.format(timestamp)
    }
}

// Custom exception for invalid notes
class InvalidNoteException(message: String) : Exception(message)
