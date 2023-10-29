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

    //viewModel for Desktop
    val precompose_version = "1.5.0"
    api(compose.foundation)
    api(compose.animation)
    api("moe.tlaster:precompose:$precompose_version")
    api("moe.tlaster:precompose-viewmodel:$precompose_version")

    //serial communication
    implementation("com.fazecast:jSerialComm:2.7.0")
    //json write/read
    implementation("com.google.code.gson:gson:2.8.9")
    //mp3 player
    implementation("javazoom:jlayer:1.0.1")

    //excel
    implementation("org.apache.poi:poi:5.2.0")
    implementation("org.apache.poi:poi-ooxml:5.2.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")
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
