package com.gamev.quizsparkstd.mathquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gamev.quizsparkstd.HistoryQuiz.QuizResultActivity;
import com.gamev.quizsparkstd.QuestionModels.Question;
import com.gamev.quizsparkstd.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class MathQuizActivity extends AppCompatActivity {

    private TextView tvHeader, tvScore, tvTimeLeft, tvQuestion;
    private TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
    private CardView cardAnswer1, cardAnswer2, cardAnswer3, cardAnswer4;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private CountDownTimer timer;
    private int timeLeft = 60; // 60 seconds per question
    private boolean isAnswered = false;
    private String mode = MathCategoryActivity.MODE_ADD;
    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_math_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String incoming = getIntent().getStringExtra(MathCategoryActivity.EXTRA_MODE);
        if (incoming != null) {
            mode = incoming;
        }

        initializeViews();
        initializeQuestions();
        displayQuestion();
        startTimer();
    }

    private void initializeViews() {
        tvHeader = findViewById(R.id.tvHeader);
        tvScore = findViewById(R.id.tvScore);
        tvTimeLeft = findViewById(R.id.tvTimeLeft);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvAnswer1 = findViewById(R.id.tvAnswer1);
        tvAnswer2 = findViewById(R.id.tvAnswer2);
        tvAnswer3 = findViewById(R.id.tvAnswer3);
        tvAnswer4 = findViewById(R.id.tvAnswer4);
        cardAnswer1 = findViewById(R.id.cardAnswer1);
        cardAnswer2 = findViewById(R.id.cardAnswer2);
        cardAnswer3 = findViewById(R.id.cardAnswer3);
        cardAnswer4 = findViewById(R.id.cardAnswer4);

        // Update header based on mode
        updateHeader();

        // Set click listeners
        cardAnswer1.setOnClickListener(v -> onAnswerClick(1));
        cardAnswer2.setOnClickListener(v -> onAnswerClick(2));
        cardAnswer3.setOnClickListener(v -> onAnswerClick(3));
        cardAnswer4.setOnClickListener(v -> onAnswerClick(4));
    }

    private void updateHeader() {
        switch (mode) {
            case MathCategoryActivity.MODE_ADD:
                tvHeader.setText("Addition Quiz");
                break;
            case MathCategoryActivity.MODE_SUB:
                tvHeader.setText("Subtraction Quiz");
                break;
            case MathCategoryActivity.MODE_MUL:
                tvHeader.setText("Multiplication Quiz");
                break;
            case MathCategoryActivity.MODE_DIV:
                tvHeader.setText("Division Quiz");
                break;
            case MathCategoryActivity.MODE_BIGGEST:
                tvHeader.setText("Biggest Number Quiz");
                break;
            case MathCategoryActivity.MODE_NEXT:
                tvHeader.setText("Next Number Quiz");
                break;
            case MathCategoryActivity.MODE_BODMAS:
                tvHeader.setText("BODMAS Quiz");
                break;
            default:
                tvHeader.setText("Maths Quiz");
        }
    }

    private void initializeQuestions() {
        questionList = new ArrayList<>();
        
        // Generate 10 questions based on mode
        for (int i = 1; i <= 10; i++) {
            Question question = generateQuestion(i);
            if (question != null) {
                questionList.add(question);
            }
        }

        // Shuffle questions for variety
        Collections.shuffle(questionList);
    }

    private Question generateQuestion(int questionNumber) {
        int min, max;
        if (questionNumber <= 3) {
            min = 10;
            max = 99;
        } else if (questionNumber <= 7) {
            min = 100;
            max = 999;
        } else {
            min = 1000;
            max = 9999;
        }

        String questionText;
        int correctAnswer;
        ArrayList<Integer> options;

        if (mode.equals(MathCategoryActivity.MODE_BIGGEST)) {
            int a = randomInRange(min, max);
            int b = randomInRange(min, max);
            int c = randomInRange(min, max);
            int d = randomInRange(min, max);
            correctAnswer = Math.max(Math.max(a, b), Math.max(c, d));
            questionText = "Which is the biggest number?\n" + a + " , " + b + " , " + c + " , " + d;
            options = new ArrayList<>();
            options.add(a);
            options.add(b);
            options.add(c);
            options.add(d);
            Collections.shuffle(options);

        } else if (mode.equals(MathCategoryActivity.MODE_NEXT)) {
            int start = randomInRange(1, 20);
            int step = randomInRange(2, 10);
            int n1 = start;
            int n2 = start + step;
            int n3 = start + (2 * step);
            int n4 = start + (3 * step);
            correctAnswer = start + (4 * step);
            questionText = "What is the next number?\n" + n1 + ", " + n2 + ", " + n3 + ", " + n4 + ", ?";
            options = buildOptions(correctAnswer, '+');

        } else if (mode.equals(MathCategoryActivity.MODE_BODMAS)) {
            int minBodmas = (questionNumber <= 3) ? 2 : (questionNumber <= 7 ? 5 : 10);
            int maxBodmas = (questionNumber <= 3) ? 12 : (questionNumber <= 7 ? 25 : 60);
            int pattern = random.nextInt(4);
            String expr;
            int a, b, c, d, e;

            if (pattern == 0) {
                a = randomInRange(minBodmas, maxBodmas);
                b = randomInRange(2, 12);
                c = randomInRange(2, 12);
                d = randomInRange(minBodmas, maxBodmas);
                correctAnswer = a + (b * c) - d;
                expr = a + " + " + b + " × " + c + " − " + d;
            } else if (pattern == 1) {
                a = randomInRange(minBodmas, maxBodmas);
                b = randomInRange(minBodmas, maxBodmas);
                c = randomInRange(2, 12);
                correctAnswer = (a + b) * c;
                expr = "(" + a + " + " + b + ") × " + c;
            } else if (pattern == 2) {
                a = randomInRange(2, 15);
                b = randomInRange(2, 15);
                d = randomInRange(2, 12);
                int q = randomInRange(2, 12);
                c = d * q;
                correctAnswer = (a * b) + (c / d);
                expr = "(" + a + " × " + b + ") + (" + c + " ÷ " + d + ")";
            } else {
                a = randomInRange(minBodmas, maxBodmas);
                b = randomInRange(minBodmas, maxBodmas);
                c = randomInRange(2, 10);
                d = randomInRange(2, 20);
                e = randomInRange(2, 10);
                correctAnswer = (a + b) * c - (d * e);
                expr = "(" + a + " + " + b + ") × " + c + " − (" + d + " × " + e + ")";
            }
            questionText = "Solve (BODMAS):\n" + expr + " = ?";
            options = buildOptions(correctAnswer, '+');

        } else {
            // Operator-based question (ADD/SUB/MUL/DIV)
            char op = getOperatorFromMode();
            int a, b;
            String symbol;

            if (op == '/') {
                int divMax = (questionNumber <= 3) ? 12 : (questionNumber <= 7 ? 20 : 30);
                b = randomInRange(2, divMax);
                int res = randomInRange(2, divMax);
                a = b * res;
                correctAnswer = a / b;
                symbol = "÷";
            } else if (op == '-') {
                a = randomInRange(min, max);
                b = randomInRange(min, a);
                correctAnswer = a - b;
                symbol = "−";
            } else if (op == '*') {
                int mulMax = (questionNumber <= 3) ? 12 : (questionNumber <= 7 ? 20 : 30);
                a = randomInRange(2, mulMax);
                b = randomInRange(2, mulMax);
                correctAnswer = a * b;
                symbol = "×";
            } else {
                a = randomInRange(min, max);
                b = randomInRange(min, max);
                correctAnswer = a + b;
                symbol = "+";
            }

            questionText = a + " " + symbol + " " + b + " = ?";
            options = buildOptions(correctAnswer, op);
        }

        // Find which option index has the correct answer
        int correctIndex = options.indexOf(correctAnswer) + 1;

        return new Question(questionText,
                String.valueOf(options.get(0)),
                String.valueOf(options.get(1)),
                String.valueOf(options.get(2)),
                String.valueOf(options.get(3)),
                correctIndex);
    }

    private ArrayList<Integer> buildOptions(int correct, char op) {
        ArrayList<Integer> options = new ArrayList<>();
        options.add(correct);

        HashSet<Integer> used = new HashSet<>();
        used.add(correct);

        while (options.size() < 4) {
            int wrong = generateWrongAnswer(correct, op);
            if (!used.contains(wrong) && wrong > 0) {
                used.add(wrong);
                options.add(wrong);
            }
        }

        Collections.shuffle(options);
        return options;
    }

    private int generateWrongAnswer(int correct, char op) {
        int delta = (op == '*') ? randomInRange(1, 10) : randomInRange(1, 15);
        int sign = random.nextBoolean() ? 1 : -1;
        int wrong = correct + (sign * delta);
        if (wrong < 0) wrong = correct + delta;
        return wrong;
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

    private void displayQuestion() {
        if (currentQuestionIndex >= questionList.size()) {
            finishQuiz();
            return;
        }

        isAnswered = false;
        Question currentQuestion = questionList.get(currentQuestionIndex);
        
        tvQuestion.setText(currentQuestion.getQuestion());
        tvAnswer1.setText(currentQuestion.getOption1());
        tvAnswer2.setText(currentQuestion.getOption2());
        tvAnswer3.setText(currentQuestion.getOption3());
        tvAnswer4.setText(currentQuestion.getOption4());

        // Reset answer card colors
        resetAnswerCards();
        
        // Enable answer cards
        enableAnswerCards();
        
        // Update score display
        updateScore();
    }

    private void resetAnswerCards() {
        cardAnswer1.setCardBackgroundColor(getResources().getColor(R.color.bright_purple_button, null));
        cardAnswer2.setCardBackgroundColor(getResources().getColor(R.color.bright_purple_button, null));
        cardAnswer3.setCardBackgroundColor(getResources().getColor(R.color.bright_purple_button, null));
        cardAnswer4.setCardBackgroundColor(getResources().getColor(R.color.bright_purple_button, null));
    }

    private void onAnswerClick(int selectedAnswer) {
        if (isAnswered) {
            return;
        }

        isAnswered = true;
        Question currentQuestion = questionList.get(currentQuestionIndex);
        int correctAnswer = currentQuestion.getCorrectAnswer();

        // Highlight the selected answer
        CardView selectedCard = getAnswerCard(selectedAnswer);
        CardView correctCard = getAnswerCard(correctAnswer);

        if (selectedAnswer == correctAnswer) {
            // Correct answer - show green
            selectedCard.setCardBackgroundColor(getResources().getColor(R.color.tile_history, null));
            score++;
            updateScore();
        } else {
            // Wrong answer - show red for selected, green for correct
            selectedCard.setCardBackgroundColor(getResources().getColor(R.color.wrong_answer, null));
            correctCard.setCardBackgroundColor(getResources().getColor(R.color.tile_history, null));
        }

        // Disable all answer cards
        disableAnswerCards();

        // Move to next question after a short delay
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentQuestionIndex++;
                if (timer != null) {
                    timer.cancel();
                }
                timeLeft = 60;
                tvTimeLeft.setText("Time: 60s");
                displayQuestion();
                startTimer();
            }
        }, 2000); // 2 second delay
    }

    private CardView getAnswerCard(int answerNumber) {
        switch (answerNumber) {
            case 1: return cardAnswer1;
            case 2: return cardAnswer2;
            case 3: return cardAnswer3;
            case 4: return cardAnswer4;
            default: return cardAnswer1;
        }
    }

    private void disableAnswerCards() {
        cardAnswer1.setClickable(false);
        cardAnswer2.setClickable(false);
        cardAnswer3.setClickable(false);
        cardAnswer4.setClickable(false);
    }

    private void enableAnswerCards() {
        cardAnswer1.setClickable(true);
        cardAnswer2.setClickable(true);
        cardAnswer3.setClickable(true);
        cardAnswer4.setClickable(true);
    }

    private void updateScore() {
        tvScore.setText("Score: " + score);
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }

        timer = new CountDownTimer(timeLeft * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = (int) (millisUntilFinished / 1000);
                tvTimeLeft.setText("Time: " + timeLeft + "s");
            }

            @Override
            public void onFinish() {
                timeLeft = 0;
                tvTimeLeft.setText("Time: 0s");
                // Auto move to next question if time runs out
                if (!isAnswered) {
                    isAnswered = true;
                    Question currentQuestion = questionList.get(currentQuestionIndex);
                    CardView correctCard = getAnswerCard(currentQuestion.getCorrectAnswer());
                    correctCard.setCardBackgroundColor(getResources().getColor(R.color.tile_history, null));
                    disableAnswerCards();
                    
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentQuestionIndex++;
                            timeLeft = 60;
                            tvTimeLeft.setText("Time: 60s");
                            displayQuestion();
                            startTimer();
                        }
                    }, 2000);
                }
            }
        }.start();
    }

    private void finishQuiz() {
        if (timer != null) {
            timer.cancel();
        }
        // Navigate to result screen
        Intent intent = new Intent(MathQuizActivity.this, QuizResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("totalQuestions", questionList.size());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
