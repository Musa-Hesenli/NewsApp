package com.example.newsapp.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.newsapp.R;

public class ReportBug extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bug);
        Button sendMail = findViewById(R.id.sendMailButton);
        sendMail.setOnClickListener(v -> {
            EditText subject = findViewById(R.id.mailSubject);
            EditText content = findViewById(R.id.mailContent);
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "musahesenli02@gmail.com" });
            email.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
            email.putExtra(Intent.EXTRA_TEXT, content.getText().toString());
            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email, "Choose an Email client :"));
        });
    }
}