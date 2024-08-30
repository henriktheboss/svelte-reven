package no.svelterev.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
public class httpapi {

    /**
     * Sender en GET-request til en angitt URL og returnerer en liste av objekter
     * fra en URL som sender JSON-array. Typen T angir hva slags Java-klasser som skal i listen.
     *
     * Eksempler: <br />
     * Dersom du har et API som returnerer en liste av skoler som en JSON-array.<br />
     *
     * <pre>
     *     [
     *      {skoleNavn: "F21"},
     *      {skoleNavn: "Etterstad"},
     *      osv.
     *     ]
     * </pre>
     *
     * Da kan du bruke følgende kode: <br />
     *
     * <pre>
     *     record Skole(String skoleNavn) { }
     *     List&lt;Skole&gt; skoler = getList(Skole.class, "https://url.no");
     * </pre>
     *
     *
     * @param <T>   the type of objects in the list
     * @param clazz the class of the objects in the list
     * @param url   the URL from which to retrieve the list
     * @return a list of objects of the specified class
     * @throws RuntimeException if there is an error during the retrieval process
     */
    public static <T> List<T> getList(Class<T> clazz, String url) {
        try(HttpClient client = HttpClient.newHttpClient()) {

            // Create HttpRequest instance
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            // Send a GET request and get HttpResponse
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Get String response body
            String responseBody = response.body();

            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert JSON string to List of User objects
            return objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}