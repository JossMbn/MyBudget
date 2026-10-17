package com.jmabilon.mybudget.feature.account.presentation

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
fun AccountScreenRoot(
    navigator: AccountNavigator
) {
    AccountScreen(
        navigator = navigator
    )
}

@Composable
private fun AccountScreen(
    navigator: AccountNavigator
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        AccountScreenContent(
            contentPadding = innerPadding,
            navigator = navigator
        )
    }
}

@Composable
private fun AccountScreenContent(
    contentPadding: PaddingValues = PaddingValues(),
    navigator: AccountNavigator
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Account Screen",
            color = Color.Black
        )
    }
}
