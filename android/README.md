# Android-Utils
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Android-Studio](https://img.shields.io/badge/Android_Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)
![Gradle](https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)

[![Latest Release](https://jitpack.io/v/tecuilacat/commons.svg)](https://jitpack.io/#tecuilacat/commons)
![MIN_SDK Badge](https://img.shields.io/badge/MIN_SDK-Java_17-red)

This module contains any utilities surrounding the android world


## To make it work
Here is how to import the API and make it work

`build.gradle (Module: app)`

### Kotlin DSL
```kotlin
//settings.gradle --> dependencyResolutionManagement
maven(url = "https://jitpack.io") // need repository for commons dependency

// build.gradle --> dependency block
dependencies {
    implementation("com.github.tecuilacat.commons:android:[latest-release]")
}

//build.gradle --> android block
packaging {
    resources {
        // whatever is written here
        excludes += "AndroidManifest.xml"
        excludes += "res/animator/*"
        excludes += "res/drawable/*"
        excludes += "res/layout/*"
        excludes += "resources.arsc"
    }
}
```

### Groovy
```groovy
repositories {
    mavenCentral() // standard repositories
    maven { url 'https://jitpack.io' } // need repository for commons dependency
}

dependencies {
    implementation 'com.github.tecuilacat.commons:android:[latest-release]'
}

packagingOptions {
    exclude 'AndroidManifest.xml'
    exclude 'res/animator/*'
    exclude 'res/drawable/*'
    exclude 'res/layout/*'
    exclude 'resources.arsc'
}

//also add this to the dependencies if there is an error. Should work without
implementation 'com.fasterxml.jackson.core:jackson-databind:2.17.0'
```

## Documentations
| Name               | Link                                                                                                                                                                                                            |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| LocalStorageHolder | Java-Documentation can be found [here](https://github.com/tecuilacat/commons/tree/master/android/src/main/java/com/github/tecuilacat/android/storage/README.md). For Kotlin simply translate the code to Kotlin |

---
&copy; tecuilacat 2024