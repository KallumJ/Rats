package motd;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;

public class MoTD {
    private static final URI PUZZLEURI = URI.create("http://cswebcat.swansea.ac.uk/puzzle");

    private static String getPuzzle() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(20)).build();
        HttpRequest puzzleRequest = HttpRequest.newBuilder().uri(PUZZLEURI).build();
        HttpResponse response = client.send(puzzleRequest, HttpResponse.BodyHandlers.ofString());
        return response.body().toString();
    }

    private static char charShift(char ch, int shift) {
        final int AZLENGTH = 26;
        final int VALUE_A = (int) 'A';
        int charIndex = ch - VALUE_A;
        charIndex = (charIndex + shift) % AZLENGTH;
        if (charIndex < 0) {
            charIndex += AZLENGTH;
        }
        char newChar = (char) (VALUE_A + charIndex);
        return newChar;
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

    public static String getMoTD() {
        String puzzle = "";
        try {
            puzzle = getPuzzle();
        } catch (IOException e) {
            System.out.println(e);
            return "IO_ERROR";
        } catch (InterruptedException e) {
            System.out.println(e);
            return "INTERRUPT_ERROR";
        }

        String solution = solvePuzzle(puzzle);
        System.out.println(solution);
        return "";
    }

    public static void main(String args[]) {
        String temp = getMoTD();
    }

}
