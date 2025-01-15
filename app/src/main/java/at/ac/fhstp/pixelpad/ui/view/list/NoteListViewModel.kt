package at.ac.fhstp.pixelpad.ui.view.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhstp.pixelpad.data.model.Note
import at.ac.fhstp.pixelpad.domain.usecase.NoteUseCase
import at.ac.fhstp.pixelpad.domain.util.NoteOrder
import at.ac.fhstp.pixelpad.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase
) : ViewModel() {

    private val _state = mutableStateOf(NoteListState())
    val state: State<NoteListState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNotes(event.noteOrder)
            }

            is NoteListEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCase.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }

            is NoteListEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCase.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }

            is NoteListEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCase.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Color -> notes.sortedBy { it.color}
                        is NoteOrder.ContentLength -> notes.sortedBy { it.content.length }
                    }.let {
                        if (noteOrder.orderType == OrderType.Descending) it.reversed() else it
                    },
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}
