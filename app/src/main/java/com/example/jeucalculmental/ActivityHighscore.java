package com.example.jeucalculmental;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityHighscore extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    TextView textviewTest  ;


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

        textviewTest = findViewById(R.id.textView2);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        textviewTest.setText(dbHelper.afficherTout());
        dbHelper.close();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close(); // Toujours fermer proprement
    }
}