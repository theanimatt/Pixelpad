package at.ac.fhstp.pixelpad.ui.view.list

import at.ac.fhstp.pixelpad.data.model.Note
import at.ac.fhstp.pixelpad.domain.util.NoteOrder

sealed class NoteListEvent {
    data class Order(val noteOrder: NoteOrder): NoteListEvent()
    data class DeleteNote(val note: Note): NoteListEvent()
    data object RestoreNote: NoteListEvent()
    data object ToggleOrderSection: NoteListEvent()
}
