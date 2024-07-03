package com.example.gourmetsearcher.ui.screen.inputkeyword

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gourmetsearcher.R
import kotlinx.collections.immutable.ImmutableList

/**
 * キーワード履歴リスト
 * @param historyList 履歴リスト
 * @param onItemClick 履歴アイテムクリック時のコールバック
 * @param onClearClick 履歴クリアボタンクリック時のコールバック
 * @param modifier Modifier
 */
@Composable
fun KeyWordHistoryList(
    historyList: ImmutableList<String>,
    onItemClick: (String) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        LazyColumn {
            items(historyList) { item ->
                ListContent(
                    item = item,
                    onItemClick = onItemClick
                )
            }
        }
        if (historyList.isNotEmpty()) {
            ClearButton(onClearClick)
        }
    }
}

/**
 * 履歴リストアイテム
 * @param item 履歴アイテム
 * @param onItemClick 履歴アイテムクリック時のコールバック
 */
@Composable
private fun ListContent(
    item: String,
    onItemClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.History,
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp)
        )

        Text(
            text = item,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.size(24.dp))
    }
    HorizontalDivider(
        thickness = 1.dp,
        color = Color.Gray
    )
}

/**
 * クリアボタン
 * @param onClearClick クリアボタンクリック時のコールバック
 */
@Composable
private fun ClearButton(onClearClick: () -> Unit) {
    OutlinedButton(
        onClick = onClearClick,
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
    ) {
        Text(
            stringResource(
                id = R.string.input_keyword_key_word_clear
            )
        )
    }
}
