package motd;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;

public class MssgOfTheDay {
    public static String getMoTD() {
        String puzzle = "";
        try {
            puzzle = getPuzzle();
        } catch (IOException e) {
            System.out.println(e);
            return "PUZZLE_IO_ERROR";
        } catch (InterruptedException e) {
            System.out.println(e);
            return "PUZZLE_INTERRUPT_ERROR";
        }

        String solution = solvePuzzle(puzzle);

        String motdMessage = "";
        try {
            motdMessage = submitSolution(solution);
        } catch (IOException e) {
            System.out.println(e);
            return "SOLUTION_IO_ERROR";
        } catch (InterruptedException e) {
            System.out.println(e);
            return "SOLUTION_INTERRUPT_ERROR";
        }

        return motdMessage;
    }

    private static String getHttpResponseBodyAsString(URI requestURI) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(20)).build();

        HttpRequest request = HttpRequest.newBuilder(requestURI).build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body().toString();
    }

    private static String getPuzzle() throws IOException, InterruptedException {
        final URI PUZZLE_URI = URI.create("http://cswebcat.swansea.ac.uk/puzzle");
        return getHttpResponseBodyAsString(PUZZLE_URI);
    }

    private static String submitSolution(String solution) throws IOException, InterruptedException {
        final String SOLUTION_URI_PREPEND = "http://cswebcat.swansea.ac.uk/message?solution=";
        final URI SOLUTION_URI = URI.create(SOLUTION_URI_PREPEND + solution);
        return getHttpResponseBodyAsString(SOLUTION_URI);
    }

    private static String solvePuzzle(String puzzle) {
        String solution = "";
        for (int i = 1; i <= puzzle.length(); i++) {
            int shift = ((int) Math.pow(-1, i)) * i;
            char shiftedChar = charShift(puzzle.charAt(i - 1), shift);
            solution += shiftedChar;
        }
        solution += "CS-230";
        solution = solution.length() + solution;
        return solution;
    }

    private static char charShift(char ch, int shift) {
        final int A_TO_Z_LENGTH = 1 + (int) ('Z' - 'A');
        final int VALUE_A = (int) 'A';
        int charIndex = ch - VALUE_A;
        charIndex = (charIndex + shift) % A_TO_Z_LENGTH;
        if (charIndex < 0) {
            charIndex += A_TO_Z_LENGTH;
        }
        char newChar = (char) (VALUE_A + charIndex);
        return newChar;
    }
}
