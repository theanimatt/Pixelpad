package at.ac.fhstp.pixelpad.domain.usecase

import at.ac.fhstp.pixelpad.data.model.Note
import at.ac.fhstp.pixelpad.data.model.Note.Companion.colorNameMap
import at.ac.fhstp.pixelpad.domain.repository.NoteRepository
import at.ac.fhstp.pixelpad.domain.util.NoteOrder
import at.ac.fhstp.pixelpad.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotes @Inject constructor(
    private val repository: NoteRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getAllNoteRepository().map { notes ->
            when(noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when(noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Color -> notes.sortedBy { colorNameMap[it.color] ?: "" }
                        is NoteOrder.ContentLength -> notes.sortedBy { it.content.length }
                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Color -> notes.sortedByDescending { colorNameMap[it.color] ?: "" }
                        is NoteOrder.ContentLength -> notes.sortedByDescending { it.content.length }
                    }
                }
            }
        }
    }

}
