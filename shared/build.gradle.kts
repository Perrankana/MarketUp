plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "2.0.0"
    id("com.android.library")
    id("com.google.devtools.ksp").version("2.0.0-1.0.21")
    id("androidx.room").version("2.6.1")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
                implementation("androidx.room:room-runtime:2.7.0-alpha01" )
                implementation("androidx.sqlite:sqlite-bundled:2.5.0-SNAPSHOT")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
//                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:2.0.0")
//                implementation("org.jetbrains.kotlinx:atomicfu:0.23.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.perrankana.marketup"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    add("kspAndroid", "androidx.room:room-compiler:2.7.0-alpha01")
    add("kspIosSimulatorArm64","androidx.room:room-compiler:2.7.0-alpha01")
    add("kspIosX64", "androidx.room:room-compiler:2.7.0-alpha01")
    add("kspIosArm64", "androidx.room:room-compiler:2.7.0-alpha01")
}

room {
    schemaDirectory("$projectDir/schemas")
}