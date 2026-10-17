package com.jmabilon.mybudget.feature.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

@Composable
fun rememberHomeNavigator(
    onBankAccountDetailCallback: (bankAccountId: String) -> Unit
): HomeNavigator = remember {
    DefaultHomeNavigator(
        onBankAccountDetailCallback = onBankAccountDetailCallback
    )
}

// =================================================================================================
// Navigator
// =================================================================================================

@Stable
fun interface HomeNavigator {
    fun onBankAccountDetail(bankAccountId: String)
}

class DefaultHomeNavigator(
    private val onBankAccountDetailCallback: (bankAccountId: String) -> Unit
) : HomeNavigator {

    override fun onBankAccountDetail(bankAccountId: String) =
        onBankAccountDetailCallback(bankAccountId)
}
