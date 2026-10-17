package com.jmabilon.mybudget.feature.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun HomeScreenRoot(
    navigator: HomeNavigator
) {
    HomeScreen(
        navigator = navigator
    )
}

@Composable
private fun HomeScreen(
    navigator: HomeNavigator
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        HomeScreenContent(
            contentPadding = innerPadding,
            navigator = navigator
        )
    }
}

@Composable
private fun HomeScreenContent(
    contentPadding: PaddingValues = PaddingValues(),
    navigator: HomeNavigator
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Home Screen",
            color = Color.Black
        )
    }
}
