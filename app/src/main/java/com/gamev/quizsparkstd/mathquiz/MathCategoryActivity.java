package com.gamev.quizsparkstd.mathquiz;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_category);

        findViewById(R.id.btnAdd).setOnClickListener(v -> openQuiz(MODE_ADD));
        findViewById(R.id.btnSub).setOnClickListener(v -> openQuiz(MODE_SUB));
        findViewById(R.id.btnMul).setOnClickListener(v -> openQuiz(MODE_MUL));
        findViewById(R.id.btnDiv).setOnClickListener(v -> openQuiz(MODE_DIV));
        findViewById(R.id.btnBiggest).setOnClickListener(v -> openQuiz(MODE_BIGGEST));
        findViewById(R.id.btnNextNumber).setOnClickListener(v -> openQuiz(MODE_NEXT));
        findViewById(R.id.btnBodmas).setOnClickListener(v -> openQuiz(MODE_BODMAS));
    }

    private void openQuiz(String mode) {
        Intent i = new Intent(this, MathQuizActivity.class);
        i.putExtra(EXTRA_MODE, mode);
        startActivity(i);
    }
}
