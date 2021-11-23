package motd;

import java.io.*;
import java.time.*;
import java.net.http.*;
import java.net.URI;

public class MoTD {
    private static final URI PUZZLEURI = URI.create("http://cswebcat.swansea.ac.uk/puzzle");

    private static HttpResponse getPuzzleResponse() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(20)).build();
        HttpRequest puzzleRequest = HttpRequest.newBuilder().uri(PUZZLEURI).build();
        return client.send(puzzleRequest, HttpResponse.BodyHandlers.ofString());
    }

    private static String getPuzzle() {
        String puzzle = "";
        try {
            puzzle = getPuzzleResponse().body().toString();
        } catch (IOException e) {
            System.out.println(e);
            puzzle = "IO_ERROR";
        } catch (InterruptedException e) {
            System.out.println(e);
            puzzle = "INTERRUPT_ERROR";
        }
        return puzzle;
    }
}
