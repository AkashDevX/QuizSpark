package com.gamev.quizsparkstd.MainScreen;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.gamev.quizsparkstd.R;

public class StartGameActivity extends AppCompatActivity {

    private AnimatorSet sparkSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        findViewById(R.id.btnStartGame).setOnClickListener(v -> {
            startActivity(new Intent(this, MainScreenActivity.class));
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        startSparkAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopSparkAnimation();
    }

    private void startSparkAnimation() {
        ImageView ivSpark = findViewById(R.id.ivSpark);
        if (ivSpark == null) return;

        // Float (smooth up-down)
        ObjectAnimator floatAnim = ObjectAnimator.ofFloat(ivSpark, "translationY", 0f, -14f, 0f);
        floatAnim.setDuration(2400);
        floatAnim.setRepeatCount(ValueAnimator.INFINITE);
        floatAnim.setInterpolator(new AccelerateDecelerateInterpolator());

        // Pulse (scale)
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivSpark, "scaleX", 1f, 1.12f, 1f);
        scaleX.setDuration(1800);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(ivSpark, "scaleY", 1f, 1.12f, 1f);
        scaleY.setDuration(1800);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        scaleY.setInterpolator(new AccelerateDecelerateInterpolator());

        // Glow (alpha breathing)
        ObjectAnimator alpha = ObjectAnimator.ofFloat(ivSpark, "alpha", 0.82f, 1f, 0.82f);
        alpha.setDuration(1800);
        alpha.setRepeatCount(ValueAnimator.INFINITE);

        // Gentle rotate (very subtle premium feel)
        ObjectAnimator rotate = ObjectAnimator.ofFloat(ivSpark, "rotation", 0f, 360f);
        rotate.setDuration(9000);
        rotate.setRepeatCount(ValueAnimator.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());

        sparkSet = new AnimatorSet();
        sparkSet.playTogether(floatAnim, scaleX, scaleY, alpha, rotate);
        sparkSet.start();
    }

    private void stopSparkAnimation() {
        if (sparkSet != null) {
            sparkSet.cancel();
            sparkSet = null;
        }
    }
}
