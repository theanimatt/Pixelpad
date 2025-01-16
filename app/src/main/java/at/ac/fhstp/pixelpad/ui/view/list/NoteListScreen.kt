package at.ac.fhstp.pixelpad.ui.view.list

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import at.ac.fhstp.pixelpad.R
import at.ac.fhstp.pixelpad.data.model.Note
import at.ac.fhstp.pixelpad.ui.component.OrderSection
import at.ac.fhstp.pixelpad.ui.navigation.NoteScreen
import at.ac.fhstp.pixelpad.ui.theme.Background
import org.w3c.dom.Text

val titleFont = FontFamily(
    Font(R.font.pixeltwist)
)

val textFont = FontFamily(
    Font(R.font.pixeled)
)

@Composable
fun NoteListScreen(
    navController: NavController,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clickable {
                        navController.navigate(NoteScreen.AddEditNoteScreen.route)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "Add",
                    tint = Color(0xffCEE5F2)

                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(25.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PixelPad",
                    // style = MaterialTheme.typography.headlineLarge,
                    fontFamily = titleFont,
                    fontSize = 60.sp
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(NoteListEvent.ToggleOrderSection)
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.pixelsort),
                        contentDescription = "Sort",
                        modifier = Modifier.size(55.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NoteListEvent.Order(it))
                    }
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(12.dp)
                            .clickable {
                                navController.navigate(
                                    NoteScreen.AddEditNoteScreen.route +
                                            "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(NoteListEvent.DeleteNote(note))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel = "Undo",
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NoteListEvent.RestoreNote)
                                }
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {

            val rectWidth = 420f // width in pixels
            val rectHeight = 420f // height in pixels

            // Draw the rectangle with a border
            val borderWidth = 35f // border width in pixels
            val borderColor = Color(0xff6F7170) // Border color

            drawRoundRect(
                color = borderColor,
                size = Size(rectWidth, rectHeight),
                style = Stroke(width = borderWidth),  // Stroke is used for borders
                cornerRadius = CornerRadius.Zero  // Optional: Rounded corners
            )

            drawRoundRect(
                color = Color(note.color),
                size = Size(rectWidth,rectHeight),
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.bodyMedium,
                color = Background,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                fontFamily = textFont
            )

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .padding(top = 110.dp)
        ) {
            Text(
                text = note.getFormattedDate(),
                fontFamily = textFont,
                color = Background,
                fontSize = 8.sp
            )
        }
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.BottomEnd)
                .offset(
                    x = (8).dp
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.pixeltrash),
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}