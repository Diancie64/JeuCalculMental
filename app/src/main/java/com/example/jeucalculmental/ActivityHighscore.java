package com.example.jeucalculmental;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActivityHighscore extends AppCompatActivity {

    RadioGroup difficultySelector;
    Difficulty difficulty;
    RecyclerView scoreRecycler;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_highscore);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        difficultySelector = findViewById(R.id.difficulty);
        scoreRecycler = findViewById(R.id.scoreRecycler);

        scoreRecycler.setLayoutManager(new LinearLayoutManager(this));

        difficulty = Difficulty.EASY;
        difficultySelector.check(R.id.radioButtonFacile);

        updateList();

        difficultySelector.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonFacile) difficulty = Difficulty.EASY;
            else if (checkedId == R.id.radioButtonMoyenne) difficulty = Difficulty.MEDIUM;
            else if (checkedId == R.id.radioButtonDifficile) difficulty = Difficulty.HARD;

            updateList();
        });
    }

    private void updateList() {
        ScoreDatabase db = new ScoreDatabase(this);
        List<ScoreDatabase.ScoreEntry> list = db.getScoresByDifficulty(difficulty.name());

        ScoreAdapter adapter = new ScoreAdapter(list);
        scoreRecycler.setAdapter(adapter);
    }

}
