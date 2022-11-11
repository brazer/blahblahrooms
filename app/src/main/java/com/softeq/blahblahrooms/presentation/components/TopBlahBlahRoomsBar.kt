package com.softeq.blahblahrooms.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.presentation.ui.BlahBlahRoomsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBlahBlahRoomsBar(
    title: String,
    backAction: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            actions()
        },
        navigationIcon = {
            if (backAction != null) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = null,
                    modifier = Modifier.clickable { backAction() }
                )
            }
        }
    )
}

@Preview
@Composable
fun AppTopBarDarkPreview() {
    BlahBlahRoomsTheme(useDarkTheme = true) {
        TopBlahBlahRoomsBar("Very very very very very very very long title") {}
    }
}

@Preview
@Composable
fun AppTopBarLightPreview() {
    BlahBlahRoomsTheme(useDarkTheme = false) {
        TopBlahBlahRoomsBar("Very very very very very very very long title") {}
    }
}