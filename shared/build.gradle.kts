import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    id("dev.sergiobelda.compose.vectorize") version "1.0.2"
    id("com.squareup.sqldelight")
    //alias(libs.plugins.app.icon)
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.sqldelight.runtime)
                implementation(libs.sqldelight.coroutines)
                implementation(libs.kotlinx.datetime)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(libs.material.icons.core)

                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(libs.compose.vectorize.core)
                implementation(libs.kotlinx.serialization.json)

                implementation(libs.multiplatform.settings)
                implementation(libs.multiplatform.settings.coroutines)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.sqldelight.android.driver)
                // Wav Recorder
                implementation(libs.android.wave.recorder)
                // SpeechRecognizer
                implementation(libs.alphacephei.vosk.android)
                implementation(libs.kotlinx.serialization.json.v160)
                implementation(libs.multiplatform.settings)

                implementation(project(":lib"))

            }
        }
        val iosX64Main by getting {
            dependencies {
                compileOnly(libs.jetbrains.atomicfu)
                api(libs.jetbrains.atomicfu)
            }
        }
        val iosArm64Main by getting {
            dependencies {
                compileOnly(libs.jetbrains.atomicfu)
                api(libs.jetbrains.atomicfu)
            }
        }
        val iosSimulatorArm64Main by getting {
            dependencies {
                compileOnly(libs.jetbrains.atomicfu)
                api(libs.jetbrains.atomicfu)
            }
        }
        val iosMain by getting {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation(libs.sqldelight.native.driver)
            }
        }
    }

    targets.all {
        compilations.all {
            kotlinOptions.freeCompilerArgs += "-Xexpect-actual-classes"
        }
    }

    val whisperFrameworkPath = file("${projectDir}/../iosApp/whisper.xcframework")
    println("========================================== ${whisperFrameworkPath}")
    iosSimulatorArm64 {
        compilations.getByName("main") {
            val whisper by cinterops.creating {
                defFile(project.file("src/nativeInterop/cinterop/whisper.def"))
                compilerOpts(
                    "-I${whisperFrameworkPath}/ios-arm64_x86_64-simulator/whisper.framework/Headers",
                    "-F${whisperFrameworkPath}"
                )
            }
        }
    }
    iosArm64 {
        compilations.getByName("main") {
            val whisper by cinterops.creating {
                defFile(project.file("src/nativeInterop/cinterop/whisper.def"))
                compilerOpts(
                    "-I${whisperFrameworkPath}/ios-arm64/whisper.framework/Headers",
                    "-F$whisperFrameworkPath"
                )
            }
        }
    }

    iosX64 {
        compilations.getByName("main") {
            val whisper by cinterops.creating {
                defFile(project.file("src/nativeInterop/cinterop/whisper.def"))
                compilerOpts(
                    "-I${whisperFrameworkPath}/ios-arm64_x86_64-simulator/whisper.framework/Headers",
                    "-F$whisperFrameworkPath"
                )
            }
        }
    }

}
sqldelight {
    database("NoteDatabase") {
        packageName = "com.module.notelycompose.database"
        sourceFolders = listOf("sqldelight")
    }
}
android {
    namespace = "com.module.notelycompose"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.animation.android)
}
