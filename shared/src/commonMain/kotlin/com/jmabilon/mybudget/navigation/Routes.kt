package com.jmabilon.mybudget.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute

@Serializable
sealed interface TopLevelRoute : AppRoute

// =================================================================================================
// Screens
// =================================================================================================

@Serializable
@SerialName("Home")
data object HomeScreen : AppRoute, TopLevelRoute

@Serializable
@SerialName("Account")
data object AccountScreen : AppRoute, TopLevelRoute
