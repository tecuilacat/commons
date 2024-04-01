package com.github.tecuilacat.openai;

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
