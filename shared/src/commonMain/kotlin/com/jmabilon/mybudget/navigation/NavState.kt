package com.jmabilon.mybudget.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.savedstate.compose.serialization.serializers.SnapshotStateListSerializer

/**
 * A composable function that remembers the navigation state of the application.
 *
 * @param startRoute The initial route to start the navigation from.
 * @param primaryTopLevelRoute The primary top-level route of the application.
 * @param topLevelRoutes A set of all top-level routes in the application.
 * @return An instance of [NavState] that holds the current navigation state.
 */
@Composable
fun rememberNavState(
    startRoute: AppRoute,
    primaryTopLevelRoute: TopLevelRoute,
    topLevelRoutes: Set<TopLevelRoute>
): NavState {

    /**
     * A map of top-level routes to their corresponding backstacks.
     *
     * Each backstack is a mutable state list of [AppRoute] objects, which allows for dynamic navigation within the app.
     * The backstacks are initialized with the top-level route itself as the starting point.
     */
    val topLevelBackstacks: Map<TopLevelRoute, SnapshotStateList<AppRoute>> = buildMap {
        topLevelRoutes.forEach { route ->
            put(route, rememberSerializable(serializer = SnapshotStateListSerializer()) {
                mutableStateListOf(route)
            })
        }
    }

    /**
     * The default backstack is used when the starting route is not a top-level route.
     * It is initialized with the starting route, allowing for navigation to non-top-level screens.
     */
    val defaultBackstack = rememberSerializable(serializer = SnapshotStateListSerializer()) {
        if (startRoute !is TopLevelRoute) {
            mutableStateListOf(startRoute)
        } else {
            mutableStateListOf()
        }
    }

    /**
     * The current backstack is determined based on the starting route.
     *
     * If the starting route is a top-level route, the corresponding backstack from [topLevelBackstacks] is used.
     * Otherwise, the [defaultBackstack] is used. This allows for proper navigation state restoration.
     */
    val currentBackstack = rememberSerializable(serializer = SnapshotStateListSerializer()) {
        val restoredBackstack = if (startRoute is TopLevelRoute) {
            topLevelBackstacks[startRoute].orEmpty()
        } else {
            defaultBackstack
        }
        restoredBackstack.toMutableStateList()
    }

    return remember(startRoute, topLevelRoutes) {
        NavState(
            primaryTopLevelRoute = primaryTopLevelRoute,
            topLevelBackStacks = topLevelBackstacks,
            defaultBackstack = defaultBackstack,
            currentBackstack = currentBackstack,
        )
    }
}

class NavState(
    val topLevelBackStacks: Map<TopLevelRoute, SnapshotStateList<AppRoute>>,
    val defaultBackstack: SnapshotStateList<AppRoute>,
    val primaryTopLevelRoute: TopLevelRoute,
    val currentBackstack: SnapshotStateList<AppRoute>
) {

    /**
     * Gets or sets the current top-level route.
     *
     * When setting a new top-level route, the current backstack is saved to the old route's storage, and the new route's backstack is loaded into the current backstack.
     * This allows for seamless navigation between top-level routes while preserving the navigation history of each route.
     */
    var topLevelRoute: TopLevelRoute?
        get() = currentBackstack.firstOrNull() as? TopLevelRoute
        set(value) {
            val oldRoute = topLevelRoute

            // Save current backstack to the old route's storage
            val oldStorage = if (oldRoute != null) topLevelBackStacks[oldRoute] else defaultBackstack
            oldStorage?.clear()
            oldStorage?.addAll(currentBackstack)

            // Load new route's backstack into currentBackstack
            val newStorage = if (value != null) topLevelBackStacks[value] else defaultBackstack
            currentBackstack.clear()
            newStorage?.let(currentBackstack::addAll)
        }

    @Composable
    fun toDecoratedEntries(
        entryProvider: (AppRoute) -> NavEntry<AppRoute>
    ): SnapshotStateList<NavEntry<AppRoute>> {
        val decorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator<AppRoute>(),
            rememberViewModelStoreNavEntryDecorator()
        )

        val topLevelEntries = topLevelBackStacks
            .mapValues { (route, stack) ->
                rememberDecoratedNavEntries(
                    backStack = if (route == topLevelRoute) currentBackstack else stack,
                    entryDecorators = decorators,
                    entryProvider = entryProvider
                )
            }
            .withDefault { emptyList() }

        val defaultEntries = rememberDecoratedNavEntries(
            backStack = if (topLevelRoute == null) currentBackstack else defaultBackstack,
            entryDecorators = decorators,
            entryProvider = entryProvider,
        )

        /**
         * Returns the decorated navigation entries based on the current top-level route.
         *
         * If the current top-level route is null, the default entries are returned.
         * If the current top-level route is the primary top-level route, the corresponding entries are returned.
         * Otherwise, the entries for both the primary top-level route and the current top-level route are combined and returned.
         */
        return when (val topRoute = topLevelRoute) {
            null -> defaultEntries
            primaryTopLevelRoute -> topLevelEntries.getValue(primaryTopLevelRoute)
            else -> topLevelEntries.getValue(primaryTopLevelRoute) + topLevelEntries.getValue(topRoute)
        }.toMutableStateList()
    }
}
