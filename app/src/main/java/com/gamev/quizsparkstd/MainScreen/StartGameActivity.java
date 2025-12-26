package com.gamev.quizsparkstd.MainScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gamev.quizsparkstd.R;

public class StartGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        findViewById(R.id.btnStartGame).setOnClickListener(v -> {
            startActivity(new Intent(this, MainScreenActivity.class));
            finish();
        });
    }
}
