package com.gamev.quizsparkstd.HistoryQuiz;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gamev.quizsparkstd.MainScreen.MainScreenActivity;
import com.gamev.quizsparkstd.R;

public class QuizResultActivity extends AppCompatActivity {

    private TextView tvFinalScore, tvMessage, tvGreatKnowledge;
    private LinearLayout llCelebration;
    private TextView tvEmoji1, tvEmoji2, tvEmoji3;
    private CardView cardScore, cardBackButton;

    private int score;
    private int totalQuestions;
    private boolean isHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get score from intent
        score = getIntent().getIntExtra("score", 0);
        totalQuestions = getIntent().getIntExtra("totalQuestions", 10);
        isHighScore = score > (totalQuestions * 3 / 4);

        initializeViews();
        displayScore();
        startAnimations();
    }

    private void initializeViews() {
        tvFinalScore = findViewById(R.id.tvFinalScore);
        tvMessage = findViewById(R.id.tvMessage);
        tvGreatKnowledge = findViewById(R.id.tvGreatKnowledge);
        llCelebration = findViewById(R.id.llCelebration);
        tvEmoji1 = findViewById(R.id.tvEmoji1);
        tvEmoji2 = findViewById(R.id.tvEmoji2);
        tvEmoji3 = findViewById(R.id.tvEmoji3);
        cardScore = findViewById(R.id.cardScore);
        cardBackButton = findViewById(R.id.cardBackButton);

        // Set click listener for back button
        cardBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizResultActivity.this, MainScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void displayScore() {
        tvFinalScore.setText(score + "/" + totalQuestions);

        if (isHighScore) {
            tvMessage.setText("Well Done!");
            tvGreatKnowledge.setVisibility(View.VISIBLE);
            llCelebration.setVisibility(View.VISIBLE);
        } else {
            tvMessage.setText("Good Try!");
            tvGreatKnowledge.setVisibility(View.GONE);
            llCelebration.setVisibility(View.GONE);
        }
    }

    private void startAnimations() {
        // Start with score card animation
        animateScoreCard();
    }

    private void animateScoreCard() {
        // Scale and fade in animation for score card
        cardScore.setAlpha(0f);
        cardScore.setScaleX(0.5f);
        cardScore.setScaleY(0.5f);

        AnimatorSet scoreAnimator = new AnimatorSet();
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(cardScore, "alpha", 0f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(cardScore, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(cardScore, "scaleY", 0.5f, 1f);

        scoreAnimator.playTogether(fadeIn, scaleX, scaleY);
        scoreAnimator.setDuration(800);
        scoreAnimator.setInterpolator(new DecelerateInterpolator());
        scoreAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                animateMessage();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        scoreAnimator.start();
    }

    private void animateMessage() {
        tvMessage.setAlpha(0f);
        tvMessage.setTranslationY(-50f);
        tvMessage.setVisibility(View.VISIBLE);

        AnimatorSet messageAnimator = new AnimatorSet();
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(tvMessage, "alpha", 0f, 1f);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(tvMessage, "translationY", -50f, 0f);

        messageAnimator.playTogether(fadeIn, translateY);
        messageAnimator.setDuration(600);
        messageAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        messageAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isHighScore) {
                    animateGreatKnowledge();
                } else {
                    animateBackButton();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        messageAnimator.start();
    }

    private void animateGreatKnowledge() {
        tvGreatKnowledge.setAlpha(0f);
        tvGreatKnowledge.setScaleX(0.8f);
        tvGreatKnowledge.setScaleY(0.8f);
        tvGreatKnowledge.setVisibility(View.VISIBLE);

        AnimatorSet greatKnowledgeAnimator = new AnimatorSet();
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(tvGreatKnowledge, "alpha", 0f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvGreatKnowledge, "scaleX", 0.8f, 1.1f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(tvGreatKnowledge, "scaleY", 0.8f, 1.1f, 1f);

        greatKnowledgeAnimator.playTogether(fadeIn, scaleX, scaleY);
        greatKnowledgeAnimator.setDuration(800);
        greatKnowledgeAnimator.setInterpolator(new BounceInterpolator());
        greatKnowledgeAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                animateCelebrationEmojis();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        greatKnowledgeAnimator.start();
    }

    private void animateCelebrationEmojis() {
        // Animate emojis one by one with bounce effect
        animateEmoji(tvEmoji1, 0);
        animateEmoji(tvEmoji2, 200);
        animateEmoji(tvEmoji3, 400);
        
        // After emojis, animate back button
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateBackButton();
            }
        }, 800);
    }

    private void animateEmoji(TextView emoji, long delay) {
        emoji.setAlpha(0f);
        emoji.setScaleX(0f);
        emoji.setScaleY(0f);
        emoji.setRotation(0f);
        emoji.setVisibility(View.VISIBLE);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimatorSet emojiAnimator = new AnimatorSet();
                ObjectAnimator fadeIn = ObjectAnimator.ofFloat(emoji, "alpha", 0f, 1f);
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(emoji, "scaleX", 0f, 1.5f, 1f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(emoji, "scaleY", 0f, 1.5f, 1f);
                ObjectAnimator rotate = ObjectAnimator.ofFloat(emoji, "rotation", 0f, 360f);

                emojiAnimator.playTogether(fadeIn, scaleX, scaleY, rotate);
                emojiAnimator.setDuration(600);
                emojiAnimator.setInterpolator(new BounceInterpolator());
                emojiAnimator.start();

                // Continuous rotation animation
                ObjectAnimator continuousRotate = ObjectAnimator.ofFloat(emoji, "rotation", 0f, 360f);
                continuousRotate.setDuration(2000);
                continuousRotate.setRepeatCount(ObjectAnimator.INFINITE);
                continuousRotate.setInterpolator(new AccelerateDecelerateInterpolator());
                continuousRotate.start();
            }
        }, delay);
    }

    private void animateBackButton() {
        cardBackButton.setAlpha(0f);
        cardBackButton.setTranslationY(100f);
        cardBackButton.setVisibility(View.VISIBLE);

        AnimatorSet buttonAnimator = new AnimatorSet();
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(cardBackButton, "alpha", 0f, 1f);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(cardBackButton, "translationY", 100f, 0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(cardBackButton, "scaleX", 0.9f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(cardBackButton, "scaleY", 0.9f, 1f);

        buttonAnimator.playTogether(fadeIn, translateY, scaleX, scaleY);
        buttonAnimator.setDuration(600);
        buttonAnimator.setInterpolator(new DecelerateInterpolator());
        buttonAnimator.start();
    }
}

