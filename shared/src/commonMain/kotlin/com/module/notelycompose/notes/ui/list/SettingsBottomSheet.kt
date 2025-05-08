package com.module.notelycompose.notes.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.module.notelycompose.notes.ui.theme.LocalCustomColors

@Composable
fun SettingsBottomSheet(
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Settings",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Row {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }
            }
        }

        Divider()

        // Theme settings section with title and toggle switches
        ThemeSettingsSection()
    }
}

@Composable
fun ThemeSettingsSection() {
    var selectedTheme by remember { mutableStateOf(ThemeOption.SYSTEM) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {

        ThemeToggleOption(
            title = "System Default",
            isSelected = selectedTheme == ThemeOption.SYSTEM,
            onClick = { selectedTheme = ThemeOption.SYSTEM }
        )

        Divider()

        ThemeToggleOption(
            title = "Light Theme",
            isSelected = selectedTheme == ThemeOption.LIGHT,
            onClick = { selectedTheme = ThemeOption.LIGHT }
        )

        Divider()

        ThemeToggleOption(
            title = "Dark Theme",
            isSelected = selectedTheme == ThemeOption.DARK,
            onClick = { selectedTheme = ThemeOption.DARK }
        )
    }
}

@Composable
fun ThemeToggleOption(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp
        )
        Switch(
            checked = isSelected,
            onCheckedChange = { if (!isSelected) onClick() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = LocalCustomColors.current.bodyContentColor
            )
        )
    }
}

enum class ThemeOption {
    SYSTEM, LIGHT, DARK
}
