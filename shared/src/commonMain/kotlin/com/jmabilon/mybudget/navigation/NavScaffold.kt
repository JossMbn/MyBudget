package com.jmabilon.mybudget.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.jmabilon.mybudget.navigation.component.BottomNavigation
import com.jmabilon.mybudget.ui.model.MainNavDestination
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import mybudget.shared.generated.resources.Res
import mybudget.shared.generated.resources.ic_home_rounded_fill
import mybudget.shared.generated.resources.ic_home_rounded_outlined
import mybudget.shared.generated.resources.ic_person_rounded_fill
import mybudget.shared.generated.resources.ic_person_rounded_outlined
import mybudget.shared.generated.resources.nav_destination_account
import mybudget.shared.generated.resources.nav_destination_home

private val bottomNavDestinations: ImmutableList<MainNavDestination<TopLevelRoute>> = persistentListOf(
    MainNavDestination(
        label = Res.string.nav_destination_home,
        icon = Res.drawable.ic_home_rounded_outlined,
        iconSelected = Res.drawable.ic_home_rounded_fill,
        route = HomeScreen
    ),
    MainNavDestination(
        label = Res.string.nav_destination_account,
        icon = Res.drawable.ic_person_rounded_outlined,
        iconSelected = Res.drawable.ic_person_rounded_fill,
        route = AccountScreen
    )
)

@Composable
internal fun NavScaffold(
    navState: NavState,
    navigator: Navigator,
    content: @Composable (PaddingValues) -> Unit
) {
    val onSelectRoute: (TopLevelRoute) -> Unit = { route -> navigator.activate(route) }
    val destinations = remember { bottomNavDestinations }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigation(
                currentRoute = navState.topLevelRoute,
                destinations = destinations,
                onSelectRoute = onSelectRoute
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        content = content
    )
}
