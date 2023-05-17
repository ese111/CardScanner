package com.example.cardinfoscanner.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.cardinfoscanner.R
import com.example.cardinfoscanner.stateholder.common.TopBarDropMenuState
import com.example.cardinfoscanner.stateholder.common.rememberDropMenuState
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTopAppBar(
    title: String,
    backButtonVisible: Boolean = false,
    onClickBackButton: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            if (backButtonVisible) {
                Row {
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clickable {
                                onClickBackButton()
                            }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuIconTopAppBar(
    title: String,
    backButtonVisible: Boolean = false,
    menuIcon: Painter = painterResource(id = R.drawable.ic_menu),
    onClickBackButton: () -> Unit = {},
    onClickMenuButton: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            if (backButtonVisible) {
                Row {
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clickable {
                                onClickBackButton()
                            }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        },
        actions = {
            Row {
                Icon(
                    painter = menuIcon,
                    contentDescription = "Menu",
                    modifier = Modifier
                        .clickable {
                            onClickMenuButton()
                        }
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    )
}

data class DropMenuState(
    val name: String,
    val onClick: () -> Unit
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropMenuTopAppBar(
    title: String,
    backButtonVisible: Boolean = false,
    menuIcon: Painter = rememberVectorPainter(image = Icons.Filled.Menu),
    dropMenuItems: List<DropMenuState> = emptyList(),
    onClickBackButton: () -> Unit = {},
    dropMenuState: TopBarDropMenuState = rememberDropMenuState(),
    openDropMenu: () -> Unit = {},
    closeDropMenu: () -> Unit = {}
) {
    Timber.i("dropMenuState.dropMenuState.value : ${dropMenuState.dropMenuState.value}")
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            DropdownMenu(
                expanded = dropMenuState.dropMenuState.value,
                onDismissRequest = dropMenuState.closeDropMenu,
                offset = DpOffset(x = 240.dp, y = 50.dp),
                modifier = Modifier
            ) {
                dropMenuItems.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(text = it.name)
                        },
                        onClick = {
                            it.onClick()
                            dropMenuState.closeDropMenu()
                        }
                    )
                }
            }
        }

        TopAppBar(
            title = {
                Text(
                    text = title
                )
            },
            navigationIcon = {
                if (backButtonVisible) {
                    Row {
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier
                                .clickable { onClickBackButton() }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            },
            actions = {
                Row {
                    Icon(
                        painter = menuIcon,
                        contentDescription = "Menu",
                        modifier = Modifier
                            .clickable { dropMenuState.openDropMenu() }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        )
    }
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
                text = title
            )
        },
        navigationIcon = {
            if (backButtonVisible) {
                Row {
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clickable { onClickBackButton() }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        },
        actions = {
            Row {
                Text(
                    modifier = Modifier.clickable { onClickMenuButton() },
                    text = menuText,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun TopAppBarPreview() {
    BasicTopAppBar(title = "Note", backButtonVisible = true)
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
@Composable
@Preview(showBackground = true)
private fun DropMenuTopAppBarPreview() {
    Scaffold(
        topBar = {
            DropMenuTopAppBar(title = "Note", backButtonVisible = true, dropMenuItems = listOf(
                DropMenuState("수정하기", {}),
                DropMenuState("추가하기", {})
            )
            )
        }
    ) {
        Column(Modifier.padding(it)) {
            
        }
    }

}
