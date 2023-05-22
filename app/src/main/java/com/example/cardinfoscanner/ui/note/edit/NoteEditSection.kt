package com.example.cardinfoscanner.ui.note.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NoteEditorView(
    modifier: Modifier = Modifier,
    title: String = "",
    content: String = "",
    onTitleFocusChanged: (Boolean) -> Unit = {},
    onTitleChange: (String) -> Unit = {},
    onContentChange: (String) -> Unit = {},
    onContentFocusChanged: (Boolean) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(20.dp)
    ) {
        TextField(
            value = title,
            onValueChange = onTitleChange,
            placeholder = {
                Text(text = "제목을 입력해주세요.", color = Color.Gray)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { state -> onTitleFocusChanged(state.hasFocus) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            )
        )

        TextField(
            value = content,
            onValueChange = onContentChange,
            placeholder = {
                Text(
                    text = "내용을 입력해주세요.",
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .onFocusChanged { state -> onContentFocusChanged(state.hasFocus) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}