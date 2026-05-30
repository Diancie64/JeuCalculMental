package com.example.jeucalculmental;

import android.content.ContentValues;
import android.content.Intent;
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
    TextInputEditText pseudo;
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

        pseudo.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                finishGame();
                return true;
            }
            return false;
        });
    }

    private void finishGame() {
        addScoreToBase();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void addScoreToBase(){
        String nom = pseudo.getText().toString().trim();
        ScoreDatabase db = new ScoreDatabase(this);
        db.addScore(nom, difficulty.name(), Integer.parseInt(score));
    }

}