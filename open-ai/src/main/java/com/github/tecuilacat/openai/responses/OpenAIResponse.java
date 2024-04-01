package com.github.tecuilacat.openai.responses;

public abstract sealed class OpenAIResponse permits OpenAISuccessfulResponse, OpenAIErrorResponse {
}
