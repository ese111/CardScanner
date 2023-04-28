package com.example.cardinfoscanner.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cardinfoscanner.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTopAppBar(
    title: String,
    backButtonVisible: Boolean = false,
    onClickBackButton: (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = if (backButtonVisible) {
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                } else {
                    Modifier.fillMaxWidth()
                },
            )
        },
        modifier = Modifier.padding(16.dp),
        navigationIcon = {
            if (backButtonVisible) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .clickable {
                            onClickBackButton?.invoke()
                        }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuIconTopAppBar(
    title: String,
    backButtonVisible: Boolean = false,
    menuIcon: Painter = painterResource(id = R.drawable.icon_menu),
    onClickBackButton: () -> Unit = {},
    onClickMenuButton: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = if (backButtonVisible) {
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                } else {
                    Modifier.fillMaxWidth()
                }
            )
        },
        modifier = Modifier.padding(16.dp),
        navigationIcon = {
            if (backButtonVisible) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .clickable {
                            onClickBackButton()
                        }
                )
            }
        },
        actions = {
            Icon(
                painter = menuIcon,
                contentDescription = "Menu",
                modifier = Modifier
                    .clickable {
                        onClickMenuButton()
                    }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuTextTopAppBar(
    title: String,
    backButtonVisible: Boolean = false,
    menuText: String = "",
    onClickBackButton: () -> Unit = {},
    onClickMenuButton: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = if (backButtonVisible) {
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                } else {
                    Modifier.fillMaxWidth()
                }
            )
        },
        modifier = Modifier.padding(16.dp),
        navigationIcon = {
            if (backButtonVisible) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .clickable {
                            onClickBackButton()
                        }
                )
            }
        },
        actions = {
            Text(
                modifier = Modifier.clickable { onClickMenuButton() },
                text = menuText,
                textAlign = TextAlign.Center
            )
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun TopAppBarPreview() {
    BasicTopAppBar(title = "Note", backButtonVisible = false)
}

@Composable
@Preview(showBackground = true)
private fun MenuIconTopAppBarPreview() {
    MenuIconTopAppBar(
        title = "Note",
        backButtonVisible = true,
        menuIcon = painterResource(id = R.drawable.ic_camera)
    )
}

@Composable
@Preview(showBackground = true)
private fun MenuTextTopAppBarPreview() {
    MenuTextTopAppBar(title = "Note", backButtonVisible = true, menuText = "저장")
}
