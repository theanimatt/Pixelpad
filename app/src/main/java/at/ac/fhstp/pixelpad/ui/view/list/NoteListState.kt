package at.ac.fhstp.pixelpad.ui.view.list

import at.ac.fhstp.pixelpad.data.model.Note
import at.ac.fhstp.pixelpad.domain.util.NoteOrder
import at.ac.fhstp.pixelpad.domain.util.OrderType

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
