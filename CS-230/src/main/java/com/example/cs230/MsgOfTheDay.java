package com.example.cs230;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Responsible for message of the day.
 *
 * @author Everybody.
 */
public class MsgOfTheDay {
    private String outputS;
    //MsgOfTheDay is the stored output.
    private String msgOfTheDay = "Error, the message is not displayed.";


    /**
     * Encode the puzzle into the solution of the message of the day.
     *
     * @param str the code from API.
     * @return encoded puzzle.
     */
    public String encode(String str) {
        final int stringLength = 100;
        final int alphabetLength = 26;
        final String cs230 = "CS-230";

        //get the number of characters in the final result. And put it to outputS(String).
        outputS = Integer.toString(cs230.length() + str.length());

        String[] out = new String[stringLength];
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        //put the string into a character array
        char[] ch = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            int characterVal = 0;

            //changing for character to numbers of the character array
            for (int j = 0; j < letters.length; j++) {
                if (ch[i] == letters[j]) {
                    characterVal = j;
                }
            }

            int characterPla = i + 1;
            int shiftingVal;

            // to prevent overflowing past 25
            if (characterPla > alphabetLength) {
                characterPla %= alphabetLength;
            }
            //Check if the index of the character array is even or odd
            if (characterPla % 2 == 1) {
                shiftingVal = characterVal - characterPla;
            } else {
                shiftingVal = characterVal + characterPla;
            }
            //encoding for example index 0, +1; index 1, -2; index 2, +3;
            if (shiftingVal > letters.length) {
                shiftingVal = shiftingVal - letters.length;
            } else if (shiftingVal < 0) {
                shiftingVal += letters.length;
            } else if (shiftingVal == alphabetLength) {
                shiftingVal = 0;
            }
            //change the value from integers to string
            out[i] = String.valueOf(letters[shiftingVal]);
            //put the results into outputS String.
            outputS = outputS + out[i];
        }
        outputS += cs230;
        //outputS would be number of characters + encoded puzzle + CS-230
        return sendSol();
    }


    /**
     * Get the puzzle code from the API.
     *
     * @return The puzzle from API.
     */
    public String getRequest() {
        String inputS;

        //get string from html to java
        try {
            URL url = new URL("http://cswebcat.swansea.ac.uk/puzzle");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            String line;
            InputStreamReader inputStreamReader =
                    new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder response = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            bufferedReader.close();
            inputS = response.toString();

        } catch (Exception e) {
            inputS = "";
            System.out.println("Fuck");
        }
        return encode(inputS);
    }

    /**
     * Return the solution to the API and then get the message of the day.
     *
     * @return The message of the day.
     */

    public String sendSol() {
        //send the solution to the html URL and gets the message of the day string.
        try {
            String htmlLink = "http://cswebcat.swansea.ac.uk/message?solution=" + outputS;
            URL url = new URL(htmlLink);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            String line;
            InputStreamReader inputStreamReader =
                    new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder response = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            bufferedReader.close();
            msgOfTheDay = response.toString();

        } catch (Exception e) {
            System.out.println("Fuck");
        }
        return msgOfTheDay;
    }
}