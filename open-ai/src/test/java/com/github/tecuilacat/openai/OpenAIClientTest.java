package com.github.tecuilacat.openai;

import com.github.tecuilacat.openai.responses.OpenAIErrorResponse;
import com.github.tecuilacat.openai.responses.OpenAIResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenAIClientTest {

    @Test
    void response_is_error() {
        OpenAIClient openAIClient = new OpenAIClient("some random key", OpenAIModel.GPT_3_5_TURBO);
        OpenAIResponse response = openAIClient.chat("Some random prompt");
        assertInstanceOf(OpenAIErrorResponse.class, response);
        OpenAIErrorResponse errorResponse = (OpenAIErrorResponse) response;
        assertEquals(401, errorResponse.getErrorCode());
        assertEquals("Incorrect API key provided: some ran*** key. You can find your API key at https://platform.openai.com/account/api-keys.", errorResponse.getMessage());
        assertNotNull(errorResponse.getException());
        assertInstanceOf(OpenAIException.class, errorResponse.getException());
    }

    @Test
    void response_is_successful() {
        // TODO ? | OS | 01.04.2024 | Implement
    }

}