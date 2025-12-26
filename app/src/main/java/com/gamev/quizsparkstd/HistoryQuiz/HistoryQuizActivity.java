package com.gamev.quizsparkstd.HistoryQuiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gamev.quizsparkstd.QuestionModels.Question;
import com.gamev.quizsparkstd.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryQuizActivity extends AppCompatActivity {

    private TextView tvHeader, tvScore, tvTimeLeft, tvQuestion;
    private TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
    private CardView cardAnswer1, cardAnswer2, cardAnswer3, cardAnswer4;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private CountDownTimer timer;
    private int timeLeft = 60; // 60 seconds per question
    private boolean isAnswered = false;
    private TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
        tvProgress = findViewById(R.id.tvProgress);


        // Set click listeners
        cardAnswer1.setOnClickListener(v -> onAnswerClick(1));
        cardAnswer2.setOnClickListener(v -> onAnswerClick(2));
        cardAnswer3.setOnClickListener(v -> onAnswerClick(3));
        cardAnswer4.setOnClickListener(v -> onAnswerClick(4));
    }

    private void initializeQuestions() {
        questionList = new ArrayList<>();
        
        // Add History Quiz Questions
        questionList.add(new Question(
                "In which year did World War II end?",
                "1943",
                "1944",
                "1945",
                "1946",
                3
        ));
        
        questionList.add(new Question(
                "Who was the first President of the United States?",
                "Thomas Jefferson",
                "George Washington",
                "John Adams",
                "Benjamin Franklin",
                2
        ));
        
        questionList.add(new Question(
                "The French Revolution began in which year?",
                "1787",
                "1788",
                "1789",
                "1790",
                3
        ));
        
        questionList.add(new Question(
                "Which ancient civilization built the Great Pyramid of Giza?",
                "Romans",
                "Greeks",
                "Egyptians",
                "Babylonians",
                3
        ));
        
        questionList.add(new Question(
                "The Berlin Wall fell in which year?",
                "1987",
                "1988",
                "1989",
                "1990",
                3
        ));
        
        questionList.add(new Question(
                "Who wrote 'The Art of War'?",
                "Confucius",
                "Sun Tzu",
                "Lao Tzu",
                "Mencius",
                2
        ));
        
        questionList.add(new Question(
                "The Industrial Revolution started in which country?",
                "France",
                "Germany",
                "United States",
                "United Kingdom",
                4
        ));
        
        questionList.add(new Question(
                "Which empire was ruled by Julius Caesar?",
                "Greek Empire",
                "Roman Empire",
                "Byzantine Empire",
                "Ottoman Empire",
                2
        ));
        
        questionList.add(new Question(
                "The Renaissance period began in which country?",
                "France",
                "Italy",
                "Spain",
                "Germany",
                2
        ));
        
        questionList.add(new Question(
                "Who was known as the 'Iron Lady'?",
                "Indira Gandhi",
                "Margaret Thatcher",
                "Golda Meir",
                "Angela Merkel",
                2
        ));

        // Shuffle questions for variety
        Collections.shuffle(questionList);
    }
    private void updateProgress(int current, int total) { tvProgress.setText("Q " + current + "/" + total); }
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
        updateProgress(currentQuestionIndex + 1, questionList.size());
    }

    private void resetAnswerCards() {
        cardAnswer1.setCardBackgroundColor(getResources().getColor(R.color.primary_blue, null));
        cardAnswer2.setCardBackgroundColor(getResources().getColor(R.color.primary_blue, null));
        cardAnswer3.setCardBackgroundColor(getResources().getColor(R.color.primary_blue, null));
        cardAnswer4.setCardBackgroundColor(getResources().getColor(R.color.primary_blue, null));
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
        Intent intent = new Intent(HistoryQuizActivity.this, QuizResultActivity.class);
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
