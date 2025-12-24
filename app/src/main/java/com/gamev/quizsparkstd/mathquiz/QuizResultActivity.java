package com.gamev.quizsparkstd.mathquiz;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gamev.quizsparkstd.MainActivity;
import com.gamev.quizsparkstd.R;

public class QuizResultActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "score";
    public static final String EXTRA_TOTAL = "total";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        int score = getIntent().getIntExtra(EXTRA_SCORE, 0);
        int total = getIntent().getIntExtra(EXTRA_TOTAL, 20);

        TextView tvFinalScore = findViewById(R.id.tvFinalScore);
        tvFinalScore.setText("Score: " + score + "/" + total);

        Button btnMainMenu = findViewById(R.id.btnMainMenu);
        btnMainMenu.setOnClickListener(v -> {
            // If your main screen is MainActivity:
            Intent i = new Intent(QuizResultActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }
}

