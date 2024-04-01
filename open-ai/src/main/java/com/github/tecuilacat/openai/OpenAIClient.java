package com.github.tecuilacat.openai;

import com.github.tecuilacat.openai.responses.OpenAIErrorResponse;
import com.github.tecuilacat.openai.responses.OpenAIResponse;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Optional;

/**
 * This client can make rest calls to the chatGPT endpoints and is the main class the user interacts with
 * @author tecuilacat
 * @since 1.0.2
 */
public class OpenAIClient {

    /**
     * API-Key must be set and can be generated <a href="https://platform.openai.com/account/api-keys">here</a> <br>
     * Note that this is a pay to win version, so the regular chatGPT account will not work
     */
    private final String apiKey;

    private final OpenAIModel model;

    private double temperature = 0.7;

    /**
     * You can define a system message that pushes the chat in a certain direction
     */
    private String systemMessage = "";

    public OpenAIClient(String apiKey, OpenAIModel model) {
        this.apiKey = apiKey;
        this.model = model;
    }

    public OpenAIClient(String apiKey, OpenAIModel model, double temperature) {
        this.apiKey = apiKey;
        this.model = model;
        this.temperature = temperature;
    }

    public OpenAIClient(String apiKey, OpenAIModel model, String systemMessage) {
        this.apiKey = apiKey;
        this.model = model;
        this.systemMessage = systemMessage;
    }

    public OpenAIClient(String apiKey, OpenAIModel model, String systemMessage, double temperature) {
        this.apiKey = apiKey;
        this.model = model;
        this.systemMessage = systemMessage;
        this.temperature = temperature;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * Sends a chat message to the ChatGPT-Server and returns the answer from the AI. This is basically the chat from the Open-AI-Playground
     * @param prompt The message, question or whatever, you want to send to the ChatGPT-Server
     * @return Response of the ChatGPT-Server
     */
    public OpenAIResponse chat(String prompt) {
        OpenAIResponse result;
        try (Response response = callEndpoint(prompt)) {
            result = parseToOpenAIRepsonse(response);
        } catch (IOException ioe) {
            result = new OpenAIErrorResponse(404, "Open AI could not be called", ioe);
        }
        return result;
    }

    private OpenAIResponse parseToOpenAIRepsonse(Response response) throws IOException {
        OpenAIResponse result;
        Optional<OpenAIErrorCode> optErrorCode = OpenAIErrorCode.getByCode(response.code());
        if (response.body() != null) {
            ResponseBody responseBody = response.body();
            if (optErrorCode.isPresent()) {
                OpenAIErrorCode errorCode = optErrorCode.get();
                TemporaryError error = new Gson().fromJson(responseBody.string(), TemporaryErrorContainer.class).getError();
                result = new OpenAIErrorResponse(errorCode.getErrorCode(), error.getMessage(), new OpenAIException("Encountered error: {\"errorCode\":" + errorCode.getErrorCode() + ":\"message\":\"" + errorCode.getMessage() + "\"}"));
            } else {
                // TODO ? | OS | 01.04.2024 | Implement
                result = null;
            }
        } else {
            result = new OpenAIErrorResponse(500, "Something went wrong", new OpenAIException("responseBody was null"));
        }
        return result;
    }

    private Response callEndpoint(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(OpenAIConstants.OPEN_AI_ENDPOINT)
                .post(RequestBody.create(mediaType, getRequestBody(prompt)))
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();
        return client.newCall(request).execute();
    }

    private String getRequestBody(String prompt) {
        return "{\n" +
                "    \"model\": \"" + model.getName() + "\",\n" +
                "    \"temperature\": " + temperature + "," +
                "    \"messages\": [\n" +
                "      {\n" +
                "        \"role\": \"system\",\n" +
                "        \"content\": \"" + systemMessage + "\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"role\": \"user\",\n" +
                "        \"content\": \"" + prompt + "!\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }";
    }

}