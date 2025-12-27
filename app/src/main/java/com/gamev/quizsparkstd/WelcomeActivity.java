package com.gamev.quizsparkstd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gamev.quizsparkstd.MainScreen.MainScreenActivity;
import com.gamev.quizsparkstd.aboutus.AboutUsActivity;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnPlayNow, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnPlayNow = findViewById(R.id.btnPlayNow);
        btnAbout = findViewById(R.id.btnAbout);

        btnPlayNow.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, MainScreenActivity.class);
            startActivity(intent);
        });

        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, AboutUsActivity.class);
            startActivity(intent);
        });
    }
}

