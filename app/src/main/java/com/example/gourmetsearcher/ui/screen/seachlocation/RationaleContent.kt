package com.example.gourmetsearcher.ui.screen.seachlocation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.gourmetsearcher.R

@Composable
fun RationaleContent(
    onConfirmClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { },
        text = {
            Text(
                text = stringResource(
                    id = R.string.search_location_permission_required_message
                ),
                fontSize = 13.sp,
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirmClick,
            ) {
                Text(
                    text = stringResource(
                        id = R.string.common_ok
                    )
                )
            }
        },
        dismissButton = null,
    )
}
