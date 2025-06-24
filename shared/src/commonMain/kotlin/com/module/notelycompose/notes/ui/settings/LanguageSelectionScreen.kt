package com.module.notelycompose.notes.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.module.notelycompose.notes.ui.detail.AndroidNoteTopBar
import com.module.notelycompose.notes.ui.detail.IOSNoteTopBar
import com.module.notelycompose.notes.ui.theme.LocalCustomColors
import com.module.notelycompose.onboarding.data.PreferencesRepository
import com.module.notelycompose.platform.getPlatform
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

val languageCodeMap = mapOf(
    "auto" to "Auto detect",
    "ar" to "Arabic",
    "ca" to "Catalan",
    "zh" to "Chinese",
    "nl" to "Dutch",
    "en" to "English",
    "fi" to "Finnish",
    "fr" to "French",
    "gl" to "Galician",
    "de" to "German",
    "id" to "Indonesian",
    "it" to "Italian",
    "ja" to "Japanese",
    "ko" to "Korean",
    "ms" to "Malay",
    "no" to "Norwegian",
    "pl" to "Polish",
    "pt" to "Portuguese",
    "ru" to "Russian",
    "es" to "Spanish",
    "sv" to "Swedish",
    "tl" to "Tagalog",
    "th" to "Thai",
    "tr" to "Turkish",
    "uk" to "Ukrainian",
    "vi" to "Vietnamese",
)


@Composable
fun LanguageSelectionScreen(
    navigateBack: () -> Unit,
    preferencesRepository: PreferencesRepository = koinInject()
) {

    val coroutineScope = rememberCoroutineScope()
    var searchText by remember { mutableStateOf("") }
    val filteredLanguages by derivedStateOf {
        languageCodeMap.filter { (language, code) ->
            language.contains(searchText, ignoreCase = true) ||
                    code.contains(searchText, ignoreCase = true)
        }
    }

    if (getPlatform().isAndroid) {
        AndroidNoteTopBar(
            title = "",
            onNavigateBack = navigateBack
        )
    } else {
        IOSNoteTopBar(
            onNavigateBack = navigateBack
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalCustomColors.current.bodyBackgroundColor)
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "Select Language",
            color = LocalCustomColors.current.bodyContentColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        // Search Bar
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            placeholder = {
                Text(
                    text = "Search",
                    color = LocalCustomColors.current.languageSearchBorderColor
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = LocalCustomColors.current.languageSearchBorderColor
                )
            },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(
                        onClick = { searchText = "" },
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                LocalCustomColors.current.languageSearchCancelButtonColor.copy(alpha = 0.3f),
                                CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = LocalCustomColors.current.languageSearchCancelIconTintColor,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = LocalCustomColors.current.languageSearchUnfocusedColor,
                unfocusedTextColor = LocalCustomColors.current.languageSearchUnfocusedColor,
                focusedBorderColor = LocalCustomColors.current.languageSearchBorderColor,
                unfocusedBorderColor = LocalCustomColors.current.languageSearchBorderColor,
                cursorColor = LocalCustomColors.current.languageSearchUnfocusedColor
            ),
            shape = RoundedCornerShape(48.dp),
            singleLine = true
        )

        // Language List
        Text(
            text = "SUPPORTED LANGUAGES",
            color = LocalCustomColors.current.languageListHeaderColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp)
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(12.dp))
        ) {
            if (filteredLanguages.isEmpty()) {
                Text(
                    text = "No languages found",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = LocalCustomColors.current.languageListTextColor
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    itemsIndexed(filteredLanguages.entries.toList()) { index, languageEntry ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    coroutineScope.launch {
                                        preferencesRepository.setDefaultTranscriptionLanguage(languageEntry.key)
                                    }
                                    navigateBack()
                                },
                            color = LocalCustomColors.current.languageListBackgroundColor,
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = languageEntry.value,
                                        color = LocalCustomColors.current.languageListTextColor,
                                        fontSize = 16.sp,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                if (index < filteredLanguages.size - 1) {
                                    Divider(
                                        thickness = 0.5.dp,
                                        color = LocalCustomColors.current.languageListDividerColor
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
