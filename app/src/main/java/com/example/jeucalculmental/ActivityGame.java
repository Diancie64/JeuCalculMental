package com.example.jeucalculmental;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.EmptyStackException;
import java.util.Random;
import java.util.Timer;

public class ActivityGame extends AppCompatActivity {
    int hp=0;
    int score = 0;
    ProgressBar progressBar;
    Difficulty difficulty;
    TextView screen;
    TextView input;
    TextView life;
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
        // Clavier
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

        // UI + Systèmes
        TextView timerText = findViewById(R.id.timer);
        progressBar = findViewById(R.id.progressBar);
        difficulty = (Difficulty) getIntent().getSerializableExtra("difficulty");
        screen = findViewById(R.id.screen);
        input = findViewById(R.id.input);
        life = findViewById(R.id.life);




        int difficultyTime = 100;

        // Gestion du temps en fonction de la difficulté
        switch (difficulty){
            case EASY:
                difficultyTime = 120;
                hp = 3;
                break;
            case MEDIUM:
                difficultyTime = 80;
                hp = 2;
                break;
            case HARD:
                difficultyTime = 40;
                hp = 1;
                break;
        }

        // Mise en place du timer
        timer = new GameTimer(difficultyTime, progressBar, new GameTimer.TimerListener() {
            @Override
            public void onTick(int timeLeft) {
                String text = timeLeft + " sec";
                timerText.setText(text);
            }

            @Override
            public void onFinish() {
                removeLife();
            }
        });

        timer.start();

        updateLifeDisplay(hp, difficulty);

        question();
    }

    private void removeLife(){
        /// Perte d'une vie
        hp--;
        errorShake(life);
        updateLifeDisplay(hp, difficulty);
        input.setText("");
        if (hp == 0){
            endGame();
        }
    }

    private void errorShake(TextView view) {
        /// Animation en cas d'erreur
        float delta = 20f;

        ObjectAnimator animator = ObjectAnimator.ofFloat(
                view,
                "translationX",
                0, -delta, delta, -delta, delta, -delta / 2, delta / 2, 0
        );

        animator.setDuration(400);
        animator.start();
    }



    private void updateLifeDisplay(int hp, Difficulty difficulty) {
        /// Mise à jour de l'affichage des PV
        int length = (difficulty.ordinal() + 1) * 2;
        String text;
        switch (hp){
            case 3:
                text = "♥ ♥ ♥";
                break;
            case 2:
                text = "♥ ♥ ❌";
                break;
            case 1:
                text = "♥ ❌ ❌";
                break;
            default:
                text = "❌ ❌ ❌";
                break;
        }
        text = text.substring(0,length - 1);
        life.setText(text);
    }


    private void endGame() {
        /// Gestion de fin du jeu
        Intent intent = new Intent(this, EndScreen.class);
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("score", score);
        startActivity(intent);
    }

    private void manageClick(String number){
        /// Gestion du clavier
        String txt = input.getText().toString();
        switch (number){
            case "ok":
                if (!txt.isEmpty()){
                    validateAnswer();
                }
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
        /// Vérifie les réponses soumises
        double answer = getAnswer();
        if (answer == resultat){
            score += 100;
            timer.reset();
            question();
            input.setText("");
            timer.start();
        }else{
            removeLife();
        }
    }

    private double getAnswer(){
        /// Récupère la réponse dans la TextView
        String text = input.getText().toString().replace(",", ".");
        return Double.parseDouble(text);
    }


    private void question(){
        /// Génère une question aléatoire
        int min = 0;
        int max = 10;
        Random random = new Random();
        int firstNumber = random.nextInt(max - min + 1) + min;
        int secondNumber = random.nextInt(max - min + 1) + min;
        Operation operation = Operation.values()[random.nextInt(Operation.values().length)];
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
    }


}