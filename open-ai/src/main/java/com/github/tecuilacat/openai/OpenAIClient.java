package com.github.tecuilacat.openai;

import com.github.tecuilacat.openai.responses.OpenAIErrorResponse;
import com.github.tecuilacat.openai.responses.OpenAIResponse;
import okhttp3.*;

import java.io.IOException;
import java.util.Optional;

public class OpenAIClient {

    private final String apiKey;

    private final OpenAIModel model;

    private double temperature = 0.7;

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

    public OpenAIResponse chat(String prompt) {
        OpenAIResponse result;
        try (Response response = callEndpoint(prompt)) {
            Optional<OpenAIErrorCode> optErrorCode = OpenAIErrorCode.getByCode(response.code());
            if (optErrorCode.isEmpty()) {
                result = parseToOpenAIRepsonse(response);
            } else {
                OpenAIErrorCode errorCode = optErrorCode.get();
                result = new OpenAIErrorResponse(errorCode.getErrorCode(), errorCode.getMessage(), new OpenAIException(errorCode.getMessage()));
            }
        } catch (IOException ioe) {
            result = new OpenAIErrorResponse(404, "Open AI could not be called", ioe);
        } catch (OpenAIException oaie) {
            result = new OpenAIErrorResponse(500, oaie.getMessage(), oaie);
        }
        return result;
    }

    private OpenAIResponse parseToOpenAIRepsonse(Response response) throws OpenAIException {
        return null;
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