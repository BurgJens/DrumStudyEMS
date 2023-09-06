import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.compose") version "1.5.0"
}

group = "com.burgdorf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)

    val precompose_version = "1.5.0"

    // Please do remember to add compose.foundation and compose.animation
    api(compose.foundation)
    api(compose.animation)
//...
    api("moe.tlaster:precompose:$precompose_version")

// api("moe.tlaster:precompose-molecule:$precompose_version") // For Molecule intergration

    api("moe.tlaster:precompose-viewmodel:$precompose_version") // For ViewModel intergration

// api("moe.tlaster:precompose-koin:$precompose_version") // For Koin intergration
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "DrumStudyEMS_Desktop"
            packageVersion = "1.0.0"
        }
    }
}
