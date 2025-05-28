package com.module.notelycompose.android.di

import android.content.Context
import android.content.SharedPreferences
import androidx.activity.result.ActivityResultLauncher
import com.module.notelycompose.audio.presentation.mappers.AudioRecorderPresentationToUiMapper
import com.module.notelycompose.audio.ui.expect.AudioRecorder
import com.module.notelycompose.audio.ui.expect.Downloader
import com.module.notelycompose.audio.ui.expect.SpeechRecognizer
import com.module.notelycompose.audio.ui.expect.Transcriper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AudioRecorderSpeechModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "notely_shared_prefs",
            Context.MODE_PRIVATE
        )
    }

    @Singleton
    class PermissionLauncherHolder @Inject constructor() {
        var permissionLauncher: ActivityResultLauncher<String>? = null
    }

    @Provides
    @Singleton
    fun providePermissionLauncherHolder(): PermissionLauncherHolder {
        return PermissionLauncherHolder()
    }

    @Singleton
    class AudioRecorderFactory @Inject constructor(
        @ApplicationContext private val context: Context,
        private val permissionLauncherHolder: PermissionLauncherHolder,

    ) {
        fun create(): AudioRecorder {
            return AudioRecorder(
                context = context,
                permissionLauncher = permissionLauncherHolder.permissionLauncher
            )
        }
    }

    @Provides
    @Singleton
    fun provideAudioRecorderFactory(
        @ApplicationContext context: Context,
        permissionLauncherHolder: PermissionLauncherHolder
    ): AudioRecorderFactory {
        return AudioRecorderFactory(context, permissionLauncherHolder)
    }

    @Provides
    fun provideAudioRecorder(factory: AudioRecorderFactory): AudioRecorder {
        return factory.create()
    }


    @Provides
    @Singleton
    fun provideAudioRecorderPresentationToUiStateMapper(): AudioRecorderPresentationToUiMapper {
        return AudioRecorderPresentationToUiMapper()
    }

    @Singleton
    class SpeechRecognizerFactory @Inject constructor(
        @ApplicationContext private val context: Context,
        private val permissionLauncherHolder: PermissionLauncherHolder
    ) {
        fun create(): SpeechRecognizer {
            return SpeechRecognizer(
                context = context,
                permissionLauncher = permissionLauncherHolder.permissionLauncher
            )
        }
    }


    @Provides
    @Singleton
    fun provideSpeechRecognizerFactory(
        @ApplicationContext context: Context,
        permissionLauncherHolder: PermissionLauncherHolder
    ): SpeechRecognizerFactory {
        return SpeechRecognizerFactory(context, permissionLauncherHolder)
    }

    @Provides
    fun provideSpeechRecognizer(factory: SpeechRecognizerFactory): SpeechRecognizer {
        return factory.create()
    }


    @Singleton
    class TranscriperFactory @Inject constructor(
        @ApplicationContext private val context: Context,
        private val permissionLauncherHolder: PermissionLauncherHolder
    ) {
        fun create(): Transcriper {
            return Transcriper(
                context = context,
                permissionLauncher = permissionLauncherHolder.permissionLauncher
            )
        }
    }


    @Provides
    @Singleton
    fun provideTranscriperFactory(
        @ApplicationContext context: Context,
        permissionLauncherHolder: PermissionLauncherHolder
    ): TranscriperFactory {
        return TranscriperFactory(context, permissionLauncherHolder)
    }

    @Provides
    fun provideTranscriper(factory: TranscriperFactory): Transcriper {
        return factory.create()
    }

    @Singleton
    class DownloaderFactory @Inject constructor(
        @ApplicationContext private val context: Context,
        private val prefs:SharedPreferences
    ) {
        fun create(): Downloader {
            return Downloader(
                mainContext = context,
                prefs = prefs
            )
        }
    }


    @Provides
    @Singleton
    fun downloaderFactory(
        @ApplicationContext context: Context,
        prefs: SharedPreferences
    ): DownloaderFactory {
        return DownloaderFactory(context, prefs)
    }

    @Provides
    fun provideDownloader(factory: DownloaderFactory): Downloader {
        return factory.create()
    }
}
