package com.gamev.quizsparkstd.mathquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gamev.quizsparkstd.R;

public class MathCategoryActivity extends AppCompatActivity {

    // Quiz types
    public static final String EXTRA_MODE = "mode";
    public static final String MODE_ADD = "ADD";
    public static final String MODE_SUB = "SUB";
    public static final String MODE_MUL = "MUL";
    public static final String MODE_DIV = "DIV";
    public static final String MODE_BIGGEST = "BIGGEST";
    public static final String MODE_NEXT = "NEXT";
    public static final String MODE_BODMAS = "BODMAS";

    private CardView cardAdd, cardSub, cardMul, cardDiv, cardBiggest, cardNext, cardBodmas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_math_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setClickListeners();
    }

    private void initializeViews() {
        cardAdd = findViewById(R.id.cardAdd);
        cardSub = findViewById(R.id.cardSub);
        cardMul = findViewById(R.id.cardMul);
        cardDiv = findViewById(R.id.cardDiv);
        cardBiggest = findViewById(R.id.cardBiggest);
        cardNext = findViewById(R.id.cardNext);
        cardBodmas = findViewById(R.id.cardBodmas);
    }

    private void setClickListeners() {
        cardAdd.setOnClickListener(v -> openQuiz(MODE_ADD));
        cardSub.setOnClickListener(v -> openQuiz(MODE_SUB));
        cardMul.setOnClickListener(v -> openQuiz(MODE_MUL));
        cardDiv.setOnClickListener(v -> openQuiz(MODE_DIV));
        cardBiggest.setOnClickListener(v -> openQuiz(MODE_BIGGEST));
        cardNext.setOnClickListener(v -> openQuiz(MODE_NEXT));
        cardBodmas.setOnClickListener(v -> openQuiz(MODE_BODMAS));
    }

    private void openQuiz(String mode) {
        Intent i = new Intent(this, MathQuizActivity.class);
        i.putExtra(EXTRA_MODE, mode);
        startActivity(i);
    }
}
