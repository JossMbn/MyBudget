package com.jmabilon.mybudget.feature.account.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

@Composable
fun rememberAccountNavigator(
    onSetting: () -> Unit
): AccountNavigator = remember {
    DefaultAccountNavigator(
        onSettingCallback = onSetting
    )
}

// =================================================================================================
// Navigator
// =================================================================================================

@Stable
fun interface AccountNavigator {
    fun onSetting()
}

class DefaultAccountNavigator(
    private val onSettingCallback: () -> Unit
) : AccountNavigator {

    override fun onSetting() = onSettingCallback()
}
