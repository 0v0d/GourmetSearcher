package com.example.gourmetsearcher.ui.screen.seachlocation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gourmetsearcher.R

@Composable
fun ErrorContent(
    isGrated: Boolean,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    onOpenSettings: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = if (isGrated) {
                stringResource(R.string.search_location_error_message)
            } else {
                stringResource(
                    R.string.search_location_permission_denied_message
                )
            },
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            OutlinedButton(
                onClick = onRetry,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 30.dp)
            ) {
                Text(stringResource(R.string.common_retry))
            }
            OutlinedButton(
                onClick = onOpenSettings,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 30.dp)
            ) {
                Text(stringResource(R.string.search_location_setting))
            }
        }
    }
}
