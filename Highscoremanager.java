package com.BowlORama;

import com.badlogic.gdx.Gdx;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Highscoremanager {

    private static final String FILE_NAME = "highscores.txt";
    public static class ScoreEntry {
        String name;
        int score;
        public ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }

    // SAVE SCORE
    public static void saveScore(String name, int score) {

        try {

           BufferedWriter writer = new BufferedWriter( Gdx.files.local(FILE_NAME).writer(true));

            writer.write(name + ":" + score);
            writer.newLine();

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // LOAD ALL SCORES
    public static ArrayList<ScoreEntry> loadScores() {

        ArrayList<ScoreEntry> scores = new ArrayList<>();

        try {

            if(!Gdx.files.local(FILE_NAME).exists())
                return scores;

            BufferedReader reader = new BufferedReader( Gdx.files.local(FILE_NAME).reader());
            String line;

            while((line = reader.readLine()) != null) {

                String[] parts = line.split(":");
                if(parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    scores.add(new ScoreEntry(name, score));
                }
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // SORT DESCENDING
        Collections.sort(scores, new Comparator<ScoreEntry>() {
            @Override
            public int compare(ScoreEntry a, ScoreEntry b) {
                return b.score - a.score;
            }
        });

        return scores;
    }

    // TOP 10
    public static ArrayList<ScoreEntry> getTop10Scores() {

        ArrayList<ScoreEntry> scores = loadScores();

        ArrayList<ScoreEntry> top10 = new ArrayList<>();

        for(int i = 0; i < scores.size() && i < 10; i++) {
            top10.add(scores.get(i));
        }

        return top10;
    }
}
