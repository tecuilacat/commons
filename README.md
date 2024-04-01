# Tecuilacat commons

![Latest Release](https://img.shields.io/github/v/release/tecuilacat/commons?color=blue&label=latest%20release)
![MIN_SDK Badge](https://img.shields.io/badge/MIN_SDK-Java_17-red)

## Importing all modules at once
All modules can be imported separately, but also as a combined package
```groovy
repositories {
    mavenCentral() // standard repositories
	maven { url 'https://jitpack.io' } // need repository for commons dependency
}

dependencies {
    implementation 'com.github.tecuilacat:commons:[latest-release]'
}
```

## Modules and where to find them
| Name             | Documentation                                                                                                   |
|------------------|-----------------------------------------------------------------------------------------------------------------|
| Document-Creator | Documentation can be found [here](https://github.com/tecuilacat/commons/blob/master/document-creator/README.md) |
| Open-AI          | Documentation can be found [here](https://github.com/tecuilacat/commons/blob/master/open-ai/README.md)          |