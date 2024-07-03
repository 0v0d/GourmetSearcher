package com.example.gourmetsearcher.ui.screen.inputkeyword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gourmetsearcher.R
import com.example.gourmetsearcher.viewmodel.InputKeyWordViewModel

@Composable
fun InputKeyWordScreen(
    modifier: Modifier = Modifier,
    viewModel: InputKeyWordViewModel = hiltViewModel(),
    onNavigateToSearchLocation: (String, Int) -> Unit,
) {
    val inputText = viewModel.inputText.value
    val historyList by viewModel.historyListData.collectAsState()
    val isInputEmpty = inputText.isEmpty()

    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            InputKeyWordTopBar()
        },
    ) { paddingValues ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SearchTextField(
                query = inputText,
                onQueryChange = { newValue ->
                    viewModel.updateInputText(newValue)
                },
                modifier =
                Modifier
                    .fillMaxWidth()
                    .testTag("SearchBar"),
            )
            if (isInputEmpty) {
                KeyWordHistoryList(
                    historyList = historyList,
                    onItemClick = { text ->
                        viewModel.updateInputText(text)
                    },
                    onClearClick = {
                        viewModel.clearHistory()
                    },
                )
            } else {
                RangeList(
                    ranges = stringArrayResource(R.array.input_keyword_range_array),
                    onRangeSelected = { index ->
                        viewModel.saveHistoryItem(inputText)
                        viewModel.updateInputText("")
                        val offset = 1
                        onNavigateToSearchLocation(inputText, index + offset)
                    },
                )
            }
        }
    }
}

@Composable
private fun InputKeyWordTopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.input_keyword_top_bar_title)) }
    )
}
