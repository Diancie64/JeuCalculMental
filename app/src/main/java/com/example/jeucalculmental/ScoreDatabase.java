package com.example.jeucalculmental;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ScoreDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "scores.db";
    private static final int DB_VERSION = 1;

    public ScoreDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE scores (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "pseudo TEXT NOT NULL, " +
                        "difficulty TEXT NOT NULL, " +
                        "score INTEGER NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS scores");
        onCreate(db);
    }

    // -----------------------------
    // INSERTION D’UN SCORE
    // -----------------------------
    public void addScore(String pseudo, String difficulty, int score) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("pseudo", pseudo);
        values.put("difficulty", difficulty);
        values.put("score", score);

        db.insert("scores", null, values);
        db.close();
    }

    // reset
    public void resetDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM scores");
    }


    // -----------------------------
    // RÉCUPÉRATION DES SCORES PAR DIFFICULTÉ
    // -----------------------------
    public List<ScoreEntry> getScoresByDifficulty(String difficulty) {
        List<ScoreEntry> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT pseudo, difficulty, score FROM scores WHERE difficulty = ? ORDER BY score DESC LIMIT 10",
                new String[]{difficulty}
        );

        if (cursor.moveToFirst()) {
            do {
                list.add(new ScoreEntry(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    // -----------------------------
    // RÉCUPÉRATION DE TOUS LES SCORES (OPTIONNEL)
    // -----------------------------
    public List<ScoreEntry> getAllScores() {
        List<ScoreEntry> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT pseudo, difficulty, score FROM scores ORDER BY score DESC",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                list.add(new ScoreEntry(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    // -----------------------------
    // PETITE CLASSE POUR STOCKER LES SCORES
    // -----------------------------
    public static class ScoreEntry {
        public String pseudo;
        public String difficulty;
        public int score;

        public ScoreEntry(String pseudo, String difficulty, int score) {
            this.pseudo = pseudo;
            this.difficulty = difficulty;
            this.score = score;
        }
    }
}
