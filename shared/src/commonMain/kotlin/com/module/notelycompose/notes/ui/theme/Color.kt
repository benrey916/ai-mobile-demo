package com.module.notelycompose.notes.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.compositionLocalOf

val DarkCustomColors = CustomColors(
    sortAscendingIconColor = Color(0xFF8514CB),
    backgroundViewColor = Color.Black, // // Color(0xFF181818),
    dateContentColorViewColor = Color.White,
    dateContentIconColor = Color(0xFFCCCCCC),
    bottomBarBackgroundColor = Color.White,
    bottomBarIconColor = Color(0xFF8514CB),
    noteListBackgroundColor = Color(0xFFEEEEEE),
    bodyBackgroundColor = Color.Black,  // Color(0xFF181818),
    onBodyColor = Color(0xFFF5F5F5),
    bodyContentColor = Color.White,
    contentTopColor = Color.White,
    floatActionButtonBorderColor = Color.White,
    floatActionButtonIconColor = Color.White,
    searchOutlinedTextFieldColor = Color(0xFFCCCCCC),
    topButtonIconColor = Color.White,
    noteTextColor = Color.Black,
    noteIconColor = Color.Black,
    iOSBackButtonColor = Color(0xFF3074F6),
    transparentColor = Color.Transparent,
    bottomFormattingContainerColor = Color(0xFFF2F2F2),
    bottomFormattingContentColor = Color.Black,
    activeThumbTrackColor = Color(0xFF666666),
    playerBoxBackgroundColor = Color(0xFFF2F2F2),
    starredColor = Color.Blue,
    settingsIconColor = Color(0xFFCCCCCC)
)

val LightCustomColors = CustomColors(
    sortAscendingIconColor = Color(0xFFA260CC),
    backgroundViewColor = Color(0xFFFFFFFF),
    dateContentColorViewColor = Color.Black,
    dateContentIconColor = Color(0xFF1E1E24),
    bottomBarBackgroundColor = Color(0xFFFFFFFF),
    bottomBarIconColor = Color.White,
    noteListBackgroundColor = Color(0xFFF4E7F9),
    bodyBackgroundColor = Color(0xFFFFFFFF),
    onBodyColor = Color(0xFF212121),
    contentTopColor = Color.Black,
    bodyContentColor = Color.Black,
    floatActionButtonBorderColor = Color.Black,
    floatActionButtonIconColor = Color.Black,
    searchOutlinedTextFieldColor = Color.Black,
    topButtonIconColor = Color.Black,
    noteTextColor = Color.White,
    noteIconColor = Color.White,
    iOSBackButtonColor = Color(0xFF3074F6),
    transparentColor = Color.Transparent,
    bottomFormattingContainerColor = Color(0xFFF2F2F2),
    bottomFormattingContentColor = Color.Black,
    activeThumbTrackColor = Color(0xFF666666),
    playerBoxBackgroundColor = Color(0xFFEEEEEE),
    starredColor = Color.Blue,
    settingsIconColor = Color.Black
)

// Create a CompositionLocal to hold the custom colors
val LocalCustomColors = compositionLocalOf {
    LightCustomColors // Default value for custom colors
}
