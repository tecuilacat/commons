# Tecuilacat commons

![Gradle](https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Github](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)
![MS-WORD](https://img.shields.io/badge/Microsoft_Word-2B579A?style=for-the-badge&logo=microsoft-word&logoColor=white)
![APACHE](https://img.shields.io/badge/Apache-D22128?style=for-the-badge&logo=Apache&logoColor=white)
![Open-AI](https://img.shields.io/badge/ChatGPT-74aa9c?style=for-the-badge&logo=openai&logoColor=white)

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
| Name             | Documentation                                                                                         |
|------------------|-------------------------------------------------------------------------------------------------------|
| Document-Creator | Documentation can be found [here](https://github.com/tecuilacat/commons/blob/master/document-creator) |
| Open-AI          | Documentation can be found [here](https://github.com/tecuilacat/commons/blob/master/open-ai)          |

---
&copy; tecuilacat 2024