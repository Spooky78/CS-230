package com.example.cs230;

public class PlayerProfile {
    private int profileIndex;
    private String name;
    public PlayerProfile(int profileIndex, String name) {
        //saveName();
        this.profileIndex = profileIndex;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public int getProfileIndex() {
        return profileIndex;
    }
/*
    public void saveName() {
        if (!nameExisted()) {
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
    */
}
