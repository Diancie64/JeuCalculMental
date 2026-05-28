package com.example.jeucalculmental;

// DatabaseHelper.java
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Infos de la base
    private static final String DATABASE_NAME = "maBase.db";
    private static final int DATABASE_VERSION = 1;

    // Nom de la table et colonnes
    public static final String TABLE_NAME = "Scores";
    public static final String COL_ID = "id";
    public static final String COL_NOM = "nom";
    public static final String COL_SCORE = "score";
    public static final String COL_DIFFICULTE = "difficulte";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NOM + " TEXT, "
                + COL_SCORE + " INTEGER,"
                + COL_DIFFICULTE + " TEXT"
                + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public String afficherTout() {
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder sb = new StringBuilder();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.getCount() == 0) {
            sb.append("La table est vide.");
        } else {
            while (cursor.moveToNext()) {
                sb.append("ID : ").append(cursor.getInt(0)).append("\n");
                sb.append("Nom : ").append(cursor.getString(1)).append("\n");
                sb.append("Score : ").append(cursor.getInt(2)).append("\n");
                sb.append("Difficulte : ").append(cursor.getInt(3)).append("\n");
                sb.append("----------\n");
            }
        }

        cursor.close();
        return sb.toString();
    }
}