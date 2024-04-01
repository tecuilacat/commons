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
        assertEquals("Invalid Authentication", errorResponse.getMessage());
        assertNotNull(errorResponse.getException());
        assertInstanceOf(OpenAIException.class, errorResponse.getException());
    }

}