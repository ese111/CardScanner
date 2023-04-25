package com.example.cardinfoscanner.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cardinfoscanner.R

@Composable
fun TopAppBar(
    title: String,
    backButtonVisible: Boolean = false,
    onClickBackButton: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (backButtonVisible) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable {
                        onClickBackButton?.invoke()
                    }
            )
            Spacer(modifier = Modifier.width(15.dp))
        }

        Text(
            text = title
        )
    }
}

@Composable
fun MenuIconTopAppBar(
    title: String,
    backButtonVisible: Boolean = false,
    menuIcon: Painter = painterResource(id = R.drawable.icon_menu),
    onClickBackButton: () -> Unit = {},
    onClickMenuButton: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (backButtonVisible) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable {
                        onClickBackButton()
                    }
            )
            Spacer(modifier = Modifier.width(15.dp))
        }

        Text(
            text = title
        )

        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = menuIcon,
            contentDescription = "Menu",
            modifier = Modifier
                .clickable {
                    onClickMenuButton()
                }
        )
    }
}

@Composable
fun MenuTextTopAppBar(
    title: String,
    backButtonVisible: Boolean = false,
    menuText: String = "",
    onClickBackButton: () -> Unit = {},
    onClickMenuButton: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (backButtonVisible) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable {
                        onClickBackButton()
                    }
            )
            Spacer(modifier = Modifier.width(15.dp))
        }

        Text(
            text = title
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(modifier = Modifier.clickable { onClickMenuButton() },text = menuText, textAlign = TextAlign.Center)
    }
}

@Composable
@Preview(showBackground = true)
private fun TopAppBarPreview() {
    TopAppBar(title = "Note",backButtonVisible = true)
}
@Composable
@Preview(showBackground = true)
private fun MenuIconTopAppBarPreview() {
    MenuIconTopAppBar(title = "Note",backButtonVisible = true, menuIcon = painterResource(id = R.drawable.ic_camera))
}
@Composable
@Preview(showBackground = true)
private fun MenuTextTopAppBarPreview() {
    MenuTextTopAppBar(title = "Note",backButtonVisible = true, menuText = "저장")
}
