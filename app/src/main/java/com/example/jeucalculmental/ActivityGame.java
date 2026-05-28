package com.example.jeucalculmental;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;
import java.util.Timer;

public class ActivityGame extends AppCompatActivity {
    ProgressBar progressBar;

    Difficulty difficulty;
    TextView screen;
    TextView input;
    double resultat;
    GameTimer timer;

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
        TextView timerText = findViewById(R.id.timer);
        Button btn0 = findViewById(R.id.btn0);
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);
        Button btnComma = findViewById(R.id.btnComma);
        Button btnOk = findViewById(R.id.btnOk);
        Button btnDelete = findViewById(R.id.btnDelete);
        btn0.setOnClickListener(v-> manageClick("0"));
        btn1.setOnClickListener(v-> manageClick("1"));
        btn2.setOnClickListener(v-> manageClick("2"));
        btn3.setOnClickListener(v-> manageClick("3"));
        btn4.setOnClickListener(v-> manageClick("4"));
        btn5.setOnClickListener(v-> manageClick("5"));
        btn6.setOnClickListener(v-> manageClick("6"));
        btn7.setOnClickListener(v-> manageClick("7"));
        btn8.setOnClickListener(v-> manageClick("8"));
        btn9.setOnClickListener(v-> manageClick("9"));
        btnComma.setOnClickListener(v-> manageClick(","));
        btnOk.setOnClickListener(v-> manageClick("ok"));
        btnDelete.setOnClickListener(v-> manageClick("del"));

        progressBar = findViewById(R.id.progressBar);
        difficulty = (Difficulty) getIntent().getSerializableExtra("difficulty");
        screen = findViewById(R.id.screen);
        input = findViewById(R.id.input);

        int difficultyTime = 100;

        switch (difficulty){
            case EASY:
                difficultyTime = 120;
                break;
            case MEDIUM:
                difficultyTime = 80;
                break;
            case HARD:
                difficultyTime = 40;
                break;
        }

        progressBar = findViewById(R.id.progressBar);

        timer = new GameTimer(difficultyTime, progressBar, new GameTimer.TimerListener() {
            @Override
            public void onTick(int timeLeft) {
                String text = timeLeft + " sec";
                timerText.setText(text);
            }

            @Override
            public void onFinish() {
                endGame();
            }
        });

        timer.start();



        resultat = question();

    }

    private void endGame() {
        // pass
    }

    private void manageClick(String number){
        String txt = input.getText().toString();
        switch (number){
            case "ok":
                validateAnswer();
                break;
            case "del":
                if (!txt.isEmpty()) {
                    input.setText(txt.substring(0, txt.length() - 1));
                }
                break;
            case ",":
                if (!txt.contains(",")){
                    input.append(number);
                }
                break;
            default:
                input.append(number);
                break;
        }
    }

    private void validateAnswer() {
        double answer = Double.parseDouble(input.getText().toString().replace(",", "."));

        if (answer == resultat){
            timer.reset();
            resultat = question();
            input.setText("");
            timer.start();
        }
    }


    private double question(){
        int min = -10;
        int max = 10;
        Random random = new Random();
        int firstNumber = random.nextInt(max - min + 1) + min;
        int secondNumber = random.nextInt(max - min + 1) + min;
        Operation operation = Operation.values()[random.nextInt(Operation.values().length)];
        double resultat;
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