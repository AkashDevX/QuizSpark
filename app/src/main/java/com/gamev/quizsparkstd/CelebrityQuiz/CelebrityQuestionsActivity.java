package com.gamev.quizsparkstd.CelebrityQuiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gamev.quizsparkstd.HistoryQuiz.QuizResultActivity;
import com.gamev.quizsparkstd.QuestionModels.CelebrityQuestion;
import com.gamev.quizsparkstd.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CelebrityQuestionsActivity extends AppCompatActivity {

    private TextView tvHeader, tvScore, tvTimeLeft, tvQuestion;
    private TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
    private ImageView ivCelebrity;
    private CardView cardCelebrityImage;
    private CardView cardAnswer1, cardAnswer2, cardAnswer3, cardAnswer4;

    private List<CelebrityQuestion> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private CountDownTimer timer;
    private int timeLeft = 60; // 60 seconds per question
    private boolean isAnswered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_celebrity_questions);
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
        ivCelebrity = findViewById(R.id.ivCelebrity);
        cardCelebrityImage = findViewById(R.id.cardCelebrityImage);
        tvAnswer1 = findViewById(R.id.tvAnswer1);
        tvAnswer2 = findViewById(R.id.tvAnswer2);
        tvAnswer3 = findViewById(R.id.tvAnswer3);
        tvAnswer4 = findViewById(R.id.tvAnswer4);
        cardAnswer1 = findViewById(R.id.cardAnswer1);
        cardAnswer2 = findViewById(R.id.cardAnswer2);
        cardAnswer3 = findViewById(R.id.cardAnswer3);
        cardAnswer4 = findViewById(R.id.cardAnswer4);

        // Set click listeners
        cardAnswer1.setOnClickListener(v -> onAnswerClick(1));
        cardAnswer2.setOnClickListener(v -> onAnswerClick(2));
        cardAnswer3.setOnClickListener(v -> onAnswerClick(3));
        cardAnswer4.setOnClickListener(v -> onAnswerClick(4));
    }

    private void initializeQuestions() {
        questionList = new ArrayList<>();
        
        // Questions with images - using assets folder (real celebrity images)
        questionList.add(new CelebrityQuestion(
                "Who is this celebrity?",
                "Tom Hanks",
                "Brad Pitt",
                "Leonardo DiCaprio",
                "Johnny Depp",
                1,
                "celebrity_images/tom_hanks.jpg"
        ));
        
        questionList.add(new CelebrityQuestion(
                "Identify this famous actor.",
                "Robert Downey Jr.",
                "Chris Evans",
                "Chris Hemsworth",
                "Mark Ruffalo",
                1,
                "celebrity_images/robert_downey_jr.jpg"
        ));
        
        // Questions without images
        questionList.add(new CelebrityQuestion(
                "Which actress won an Oscar for her role in 'La La Land'?",
                "Emma Stone",
                "Jennifer Lawrence",
                "Natalie Portman",
                "Meryl Streep",
                1,
                null // No image
        ));
        
        questionList.add(new CelebrityQuestion(
                "Who is this celebrity?",
                "Beyoncé",
                "Rihanna",
                "Taylor Swift",
                "Ariana Grande",
                1,
                "celebrity_images/beyonce.jpg"
        ));
        
        questionList.add(new CelebrityQuestion(
                "Which singer is known as the 'Queen of Pop'?",
                "Madonna",
                "Britney Spears",
                "Lady Gaga",
                "Katy Perry",
                1,
                null // No image
        ));
        
        questionList.add(new CelebrityQuestion(
                "Which actor played Iron Man in the Marvel Cinematic Universe?",
                "Chris Evans",
                "Robert Downey Jr.",
                "Chris Hemsworth",
                "Tom Holland",
                2,
                null // No image
        ));
        
        questionList.add(new CelebrityQuestion(
                "Which actress starred in 'The Devil Wears Prada'?",
                "Anne Hathaway",
                "Meryl Streep",
                "Emily Blunt",
                "All of the above",
                4,
                null // No image
        ));
        
        questionList.add(new CelebrityQuestion(
                "Identify this famous musician.",
                "Elvis Presley",
                "Michael Jackson",
                "Freddie Mercury",
                "David Bowie",
                2,
                "celebrity_images/micheal_jackson_.jpg"
        ));
        
        questionList.add(new CelebrityQuestion(
                "Who is this celebrity?",
                "Oprah Winfrey",
                "Ellen DeGeneres",
                "Michelle Obama",
                "Serena Williams",
                1,
                "celebrity_images/oprah_winfrey_.jpg"
        ));
        
        questionList.add(new CelebrityQuestion(
                "Identify this famous athlete.",
                "Cristiano Ronaldo",
                "Lionel Messi",
                "LeBron James",
                "Serena Williams",
                3,
                "celebrity_images/lebron_james_.jpg"

        ));

        // Shuffle questions for variety
        Collections.shuffle(questionList);
    }

    private void displayQuestion() {
        if (currentQuestionIndex >= questionList.size()) {
            finishQuiz();
            return;
        }

        isAnswered = false;
        CelebrityQuestion currentQuestion = questionList.get(currentQuestionIndex);
        
        tvQuestion.setText(currentQuestion.getQuestion());
        tvAnswer1.setText(currentQuestion.getOption1());
        tvAnswer2.setText(currentQuestion.getOption2());
        tvAnswer3.setText(currentQuestion.getOption3());
        tvAnswer4.setText(currentQuestion.getOption4());

        // Show or hide celebrity image
        if (currentQuestion.hasImage()) {
            Log.d("CelebrityQuiz", "Question has image. isResourceImage: " + currentQuestion.isResourceImage());
            if (currentQuestion.isResourceImage()) {
                // Load from drawable resources
                Log.d("CelebrityQuiz", "Loading from resource: " + currentQuestion.getImageResourceId());
                ivCelebrity.setImageResource(currentQuestion.getImageResourceId());
                ivCelebrity.setVisibility(View.VISIBLE);
            } else {
                // Load from assets folder
                Log.d("CelebrityQuiz", "Loading from assets: " + currentQuestion.getImageFileName());
                loadImageFromAssets(currentQuestion.getImageFileName());
            }
            // Make sure the card is visible
            cardCelebrityImage.setVisibility(View.VISIBLE);
            // Request layout update to ensure proper display
            cardCelebrityImage.requestLayout();
            cardCelebrityImage.invalidate();
            Log.d("CelebrityQuiz", "Image container visibility set to VISIBLE");
        } else {
            // Clear image and hide container
            Log.d("CelebrityQuiz", "Question has no image, hiding container");
            ivCelebrity.setImageDrawable(null);
            ivCelebrity.setVisibility(View.GONE);
            cardCelebrityImage.setVisibility(View.GONE);
        }

        // Reset answer card colors
        resetAnswerCards();
        
        // Enable answer cards
        enableAnswerCards();
        
        // Update score display
        updateScore();
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
        CelebrityQuestion currentQuestion = questionList.get(currentQuestionIndex);
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

    private void loadImageFromAssets(String fileName) {
        try {
            Log.d("CelebrityQuiz", "Attempting to load image: " + fileName);
            // Get input stream from assets
            InputStream ims = getAssets().open(fileName);
            
            // Use BitmapFactory for more reliable loading
            Bitmap bitmap = BitmapFactory.decodeStream(ims);
            ims.close();
            
            if (bitmap != null) {
                // Set the bitmap to the ImageView
                ivCelebrity.setImageBitmap(bitmap);
                // Make sure ImageView is visible
                ivCelebrity.setVisibility(View.VISIBLE);
                Log.d("CelebrityQuiz", "Image loaded successfully: " + fileName + ", Size: " + bitmap.getWidth() + "x" + bitmap.getHeight());
            } else {
                Log.e("CelebrityQuiz", "Bitmap is null for: " + fileName);
                ivCelebrity.setImageDrawable(null);
                cardCelebrityImage.setVisibility(View.GONE);
            }
        } catch (IOException ex) {
            Log.e("CelebrityQuiz", "Error loading image from assets: " + fileName, ex);
            Log.e("CelebrityQuiz", "Exception details: " + ex.getMessage());
            // If image fails to load, hide the image container
            ivCelebrity.setImageDrawable(null);
            cardCelebrityImage.setVisibility(View.GONE);
        } catch (Exception ex) {
            Log.e("CelebrityQuiz", "Unexpected error loading image: " + fileName, ex);
            ivCelebrity.setImageDrawable(null);
            cardCelebrityImage.setVisibility(View.GONE);
        }
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
                    CelebrityQuestion currentQuestion = questionList.get(currentQuestionIndex);
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
        Intent intent = new Intent(CelebrityQuestionsActivity.this, QuizResultActivity.class);
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
