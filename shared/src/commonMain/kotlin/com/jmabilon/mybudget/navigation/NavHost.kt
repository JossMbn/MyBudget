package com.jmabilon.mybudget.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.jmabilon.mybudget.feature.account.presentation.AccountScreenRoot
import com.jmabilon.mybudget.feature.account.presentation.rememberAccountNavigator
import com.jmabilon.mybudget.feature.home.presentation.HomeScreenRoot
import com.jmabilon.mybudget.feature.home.presentation.rememberHomeNavigator

@Composable
fun NavHost() {
    val navState = rememberNavState(
        startRoute = HomeScreen,
        topLevelRoutes = setOf(
            HomeScreen,
            AccountScreen
        ),
        primaryTopLevelRoute = HomeScreen,
    )

    val navigator = rememberNavigator(navState = navState)

    val entryProvider = entryProvider {
        screens(
            navigator = navigator,
            onBack = navigator::goBack,
        )
    }

    NavScaffold(
        navState = navState,
        navigator = navigator
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            entries = navState.toDecoratedEntries(entryProvider),
            onBack = navigator::goBack,
        )
    }
}

// =================================================================================================
// Screens
// =================================================================================================

fun EntryProviderScope<AppRoute>.screens(
    navigator: Navigator,
    onBack: () -> Unit
) {
    entry<HomeScreen> {
        HomeScreenRoot(
            navigator = rememberHomeNavigator(
                onBankAccountDetailCallback = { /* no-op */ }
            )
        )
    }

    entry<AccountScreen> {
        AccountScreenRoot(
            navigator = rememberAccountNavigator(
                onSetting = { /* no-op */ }
            )
        )
    }
}
