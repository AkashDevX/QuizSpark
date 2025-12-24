package com.gamev.quizsparkstd.mathquiz;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gamev.quizsparkstd.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;



public class MathQuizActivity extends AppCompatActivity {

    private TextView tvQuestion, tvResult, tvScore, tvProgress, tvTimer;
    private RadioGroup rgOptions;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button btnSubmit, btnNext;

    private final Random random = new Random();
    private String mode = MathCategoryActivity.MODE_ADD;

    private int score = 0;
    private int currentQ = 1;
    private final int totalQ = 20;

    private int correctAnswer = 0;
    private boolean answered = false;

    private CountDownTimer timer;
    private final long TIME_PER_QUESTION_MS = 15000; // 15 seconds (change to 20000 for 20 seconds)
    private int minByQuestion() {
        // Q1-7: 2 digits, Q8-14: 3 digits, Q15-20: 4 digits
        if (currentQ <= 7) return 10;
        if (currentQ <= 14) return 100;
        return 1000;
    }

    private int maxByQuestion() {
        if (currentQ <= 7) return 99;
        if (currentQ <= 14) return 999;
        return 9999;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_quiz);
        String incoming = getIntent().getStringExtra(MathCategoryActivity.EXTRA_MODE);
        if (incoming != null) mode = incoming;


        tvQuestion = findViewById(R.id.tvQuestion);
        tvResult   = findViewById(R.id.tvResult);
        tvScore    = findViewById(R.id.tvScore);
        tvProgress = findViewById(R.id.tvProgress);
        tvTimer    = findViewById(R.id.tvTimer);

        rgOptions = findViewById(R.id.rgOptions);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnNext   = findViewById(R.id.btnNext);

        loadQuestion();

        btnSubmit.setOnClickListener(v -> submitAnswer());

