plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.4.0").apply(false)
    id("com.android.library").version("8.4.0").apply(false)
    kotlin("android").version("2.0.0").apply(false)
    kotlin("multiplatform").version("2.0.0").apply(false)
    id("com.google.devtools.ksp").version("2.0.0-1.0.21").apply(false)
    id("com.google.dagger.hilt.android").version("2.51.1").apply(false)
    id("org.jetbrains.kotlin.plugin.compose").version("2.0.0").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
