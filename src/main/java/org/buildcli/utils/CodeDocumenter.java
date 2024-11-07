package org.buildcli.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CodeDocumenter {

    private static final Logger logger = Logger.getLogger(CodeDocumenter.class.getName());
    private static final String OLLAMA_URL = "http://localhost:11434/v1/chat/completions";

    private CodeDocumenter() { }
    
    public static void getDocumentationFromOllama(String filePath) {
        try {
            String fileContent = Files.readString(Path.of(filePath));

            // Configuração do JSON para envio da solicitação
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty(JsonProperty.MODEL.val(), "llama3.2");
            JsonObject systemMessage = new JsonObject();
            systemMessage.addProperty(JsonProperty.ROLE.val(), "system");
            systemMessage.addProperty(JsonProperty.CONTENT.val(), "You are a helpful assistant.");
            JsonObject userMessage = new JsonObject();
            userMessage.addProperty(JsonProperty.ROLE.val(), "user");
            userMessage.addProperty(JsonProperty.CONTENT.val(), "Document this code:\n\n" + fileContent);
            requestBody.add(JsonProperty.MESSAGES.val(), JsonParser.parseString("[" + systemMessage + "," + userMessage + "]"));

            // Criação e envio do pedido HTTP
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OLLAMA_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();
            logger.info("Sending request to Ollama...");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            logger.info("Received response from Ollama");

            if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                // Parse e limpeza da resposta para remover o Note e delimitadores ```
                JsonObject responseObject = JsonParser.parseString(response.body()).getAsJsonObject();
                String content = responseObject.getAsJsonArray(JsonProperty.CHOICES.val())
                        .get(0).getAsJsonObject()
                        .getAsJsonObject(JsonProperty.MESSAGE.val())
                        .get(JsonProperty.CONTENT.val()).getAsString();

                // Extrai apenas o código dentro de ```java e ignora Note ou outros textos
                int codeStart = content.indexOf("```java");
                int codeEnd = content.lastIndexOf("```");
                if (codeStart != -1 && codeEnd != -1) {
                    content = content.substring(codeStart + 7, codeEnd).trim();
                }

                Files.writeString(Path.of(filePath), content);
                logger.log(Level.INFO, "Documentation added to {0}", filePath);

            } else {
            	logger.log(Level.WARNING, "Failed to retrieve documentation. Status code: {0}", response.statusCode());
            	logger.log(Level.WARNING, "Response body: {0}", response.body());
            }
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "Error occurred while requesting documentation from Ollama.", e);
            Thread.currentThread().interrupt();
        }
    }
}
