package com.github.tecuilacat.openai;

/**
 * In this enum all the available chatGPT models are listed
 * @author tecuilacat
 * @since 1.0.2
 */
public enum OpenAIModel {

    GPT_3_5_TURBO("gpt-3.5-turbo")
    ;

    private final String name;

    OpenAIModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
