package com.example.jeucalculmental;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameSettings extends AppCompatActivity {
    RadioGroup difficultySelector;
    Difficulty difficulty = Difficulty.EASY;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game_settings), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        difficultySelector = findViewById(R.id.DifficultySelector);

        difficultySelector.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.easy) {
                difficulty = Difficulty.EASY;
            } else if (checkedId == R.id.medium) {
                difficulty = Difficulty.MEDIUM;
            } else if (checkedId == R.id.hard) {
                difficulty = Difficulty.HARD;
            }
        });



        start = findViewById(R.id.start);
        start.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityGame.class);
            intent.putExtra("difficulty", difficulty);
            startActivity(intent);
        });


    }
}