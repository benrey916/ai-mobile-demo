package com.module.notelycompose.android.di

import android.app.Application
import android.content.Context
import com.module.notelycompose.AndroidPlatform
import com.module.notelycompose.Platform
import com.module.notelycompose.core.DatabaseDriverFactory
import com.module.notelycompose.database.NoteDatabase
import com.module.notelycompose.notes.data.NoteSqlDelightDataSource
import com.module.notelycompose.notes.domain.DeleteNoteById
import com.module.notelycompose.notes.domain.GetAllNotesUseCase
import com.module.notelycompose.notes.domain.GetLastNote
import com.module.notelycompose.notes.domain.GetNoteById
import com.module.notelycompose.notes.domain.InsertNoteUseCase
import com.module.notelycompose.notes.domain.NoteDataSource
import com.module.notelycompose.notes.domain.SearchNotesUseCase
import com.module.notelycompose.notes.domain.UpdateNoteUseCase
import com.module.notelycompose.notes.domain.mapper.NoteDomainMapper
import com.module.notelycompose.notes.domain.mapper.TextFormatMapper
import com.module.notelycompose.notes.presentation.helpers.TextEditorHelper
import com.module.notelycompose.notes.presentation.list.mapper.NotesFilterMapper
import com.module.notelycompose.notes.presentation.mapper.EditorPresentationToUiStateMapper
import com.module.notelycompose.notes.presentation.mapper.NotePresentationMapper
import com.module.notelycompose.notes.presentation.mapper.TextAlignPresentationMapper
import com.module.notelycompose.notes.presentation.mapper.TextFormatPresentationMapper
import com.module.notelycompose.web.BrowserLauncher
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabaseDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideNotesDataSource(driver: SqlDriver): NoteDataSource {
        return NoteSqlDelightDataSource(
            database = NoteDatabase(driver)
        )
    }

    @Provides
    @Singleton
    fun provideAppVersion(app: Application): String {
        return try {
            val packageInfo = app.packageManager.getPackageInfo(app.packageName, 0)
            packageInfo.versionName ?: "Unknown"
        } catch (e: Exception) {
            "Unknown"
        }
    }

    @Provides
    @Singleton
    fun providePlatform(appVersion: String): Platform {
        return AndroidPlatform(appVersion)
    }

    @Provides
    @Singleton
    fun provideGetAllNotesUseCase(
        dataSource: NoteDataSource,
        noteDomainMapper: NoteDomainMapper
    ): GetAllNotesUseCase {
        return GetAllNotesUseCase(dataSource, noteDomainMapper)
    }

    @Provides
    @Singleton
    fun provideSearchNotesUseCase(
        dataSource: NoteDataSource,
        noteDomainMapper: NoteDomainMapper
    ): SearchNotesUseCase {
        return SearchNotesUseCase(dataSource, noteDomainMapper)
    }

    @Provides
    @Singleton
    fun provideDeleteNoteByIdUseCase(
        dataSource: NoteDataSource
    ): DeleteNoteById {
        return DeleteNoteById(dataSource)
    }

    @Provides
    @Singleton
    fun provideInsertNoteUseCase(
        dataSource: NoteDataSource,
        textFormatMapper: TextFormatMapper,
        noteDomainMapper: NoteDomainMapper
    ): InsertNoteUseCase {
        return InsertNoteUseCase(
            dataSource,
            textFormatMapper,
            noteDomainMapper
        )
    }

    @Provides
    @Singleton
    fun provideGetNoteByIdUseCase(
        dataSource: NoteDataSource,
        noteDomainMapper: NoteDomainMapper
    ): GetNoteById {
        return GetNoteById(dataSource, noteDomainMapper)
    }

    @Provides
    @Singleton
    fun provideGetLastNoteUseCase(
        dataSource: NoteDataSource,
        noteDomainMapper: NoteDomainMapper
    ): GetLastNote {
        return GetLastNote(dataSource, noteDomainMapper)
    }

    @Provides
    @Singleton
    fun provideUpdateNoteUseCase(
        dataSource: NoteDataSource,
        textFormatMapper: TextFormatMapper,
        noteDomainMapper: NoteDomainMapper
    ): UpdateNoteUseCase {
        return UpdateNoteUseCase(
            dataSource,
            textFormatMapper,
            noteDomainMapper
        )
    }

    @Provides
    @Singleton
    fun provideTextFormatMapper(): TextFormatMapper {
        return TextFormatMapper()
    }

    @Provides
    @Singleton
    fun provideNoteMapper(): NoteDomainMapper {
        return NoteDomainMapper(textFormatMapper = TextFormatMapper())
    }

    @Provides
    @Singleton
    fun provideNotePresentationMapper(): NotePresentationMapper {
        return NotePresentationMapper()
    }

    @Provides
    @Singleton
    fun provideEditorPresentationToUiStateMapper(): EditorPresentationToUiStateMapper {
        return EditorPresentationToUiStateMapper()
    }

    @Provides
    @Singleton
    fun provideTextFormatPresentationMapper(): TextFormatPresentationMapper {
        return TextFormatPresentationMapper()
    }

    @Provides
    @Singleton
    fun provideTextAlignPresentationMapper(): TextAlignPresentationMapper {
        return TextAlignPresentationMapper()
    }

    @Provides
    @Singleton
    fun provideNotesFilterMapper(): NotesFilterMapper {
        return NotesFilterMapper()
    }

    @Provides
    @Singleton
    fun provideTextEditorHelper(): TextEditorHelper {
        return TextEditorHelper()
    }

    @Provides
    @Singleton
    fun provideBrowserLauncher(
        @ApplicationContext context: Context
    ): BrowserLauncher {
        return BrowserLauncher(context)
    }
}
