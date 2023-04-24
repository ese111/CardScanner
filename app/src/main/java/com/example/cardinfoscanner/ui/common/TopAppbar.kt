package com.example.cardinfoscanner.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopAppBar(title: String) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            text = title
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun TopAppBarPreview() {
    TopAppBar(title = "Note")
}