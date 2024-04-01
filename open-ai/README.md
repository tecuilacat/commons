# Open-AI
![Open-AI](https://img.shields.io/badge/ChatGPT-74aa9c?style=for-the-badge&logo=openai&logoColor=white)

![Latest Release](https://img.shields.io/github/v/release/tecuilacat/commons?color=blue&label=latest%20release)
![MIN_SDK Badge](https://img.shields.io/badge/MIN_SDK-Java_17-red)

## Import Dependency
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

Example using Spring Boot:
```java
@Service
public class OpenAiTester {
    
    @Value("${open-ai.api-key}")
    private String apiKey;

    public OpenAIResponse callChatGpt(String prompt) {
        OpenAiClient chatGPT = new OpenAiClient(
                    apiKey,
                    OpenAIModel.GPT_3_5_TURBO,
                    "You are a software developer using Spring Boot with Gradle" //optional
                );
        return chatGPT.chat("How can I inject values in Spring Boot?");
    }
    
}

```