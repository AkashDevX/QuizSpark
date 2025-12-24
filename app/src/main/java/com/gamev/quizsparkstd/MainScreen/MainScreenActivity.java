package com.gamev.quizsparkstd.MainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gamev.quizsparkstd.HistoryQuiz.HistoryQuizActivity;
import com.gamev.quizsparkstd.R;

public class MainScreenActivity extends AppCompatActivity {

    private CardView cardHistory, cardMaths, cardCelebrity, cardAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize card views
        cardHistory = findViewById(R.id.cardHistory);
        cardMaths = findViewById(R.id.cardMaths);
        cardCelebrity = findViewById(R.id.cardCelebrity);
        cardAbout = findViewById(R.id.cardAbout);

        // Set click listeners
        cardHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, HistoryQuizActivity.class);
                startActivity(intent);
            }
        });

        cardMaths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Navigate to Maths Quiz activity
                Toast.makeText(MainScreenActivity.this, "Maths Quiz", Toast.LENGTH_SHORT).show();
            }
        });

        cardCelebrity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Navigate to Celebrity Quiz activity
                Toast.makeText(MainScreenActivity.this, "Celebrity Quiz", Toast.LENGTH_SHORT).show();
            }
        });

        cardAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Navigate to About Us activity
                Toast.makeText(MainScreenActivity.this, "About Us", Toast.LENGTH_SHORT).show();
            }
        });
    }
}