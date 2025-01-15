package at.ac.fhstp.pixelpad.ui.view.edit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhstp.pixelpad.data.model.InvalidNoteException
import at.ac.fhstp.pixelpad.data.model.Note
import at.ac.fhstp.pixelpad.domain.usecase.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.content.Context
import at.ac.fhstp.pixelpad.R
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val noteUseCase: NoteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle: MutableState<NoteTextFieldState> = mutableStateOf(
        NoteTextFieldState(stateHint = context.getString(R.string.noteTitle) )
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent: MutableState<NoteTextFieldState> = mutableStateOf(
        NoteTextFieldState(stateHint = context.getString(R.string.noteText))
    )
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor: MutableState<Int> = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.takeIf { it != -1 }?.let { noteId ->
            viewModelScope.launch {
                noteUseCase.getNote(noteId)?.let { note ->
                    currentNoteId = note.id
                    updateNoteState(note.title, note.content, note.color)
                }
            }
        }
    }

    private fun updateNoteState(title: String, content: String, color: Int) {
        _noteTitle.value = _noteTitle.value.copy(stateText = title, stateIsHintVisible = false)
        _noteContent.value = _noteContent.value.copy(stateText = content, stateIsHintVisible = false)
        _noteColor.value = color
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> _noteTitle.value = _noteTitle.value.copy(stateText = event.value)
            is AddEditNoteEvent.ChangeTitleFocus -> toggleHintVisibility(_noteTitle, event.focusState.isFocused)
            is AddEditNoteEvent.EnteredContent -> _noteContent.value = _noteContent.value.copy(stateText = event.value)
            is AddEditNoteEvent.ChangeContentFocus -> toggleHintVisibility(_noteContent, event.focusState.isFocused)
            is AddEditNoteEvent.ChangeColor -> _noteColor.value = event.color
            is AddEditNoteEvent.SaveNote -> saveNote()
        }
    }

    private fun toggleHintVisibility(field: MutableState<NoteTextFieldState>, isFocused: Boolean) {
        field.value = field.value.copy(
            stateIsHintVisible = !isFocused && field.value.stateText.isBlank()
        )
    }

    private fun saveNote() {
        viewModelScope.launch {
            try {
                noteUseCase.addNote(
                    Note(
                        title = _noteTitle.value.stateText,
                        content = _noteContent.value.stateText,
                        timestamp = System.currentTimeMillis(),
                        color = _noteColor.value,
                        id = currentNoteId,
                    )
                )
                _eventFlow.emit(UiEvent.SaveNote)
            } catch (e: InvalidNoteException) {
                _eventFlow.emit(UiEvent.ShowSnackbar(e.message ?: "Failed to save note"))
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}
