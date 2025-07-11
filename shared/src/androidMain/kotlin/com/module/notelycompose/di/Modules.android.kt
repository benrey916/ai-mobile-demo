package com.module.notelycompose.di

import android.app.Application
import com.module.notelycompose.FileSaverHandler
import com.module.notelycompose.FileSaverLauncherHolder
import com.module.notelycompose.PermissionHandler
import com.module.notelycompose.PermissionLauncherHolder
import com.module.notelycompose.audio.domain.AudioRecorderInteractor
import com.module.notelycompose.audio.domain.AudioRecorderInteractorImpl
import com.module.notelycompose.audio.domain.SaveAudioNoteInteractor
import com.module.notelycompose.audio.domain.SaveAudioNoteInteractorImpl
import com.module.notelycompose.database.NoteDatabase
import com.module.notelycompose.platform.AndroidPlatform
import com.module.notelycompose.platform.BrowserLauncher
import com.module.notelycompose.platform.Downloader
import com.module.notelycompose.platform.Platform
import com.module.notelycompose.platform.PlatformAudioPlayer
import com.module.notelycompose.platform.PlatformUtils
import com.module.notelycompose.platform.Transcriber
import com.module.notelycompose.platform.dataStore
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule = module {
    single<String>(qualifier = named("AppVersion")) {
        val app: Application = get()
        try {
            val packageInfo = app.packageManager.getPackageInfo(app.packageName, 0)
            packageInfo.versionName ?: "Unknown"
        } catch (e: Exception) {
            "Unknown"
        }
    }
    single { PermissionLauncherHolder() }
    factory { PermissionHandler(get()).requestPermission() }
    single { FileSaverLauncherHolder() }
    single { FileSaverHandler(get()) }
    single<Platform> { AndroidPlatform(get(named("AppVersion")), get()) }
    single { dataStore(get()) }
    single { PlatformUtils(get(), get()) }
    single { BrowserLauncher(get()) }

    single<SqlDriver> {
        AndroidSqliteDriver(NoteDatabase.Schema, context = get(), "notes.db")
    }

    single { PlatformAudioPlayer() }

    single { Downloader(get(), get()) }

    single { Transcriber(get(), get()) }

    // domain
    single<AudioRecorderInteractor> { AudioRecorderInteractorImpl(get(), get(), get()) }
    single<SaveAudioNoteInteractor> {
        SaveAudioNoteInteractorImpl(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}