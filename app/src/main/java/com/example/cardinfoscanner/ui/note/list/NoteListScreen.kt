package com.example.cardinfoscanner.ui.note.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cardinfoscanner.R
import com.example.cardinfoscanner.stateholder.note.Note
import com.example.cardinfoscanner.stateholder.note.NoteListState
import com.example.cardinfoscanner.stateholder.note.rememberNoteListState
import com.example.cardinfoscanner.ui.common.MenuIconTopAppBar
import com.example.cardinfoscanner.ui.common.NormalDialog
import kotlinx.coroutines.launch

@Composable
fun NoteListScreen(
    state: NoteListState,
    onClickMenuButton: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            MenuIconTopAppBar(
                title = "Note",
                menuIcon = painterResource(R.drawable.ic_camera),
                onClickMenuButton = onClickMenuButton
            )
        },
        contentColor = MaterialTheme.colorScheme.onSurface,
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.padding(16.dp)
            ) { data ->
                Snackbar(
                    action = {
                        TextButton(onClick = state.onCancelRemove) {
                            data.visuals.actionLabel?.let { Text(text = it) }
                        }
                    }
                ) {
                    Text(text = data.visuals.message)
                }
            }
        }
    ) { paddingValues ->

        if(state.noteList.value.isEmpty()) {
            EmptyNoteItem(
                Modifier.padding(paddingValues = paddingValues),
                onClick = onClickMenuButton
            )
            return@Scaffold
        }

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
                NoteItem(
                    note = item,
                    removeNote = {
                        state.onRemoveNote(item)
                        state.uiState.scope.launch {
                            snackBarHostState.showSnackbar(
                                message = "삭제 완료",
                                actionLabel = "실행 취소",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    removeNote: () -> Unit
) {
    Card(
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "delete",
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp)
                        .clickable {
                            removeNote()
                        }
                )
            }
            Spacer(
                modifier = Modifier.height(5.dp)
            )
            Text(text = note.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, overflow = TextOverflow.Ellipsis, maxLines = 1)
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            Text(text = note.content, fontSize = 14.sp, overflow = TextOverflow.Ellipsis, maxLines = 4)
            Spacer(
                modifier = Modifier.height(15.dp)
            )
            Text(text = note.date, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun EmptyNoteItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clickable {
                    onClick()
                },
            painter = painterResource(id = R.drawable.ic_baseline_queue_24),
            contentDescription = "",
            tint = Color.DarkGray
        )
        Text(text = "New Note!!")
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteScreenPreview() {
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
        )
    )
    NoteListScreen(
        state = rememberNoteListState(
            noteList = remember { mutableStateOf(list) }
        ),
        onClickMenuButton = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun NoteItemPreview() {
    NoteItem(
        note = Note(0, "인공눈물 설명서", "하루에 1번 뚜껑을 따서...", "2023.03.03"),
        removeNote = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun EmptyNoteItemPreview() {
    EmptyNoteItem()
}
