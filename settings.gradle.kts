pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jcenter.bintray.com")
        maven("https://jitpack.io")
        }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jcenter.bintray.com")
        maven("https://jitpack.io")


    }
}

rootProject.name = "WelspunWay"
include(":app")
 