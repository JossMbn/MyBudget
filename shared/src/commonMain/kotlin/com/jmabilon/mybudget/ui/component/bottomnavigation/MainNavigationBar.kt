package com.jmabilon.mybudget.ui.component.bottomnavigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.jmabilon.mybudget.ui.model.MainNavDestination
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun <T : Any> MainNavigationBar(
    modifier: Modifier = Modifier,
    currentDestination: MainNavDestination<T>?,
    destinations: ImmutableList<MainNavDestination<T>>,
    onSelect: (MainNavDestination<T>) -> Unit
) {
    NavigationBar(
        modifier = modifier
    ) {
        destinations.forEach { destination ->
            NavigationBarItem(
                selected = destination == currentDestination,
                onClick = { onSelect(destination) },
                icon = {
                    MainNavigationButton(
                        iconResource = destination.icon,
                        iconFilledResource = destination.iconSelected,
                        contentDescription = stringResource(destination.label),
                        selected = destination == currentDestination,
                        onClick = { onSelect(destination) }
                    )
                },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
private fun MainNavigationButton(
    modifier: Modifier = Modifier,
    iconResource: DrawableResource,
    iconFilledResource: DrawableResource,
    contentDescription: String?,
    selected: Boolean,
    onClick: () -> Unit
) {
    val iconColor by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.secondary
    )

    Icon(
        modifier = modifier
            .size(28.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Tab,
                onClick = onClick
            ),
        painter = painterResource(if (selected) iconFilledResource else iconResource),
        contentDescription = contentDescription,
        tint = iconColor
    )
}
