package com.jmabilon.mybudget.navigation.component

import androidx.compose.runtime.Composable
import com.jmabilon.mybudget.navigation.TopLevelRoute
import com.jmabilon.mybudget.ui.component.bottomnavigation.MainNavigationBar
import com.jmabilon.mybudget.ui.model.MainNavDestination
import kotlinx.collections.immutable.ImmutableList

@Composable
fun BottomNavigation(
    currentRoute: TopLevelRoute?,
    destinations: ImmutableList<MainNavDestination<TopLevelRoute>>,
    onSelectRoute: (TopLevelRoute) -> Unit
) {
    val currentDestination = destinations.find { it.route == currentRoute }

    MainNavigationBar(
        currentDestination = currentDestination,
        destinations = destinations,
        onSelect = { selectedDestination ->
            onSelectRoute(selectedDestination.route)
        }
    )
}
