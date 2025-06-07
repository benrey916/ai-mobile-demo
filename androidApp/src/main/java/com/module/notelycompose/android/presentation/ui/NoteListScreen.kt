package com.module.notelycompose.android.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.module.notelycompose.android.presentation.AndroidNoteListViewModel
import com.module.notelycompose.notes.presentation.list.NoteListIntent.OnNoteDeleted
import com.module.notelycompose.notes.presentation.list.NoteListIntent.OnFilterNote
import com.module.notelycompose.notes.presentation.list.NoteListIntent.OnSearchNote
import com.module.notelycompose.notes.ui.list.SharedNoteListScreen

@Composable
fun NoteListScreen(
    androidNoteListViewModel: AndroidNoteListViewModel,
    onFloatingActionButtonClicked: () -> Unit,
    onNoteClicked: (Long) -> Unit,
    onInfoClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
    isTablet: Boolean
) {
    val state by androidNoteListViewModel.state.collectAsState()
    val notes = androidNoteListViewModel.onGetUiState(state)

    SharedNoteListScreen(
        notes = notes,
        onFloatingActionButtonClicked = {
            onFloatingActionButtonClicked()
        },
        onNoteClicked = {
            onNoteClicked(it)
        },
        onNoteDeleteClicked = {
            androidNoteListViewModel.onProcessIntent(OnNoteDeleted(it))
        },
        onFilterTabItemClicked = { filter ->
            androidNoteListViewModel.onProcessIntent(OnFilterNote(filter))
        },
        onSearchByKeyword = { keyword ->
            androidNoteListViewModel.onProcessIntent(OnSearchNote(keyword))
        },
        selectedTabTitle = state.selectedTabTitle,
        showEmptyContent = state.showEmptyContent,
        onInfoClicked = onInfoClicked,
        onSettingsClicked = onSettingsClicked,
        isTablet = isTablet
    )
}