        btnNext.setOnClickListener(v -> {
            if (currentQ >= totalQ) {
                finishQuiz();
            } else {
                currentQ++;
                loadQuestion();
            }
        });
    }

    private void loadQuestion() {
        answered = false;
        btnNext.setEnabled(false);
        btnSubmit.setEnabled(true);

        tvResult.setText("");
        rgOptions.clearCheck();
        setOptionsEnabled(true);

        tvProgress.setText("Q: " + currentQ + "/" + totalQ);
        tvScore.setText("Score: " + score);

        // ✅ Handle non-operator modes first
        if (mode.equals(MathCategoryActivity.MODE_BIGGEST)) {
            loadBiggestNumberQuestion();
            startTimer();
            return;
        }

        if (mode.equals(MathCategoryActivity.MODE_NEXT)) {
            loadNextNumberQuestion();
            startTimer();
            return;
        }

        if (mode.equals(MathCategoryActivity.MODE_BODMAS)) {
            loadBodmasQuestion();
            startTimer();
            return;
        }

        int min, max;
        if (currentQ <= 7) {
            min = 10;   max = 99;
        } else if (currentQ <= 14) {
            min = 100;  max = 999;
        } else {
            min = 1000; max = 9999;
        }

        // ✅ Operator-based question (ADD/SUB/MUL/DIV)
        char op = getOperatorFromMode();
        int a, b;

        if (op == '/') {
            // Keep integer division but increase difficulty
            int divMax = (currentQ <= 7) ? 12 : (currentQ <= 14 ? 20 : 30);
            b = randomInRange(2, divMax);
            int res = randomInRange(2, divMax);
            a = b * res;
            correctAnswer = a / b;

        } else if (op == '-') {
            // Use digit range and avoid negative
            a = randomInRange(min, max);
            b = randomInRange(min, a);
            correctAnswer = a - b;

        } else if (op == '*') {
            // Increase multiplication difficulty gradually (but avoid huge numbers)
            int mulMax = (currentQ <= 7) ? 12 : (currentQ <= 14 ? 20 : 30);
            a = randomInRange(2, mulMax);
            b = randomInRange(2, mulMax);
            correctAnswer = a * b;

        } else { // '+'
            // Use digit range
            a = randomInRange(min, max);
            b = randomInRange(min, max);
            correctAnswer = a + b;
        }

        // Display question (use × and ÷ symbols nicely)
        String symbol = (op == '*') ? "×" : (op == '/') ? "÷" : String.valueOf(op);
        tvQuestion.setText(a + " " + symbol + " " + b + " = ?");

        ArrayList<Integer> options = buildOptions(correctAnswer, op);
        applyOptions(options);

        startTimer();
    }


    private void applyOptions(ArrayList<Integer> options) {
        rb1.setText(String.valueOf(options.get(0)));
        rb2.setText(String.valueOf(options.get(1)));
        rb3.setText(String.valueOf(options.get(2)));
        rb4.setText(String.valueOf(options.get(3)));
    }
    private void loadBiggestNumberQuestion() {
        int min = minByQuestion();
        int max = maxByQuestion();

        int a = randomInRange(min, max);
        int b = randomInRange(min, max);
        int c = randomInRange(min, max);
        int d = randomInRange(min, max);

        correctAnswer = Math.max(Math.max(a, b), Math.max(c, d));

        tvQuestion.setText("Which is the biggest number?\n" + a + " , " + b + " , " + c + " , " + d);

        ArrayList<Integer> options = new ArrayList<>();
        options.add(a);
        options.add(b);
        options.add(c);
        options.add(d);

        Collections.shuffle(options);
        applyOptions(options);
    }

    private void loadBodmasQuestion() {
        int min = (currentQ <= 7) ? 2 : (currentQ <= 14 ? 5 : 10);
        int max = (currentQ <= 7) ? 12 : (currentQ <= 14 ? 25 : 60);

        int pattern = random.nextInt(4);

        int a, b, c, d, e;
        String expr;

        if (pattern == 0) {
            a = randomInRange(min, max);
            b = randomInRange(2, 12);
            c = randomInRange(2, 12);
            d = randomInRange(min, max);
            correctAnswer = a + (b * c) - d;
            expr = a + " + " + b + " × " + c + " − " + d;

        } else if (pattern == 1) {
            a = randomInRange(min, max);
            b = randomInRange(min, max);
            c = randomInRange(2, 12);
            correctAnswer = (a + b) * c;
            expr = "(" + a + " + " + b + ") × " + c;

        } else if (pattern == 2) {
            a = randomInRange(2, 15);
            b = randomInRange(2, 15);

            d = randomInRange(2, 12);
            int q = randomInRange(2, 12);
            c = d * q; // c/d = q integer

            correctAnswer = (a * b) + (c / d);
            expr = "(" + a + " × " + b + ") + (" + c + " ÷ " + d + ")";

        } else { // pattern == 3
            a = randomInRange(min, max);
            b = randomInRange(min, max);
            c = randomInRange(2, 10);
            d = randomInRange(2, 20);
            e = randomInRange(2, 10);

            correctAnswer = (a + b) * c - (d * e);
            expr = "(" + a + " + " + b + ") × " + c + " − (" + d + " × " + e + ")";
        }

        tvQuestion.setText("Solve (BODMAS):\n" + expr + " = ?");

        ArrayList<Integer> options = new ArrayList<>();
        options.add(correctAnswer);

        HashSet<Integer> used = new HashSet<>();
        used.add(correctAnswer);

        while (options.size() < 4) {
            int wrong = correctAnswer + randomInRange(-20, 20);
            if (!used.contains(wrong)) {
                used.add(wrong);
                options.add(wrong);
            }
        }

        Collections.shuffle(options);
        applyOptions(options);
    }


    private void loadNextNumberQuestion() {
        int start = randomInRange(1, 20);
        int step = randomInRange(2, 10);

        int n1 = start;
        int n2 = start + step;
        int n3 = start + (2 * step);
        int n4 = start + (3 * step);

        correctAnswer = start + (4 * step);

        tvQuestion.setText("What is the next number?\n" + n1 + ", " + n2 + ", " + n3 + ", " + n4 + ", ?");

        ArrayList<Integer> options = new ArrayList<>();
        options.add(correctAnswer);

        // 3 wrong answers
        HashSet<Integer> used = new HashSet<>();
        used.add(correctAnswer);

        while (options.size() < 4) {
            int wrong = correctAnswer + randomInRange(-10, 10);
            if (wrong <= 0) continue;
            if (!used.contains(wrong)) {
                used.add(wrong);
                options.add(wrong);
            }
        }

        Collections.shuffle(options);
        applyOptions(options);
    }



    private void submitAnswer() {
        if (answered) return;

        int selectedId = rgOptions.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        stopTimer();

        RadioButton selected = findViewById(selectedId);
        int selectedValue = Integer.parseInt(selected.getText().toString());

        answered = true;
        btnNext.setEnabled(true);
        btnSubmit.setEnabled(false);
        setOptionsEnabled(false);

        if (selectedValue == correctAnswer) {
            score++;
            tvResult.setText("✅ Correct!");
        } else {
            tvResult.setText("❌ Wrong! Correct: " + correctAnswer);
        }
        tvScore.setText("Score: " + score);
    }

    private void startTimer() {
        stopTimer();
        timer = new CountDownTimer(TIME_PER_QUESTION_MS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long sec = millisUntilFinished / 1000;
                tvTimer.setText("Time: " + sec + "s");
            }

            @Override
            public void onFinish() {
                tvTimer.setText("Time: 0s");
                timeUp();
            }
        }.start();
    }

    private void timeUp() {
        answered = true;
        btnNext.setEnabled(true);
        btnSubmit.setEnabled(false);
        setOptionsEnabled(false);

        tvResult.setText("⏰ Time’s up! Correct: " + correctAnswer);
    }

    private void finishQuiz() {
        stopTimer();

        Intent i = new Intent(this, QuizResultActivity.class);
        i.putExtra(QuizResultActivity.EXTRA_SCORE, score);
        i.putExtra(QuizResultActivity.EXTRA_TOTAL, totalQ);
        startActivity(i);
        finish();
    }


    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void setOptionsEnabled(boolean enabled) {
        for (int i = 0; i < rgOptions.getChildCount(); i++) {
            rgOptions.getChildAt(i).setEnabled(enabled);
        }
    }

    private ArrayList<Integer> buildOptions(int correct, char op) {
        ArrayList<Integer> options = new ArrayList<>();
        options.add(correct);

        HashSet<Integer> used = new HashSet<>();
        used.add(correct);

        while (options.size() < 4) {
            int wrong = generateWrongAnswer(correct, op);
            if (!used.contains(wrong)) {
                used.add(wrong);
                options.add(wrong);
            }
        }

        Collections.shuffle(options);
        return options;
    }

    private char getOperatorFromMode() {
        switch (mode) {
            case MathCategoryActivity.MODE_ADD: return '+';
            case MathCategoryActivity.MODE_SUB: return '-';
            case MathCategoryActivity.MODE_MUL: return '*';
            case MathCategoryActivity.MODE_DIV: return '/';
            default: return '+';
        }
    }


    private int randomInRange(int min, int max) {
        return min + random.nextInt((max - min) + 1);
    }

    private int generateWrongAnswer(int correct, char op) {
        int delta = (op == '*') ? randomInRange(1, 10) : randomInRange(1, 15);
        int sign = random.nextBoolean() ? 1 : -1;
        int wrong = correct + (sign * delta);
        if (wrong < 0) wrong = correct + delta;
        return wrong;
    }

    @Override
    protected void onDestroy() {
        stopTimer();
        super.onDestroy();
    }
}
