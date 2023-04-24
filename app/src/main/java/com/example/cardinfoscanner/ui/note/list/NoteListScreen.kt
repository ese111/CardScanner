package com.example.cardinfoscanner.ui.note.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cardinfoscanner.R
import com.example.cardinfoscanner.stateholder.note.Note
import com.example.cardinfoscanner.stateholder.note.NoteListState
import com.example.cardinfoscanner.stateholder.note.rememberNoteListState
import com.example.cardinfoscanner.ui.common.TopAppBar
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun NoteListScreen(
    state: NoteListState,
    onClickMenuButton: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = "Note",
                menuButtonVisible = true,
                menuIcon = painterResource(R.drawable.ic_camera),
                onClickMenuButton = onClickMenuButton
            )
        },
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier.padding(paddingValues = paddingValues),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            )
        ) {
            items(items = state.noteList.value, key = { note -> note.id }) { item ->
                NoteItem(note = item)
            }
        }
    }
}

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note
) {
    Card(
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(text = note.title, fontSize = 18.sp)
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            Text(text = note.content, overflow = TextOverflow.Ellipsis, maxLines = 4)
            Spacer(
                modifier = Modifier.height(15.dp)
            )
            Text(text = note.date, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    val list = listOf(
        Note(
            0,
            "description1",
            "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
            "22.04.24"
        ),
        Note(
            1,
            "description2",
            "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
            "22.04.24"
        ),
        Note(
            2,
            "description3",
            "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
            "22.04.24"
        ),
        Note(
            3,
            "description4",
            "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
            "22.04.24"
        ),
        Note(
            4,
            "description5",
            "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
            "22.04.24"
        ),
        Note(
            5,
            "description6",
            "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
            "22.04.24"
        ),
        Note(
            6,
            "description7",
            "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
            "22.04.24"
        ),
        Note(
            7,
            "description8",
            "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
            "22.04.24"
        ),
    )
    NoteListScreen(state = rememberNoteListState(noteList = MutableStateFlow(list))) {}
}

@Preview(showBackground = true)
@Composable
fun NoteItemPreview() {
    NoteItem(note = Note(0, "인공눈물 설명서", "하루에 1번 뚜껑을 따서...", "2023.03.03"))
}
