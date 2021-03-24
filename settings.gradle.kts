rootProject.name = "MorinoInsect"
pluginManagement {
    /* 1.4.30のdokkaを使うために一時的に必要
       https://github.com/Kotlin/dokka/issues/1779 */
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.jetbrains.dokka") {
                useModule("org.jetbrains.dokka:dokka-gradle-plugin:${requested.version}")
            }
        }
    }
    repositories {
        gradlePluginPortal()
        jcenter()
    }
}