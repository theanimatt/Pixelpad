package at.ac.fhstp.pixelpad.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import at.ac.fhstp.pixelpad.domain.util.NoteOrder
import at.ac.fhstp.pixelpad.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Box(
        modifier = modifier
            .padding( top = 0.dp)
            .border(4.dp, Color.DarkGray)
            .background(
                color = Color.Gray,
                shape =  RectangleShape
            )
            .padding(14.dp)
    ) {

        Column {
            Text(
                text = "Sort by",
                style = MaterialTheme.typography.bodyMedium,
                color = Color( 0xffffffff),
                fontFamily = textFont
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                DefaultRadioButton(
                    text = "Title",
                    selected = noteOrder is NoteOrder.Title,
                    onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
                )
                Spacer(modifier = Modifier.width(10.dp))
                DefaultRadioButton(
                    text = "Date",
                    selected = noteOrder is NoteOrder.Date,
                    onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Row(
                modifier = Modifier
            ) {
                DefaultRadioButton(
                    text = "Color",
                    selected = noteOrder is NoteOrder.Color,
                    onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
                )
                Spacer(modifier = Modifier.width(10.dp))
                DefaultRadioButton(
                    text = "Content Length",
                    selected = noteOrder is NoteOrder.ContentLength,
                    onSelect = { onOrderChange(NoteOrder.ContentLength(noteOrder.orderType)) }
                )
            }
            Spacer(modifier = Modifier.height(26.dp))
            Text(
                text = "Type",
                style = MaterialTheme.typography.bodyMedium,
                color = Color( 0xffffffff),
                fontFamily = textFont
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                DefaultRadioButton(
                    text = "Ascending",
                    selected = noteOrder.orderType is OrderType.Ascending,
                    onSelect = {
                        onOrderChange(noteOrder.copy(OrderType.Ascending))
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
                DefaultRadioButton(
                    text = "Descending",
                    selected = noteOrder.orderType is OrderType.Descending,
                    onSelect = {
                        onOrderChange(noteOrder.copy(OrderType.Descending))
                    }
                )
            }
        }
    }
}

