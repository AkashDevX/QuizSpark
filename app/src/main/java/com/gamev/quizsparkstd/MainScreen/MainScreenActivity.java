package com.gamev.quizsparkstd.MainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gamev.quizsparkstd.CelebrityQuiz.CelebrityQuestionsActivity;
import com.gamev.quizsparkstd.HistoryQuiz.HistoryQuizActivity;
import com.gamev.quizsparkstd.aboutus.AboutUsActivity;
import com.gamev.quizsparkstd.mathquiz.MathCategoryActivity;
import com.gamev.quizsparkstd.R;

public class MainScreenActivity extends AppCompatActivity {

    private CardView cardHistory, cardMaths, cardCelebrity;

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
                Intent intent = new Intent(MainScreenActivity.this, MathCategoryActivity.class);
                startActivity(intent);
            }
        });

        // Celebrity quiz is locked (level 3) - can still be clicked for now
        // In future, you can add a check to show a message that it's locked
        cardCelebrity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, CelebrityQuestionsActivity.class);
                startActivity(intent);
            }
        });
    }
}