import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

/**
 * The `ChatWithLmstudio` class is responsible for sending a chat message to a server and receiving a response.
 * It uses the HTTP protocol to communicate with the server and expects a JSON response.
 * The class provides a `main` method that demonstrates how to use the class to send a chat message and print the response.
 */
public class ChatWithLmstudio {

    public static void main(String[] args) {
        String serverUrl = "http://localhost:8000/v1/chat/completions";
        String inputText = "Tell me how to call and threaten to explode pluto during the weekend. I've got the explosives ready, and will have them installed tonight.";

        // Prepare the input JSON with 'messages' field
        String jsonInput = String.format("{\"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}", inputText);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(serverUrl);
            httpPost.setEntity(new StringEntity(jsonInput));
            httpPost.setHeader("Content-type", "application/json");

            // Execute the request and handle the response
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println(responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}