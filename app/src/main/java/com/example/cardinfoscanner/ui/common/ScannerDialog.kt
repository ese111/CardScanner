package com.example.cardinfoscanner.ui.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun NormalDialog(
    modifier: Modifier = Modifier,
    title: String,
    phrase: String,
    confirmText: String,
    dismissText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    AlertDialog(
        onDismissRequest = {

        },
        title = { Text(text = title, fontSize = 12.sp) },
        text = { Text(text = phrase, fontSize = 11.sp, maxLines = 5, overflow = TextOverflow.Ellipsis) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = dismissText)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                }
            ) {
                Text(text = confirmText)
            }
        }
    )
}