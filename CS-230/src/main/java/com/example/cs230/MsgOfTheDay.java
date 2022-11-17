package com.example.cs230;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class MsgOfTheDay {
    public static void main(String[] args) {
        getRequest();
        shifting(inputS);
    }

    private static String inputS;
    private static String outputS;
    private static String cs230 = "CS-230";
    private static String MsgOfTheDay;
    // MsgOfTheDay is the stored output.

    public static void shifting(String str) {
        //get the number of characters in the final result. And put it to outputS(String).
        outputS = Integer.toString(cs230.length() + str.length());

        String out[] = new String[100];
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        //put the string into a character array
        char ch[] = new char[100];
        ch = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            int characterVal = 0;

            //changing for character to numbers of the character array
            for (int j = 0; j < letters.length; j++) {
                if (ch[i] == letters[j]) {
                    characterVal = j;
                }
            }

            int characterPla = i + 1;
            int shiftingVal = 0;

            // to prevent overflowing past 25
            if (characterPla > 26) {
                characterPla %= 26;
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
            } else if (shiftingVal == 26) {
                shiftingVal = 0;
            }
                //change the value from integers to string
                out[i] = String.valueOf(letters[shiftingVal]);
                //System.out.print(out[i]);

                //put the results into outputS String.
                outputS += out[i];

        }
        outputS += cs230;
        //outputS would be number of characters + encoded puzzle + CS-230
        sendSol();
    }

    public static void getRequest() {
        //get string from html to java
        try{
            URL url = new URL("http://cswebcat.swansea.ac.uk/puzzle");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            String line ="";
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder response = new StringBuilder();
            while ((line= bufferedReader.readLine())!=null) {
                response.append(line);
            }
            bufferedReader.close();
            inputS = response.toString();

        } catch (Exception e) {
            System.out.print("Error");
        }
    }
    
    public static void sendSol() {
        //send the solution to the html URL and gets the message of the day string.
        try{
            String htmlLink = "http://cswebcat.swansea.ac.uk/message?solution=" + outputS;
            URL url = new URL(htmlLink);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            String line ="";
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder response = new StringBuilder();
            while ((line= bufferedReader.readLine())!=null) {
                response.append(line);
            }
            bufferedReader.close();
            MsgOfTheDay = response.toString();
            System.out.println(MsgOfTheDay); //current output method
            //return MsgOfTheDay

        } catch (Exception e) {
            System.out.print("Error");
        }
    }

}
