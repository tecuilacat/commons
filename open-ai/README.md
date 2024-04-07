# Open-AI-Interface
![Open-AI](https://img.shields.io/badge/ChatGPT-74aa9c?style=for-the-badge&logo=openai&logoColor=white)
![Gradle](https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)

[![Latest Release](https://jitpack.io/v/tecuilacat/commons.svg)](https://jitpack.io/#tecuilacat/commons)
![MIN_SDK Badge](https://img.shields.io/badge/MIN_SDK-Java_17-red)

This module can communicate with ChatGPT just like you would in the Open-AI-Playground.

## Import Dependency from JitPack
```groovy
repositories {
    mavenCentral() // standard repositories
	maven { url 'https://jitpack.io' } // need repository for commons dependency
}

dependencies {
    implementation 'com.github.tecuilacat.commons:open-ai:[latest-release]'
}
```

## How it works

1. Create an API-Token [here](https://platform.openai.com/account/api-keys)
2. Create a new `OpenAIClient` declaring your API-Key and the model you want to choose
3. Ask the client your question

Example using a Spring Boot service:
```java
@Service
public class OpenAiTester {
    
    @Value("${open-ai.api-key}")
    private String apiKey;

    public OpenAIResponse callChatGpt(String prompt) {
        OpenAiClient chatGPT = new OpenAiClient(
                    apiKey, // enter your personal API-Key
                    OpenAIModel.GPT_3_5_TURBO, // choose a model
                    "You are a software developer using Spring Boot with Gradle" // optional
                );
        return chatGPT.chat("How can I inject values in Spring Boot?");
    }
    
}

```


---
&copy; tecuilacat 2024