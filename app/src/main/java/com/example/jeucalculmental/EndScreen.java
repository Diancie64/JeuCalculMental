package com.example.jeucalculmental;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EndScreen extends AppCompatActivity {
    TextView scoreText;
    TextInputLayout pseudo;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    Difficulty difficulty;
    String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_end_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        difficulty = (Difficulty) getIntent().getSerializableExtra("difficulty");
        score = String.valueOf(getIntent().getIntExtra("score", 0));

        pseudo = findViewById(R.id.nameInputLayout);
        scoreText = findViewById(R.id.score);

        scoreText.setText(score);

    }

    private void AddScoreToBase(){
        String nom= String.valueOf(pseudo.getEditText());

        // On réutilise le même helper, même base
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();


        // Utilisation normale
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_NOM, nom);
        values.put(DatabaseHelper.COL_SCORE, score);
        values.put(DatabaseHelper.COL_DIFFICULTE, String.valueOf(difficulty));
        db.insert(DatabaseHelper.TABLE_NAME, null, values);
    }

}