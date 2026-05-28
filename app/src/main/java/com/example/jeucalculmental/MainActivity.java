package com.example.jeucalculmental;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button play;
    Button highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialise la base dès le lancement
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.getWritableDatabase(); // Force la création si elle n'existe pas
        dbHelper.close();

    play = findViewById(R.id.button_play);
    play.setOnClickListener(v -> {
        Intent intent = new Intent(this, GameSettings.class);
        startActivity(intent);
    });
    highscore = findViewById(R.id.button_high_score);
    highscore.setOnClickListener(v -> {
        Intent intent = new Intent(this, ActivityHighscore.class);
        startActivity(intent);
    });

    }
}