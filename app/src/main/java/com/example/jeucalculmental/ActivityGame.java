package com.example.jeucalculmental;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class ActivityGame extends AppCompatActivity {

    Difficulty difficulty;
    TextView screen;
    float resultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        difficulty = (Difficulty) getIntent().getSerializableExtra("difficulty");
        screen = findViewById(R.id.screen);

        resultat = question();
    }

    private float question(){
        int min = -10;
        int max = 10;
        Random random = new Random();
        int firstNumber = random.nextInt(max - min + 1) + min;
        int secondNumber = random.nextInt(max - min + 1) + min;
        Operation operation = Operation.values()[random.nextInt(Operation.values().length)];
        float resultat;
        String affichage;

        switch (operation){
            case PLUS:
                affichage = firstNumber + " + " + secondNumber;
                resultat = firstNumber + secondNumber;
                break;
            case MINUS:
                affichage = firstNumber + " - " + secondNumber;
                resultat = firstNumber - secondNumber;
                break;
            case TIMES:
                affichage = firstNumber + " * " + secondNumber;
                resultat = firstNumber * secondNumber;
                break;
            case DIVIDE:
                affichage = firstNumber + " / " + secondNumber;
                resultat = (float) firstNumber / secondNumber;
                break;
            default:
                affichage = "";
                resultat = 0;
                break;
        }

        screen.setText(affichage);
        return resultat;
    }


}