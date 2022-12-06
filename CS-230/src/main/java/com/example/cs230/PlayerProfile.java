package com.example.cs230;

import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class PlayerProfile {
    private String name;
    private final String PROFILE_EXISTED = "Fuck. Someone used your name!";
private final String FILE_PATH ="CS-230/src/main/resources/Levels/Profile.txt";
    public PlayerProfile(String name) {
        this.name = name;
        saveName();
    }

    public void setScore() {
    }

    public String getName() {
        return name;
    }

    public void saveName() {
        if (nameExisted()) {

        } else {
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new FileWriter(FILE_PATH, true));
                bw.write(name);
                bw.newLine();
                bw.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException ioe2) {
                    }
                }
            }
        }
    }

    public boolean nameExisted() {
        String existingName;
        try {
            File playerProfile = new File(FILE_PATH);
            Scanner playerProfileFileReader = new Scanner(playerProfile);
            while (playerProfileFileReader.hasNextLine()) {
                existingName = playerProfileFileReader.nextLine();
                if (name.equals(existingName)) {
                    return true;
                }
            }
            return false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getArrayOfProfiles() {
        ArrayList<String> nameList = new ArrayList<>();
        try {
            File playerProfile = new File(FILE_PATH);
            Scanner playerProfileFileReader = new Scanner(playerProfile);
            while (playerProfileFileReader.hasNextLine()) {
                String names = playerProfileFileReader.nextLine();
                nameList.add(names);
                }
            } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return nameList;
    }

    public PlayerProfile getProfile() {
        return this;
    }
}
