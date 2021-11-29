package motd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URI;

/**
 * Contains methods to return the Message of the Day from the <a href="http://cswebcat.swansea.ac.uk/">MotD API</a>.
 * @author Matthew Stephenson
 */
public class MssgOfTheDay {
    /**
     * Returns the current Message of the Day as a string.
     *
     * May return some error instead, should the message be unable to be
     * retrieved.
     * @return      current message string, or {@code "PUZZLE_IO_ERROR"},
     *      {@code "PUZZLE_INTERRUPT_ERROR"}, {@code "SOLUTION_IO_ERROR"},
     *      {@code "SOLUTION_INTERRUPT_ERROR"}, as appropriate, errors thrown
     *      while attempting to retrieve message.
     */
    public static String getMotD() {
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

    /**
     * Returns a string containing the body of the from the specified URI.
     *
     * The request made is a generic HTTP GET, response
     * is then parsed, and only the body returned as string.
     * Can throw errors due to bad web responses/non-responses.
     * @param   requestURI              the URI of the page to be requested from
     * @return                          the body of the HTTP response from the supplied URI, as string
     * @throws  IOException             if an I/O error occurs when sending or receiving
     */
    private static String getHttpResponseBodyAsString(URI requestURI) throws IOException {
        URL website = new URL(requestURI.toString());
        URLConnection connection = website.openConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }

        return response.toString();
    }

    /**
     * Returns the current puzzle string obtained from the <a href="http://cswebcat.swansea.ac.uk/">MotD API</a>.
     *
     * Calls {@link #getHttpResponseBodyAsString} on the URI of the puzzle webpage in the MotD API.
     * Can throw errors due to bad web responses/non-responses.
     * @return                          the puzzle string
     * @throws  IOException             if an I/O error occurs when sending or receiving
     * @throws  InterruptedException    if the operation is interrupted before response recieved
     */
    private static String getPuzzle() throws IOException, InterruptedException {
        final URI PUZZLE_URI = URI.create("http://cswebcat.swansea.ac.uk/puzzle");
        return getHttpResponseBodyAsString(PUZZLE_URI);
    }

    /**
     * Takes the puzzle solution, returns current message from the <a href="http://cswebcat.swansea.ac.uk/">MotD API</a>.
     *
     * Calls {@link #getHttpResponseBodyAsString} on the URI of the solution webpage
     * with the solution itself appended as a query parameter to the URI.
     * Can throw errors due to bad web responses/non-responses.
     * @param   solution                the puzzle solution for submission
     * @return                          the current message
     * @throws  IOException             if an I/O error occurs when sending or receiving
     * @throws  InterruptedException    if the operation is interrupted before response recieved
     */
    private static String submitSolution(String solution) throws IOException, InterruptedException {
        final String SOLUTION_URI_PREPEND = "http://cswebcat.swansea.ac.uk/message?solution=";
        final URI SOLUTION_URI = URI.create(SOLUTION_URI_PREPEND + solution);
        return getHttpResponseBodyAsString(SOLUTION_URI);
    }

    /**
     * Returns the solution to the supplied puzzle string.
     *
     * Iterates over the puzzle string, applying {@link #charShift} to each char
     * as appropriate, then pre- and suffixing as needed.
     * Puzzle solved as specified in the <a href="http://cswebcat.swansea.ac.uk/">MotD API</a>.
     * @param   puzzle  the puzzle string
     * @return          the puzzle solution
     */
    private static String solvePuzzle(String puzzle) {
        String solution = "";
        for (int i = 1; i <= puzzle.length(); i++) {
            int shift = ((int) Math.pow(-1, i)) * i; // -1, 2, -3, 4, -5,...
            char shiftedChar = charShift(puzzle.charAt(i - 1), shift);
            solution += shiftedChar;
        }
        solution += "CS-230";
        solution = solution.length() + solution;
        return solution;
    }

    /**
     * Shifts the character up or down the (upper) alphabet by amount specified.
     *
     * Will only function as intended on capital letters A-Z, +ve shift
     * returning character alphabetically shifted along A-Z, wrapping both ways.
     * -ve shift moves oppositely.
     *
     * e.g. {@code charShift('B',3)} returns {@code 'E'}.
     * @param   ch      the character to be shifted
     * @param   shift   the shift value (+ve shift is alphabetical)
     * @return          the shifted character
     */
    private static char charShift(char ch, int shift) {
        final int A_TO_Z_LENGTH = 1 + (int) ('Z' - 'A');
        final int VALUE_A = (int) 'A';
        int charIndex = ch - VALUE_A;
        charIndex = (charIndex + shift) % A_TO_Z_LENGTH;

        // % is not true modulo; this fixes negative shifts that wrap A->Z
        if (charIndex < 0) {
            charIndex += A_TO_Z_LENGTH;
        }
        char newChar = (char) (VALUE_A + charIndex);
        return newChar;
    }
}