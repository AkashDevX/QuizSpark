package com.gamev.quizsparkstd.aboutus;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gamev.quizsparkstd.R;

public class AboutUsActivity extends AppCompatActivity {

    // Change this email to your real support email
    private static final String SUPPORT_EMAIL = "connect.quantumsparkstudios@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        EditText etFeedback = findViewById(R.id.etFeedback);
        Button btnSend = findViewById(R.id.btnSendFeedback);

        btnSend.setOnClickListener(v -> {
            int stars = (int) ratingBar.getRating();
            String message = etFeedback.getText().toString().trim();

            if (stars <= 0) {
                Toast.makeText(this, "Please select a rating.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (message.isEmpty()) {
                Toast.makeText(this, "Please write a short feedback message.", Toast.LENGTH_SHORT).show();
                return;
            }

            String subject = "Quiz Spark Feedback - " + stars + "/5 Stars";
            String body =
                    "App: Quiz Spark\n" +
                            "Company: QuantumSpark Studios\n" +
                            "Rating: " + stars + "/5\n\n" +
                            "Feedback:\n" + message + "\n\n" +
                            "Device/App info:\n" +
                            "Package: " + getPackageName();

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + SUPPORT_EMAIL));
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, body);

            try {
                startActivity(Intent.createChooser(intent, "Send Feedback"));
            } catch (Exception e) {
                Toast.makeText(this, "No email app found to send feedback.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
