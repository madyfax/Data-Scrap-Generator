package com.data.extract.generator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EmailActivity extends AppCompatActivity {

    Spinner platform;
    TextView scrap;
    EditText keyword;
    Button search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        platform = findViewById(R.id.platfrom);
        scrap = findViewById(R.id.scrap);
        keyword = findViewById(R.id.keyword);
        search = findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String program = platform.getSelectedItem().toString().trim();
                String lead = scrap.getText().toString().trim();
                String subject = keyword.getText().toString().trim();
                if (program.equals("")) {
                    Toast.makeText(EmailActivity.this, "select field", Toast.LENGTH_SHORT).show();
                } else if (lead.equals("")) {
                    Toast.makeText(EmailActivity.this, "Select field", Toast.LENGTH_SHORT).show();
                } else if (subject.equals("")) {
                    keyword.setError("Enter proper subject");
                } else {
                    Intent intent = new Intent(EmailActivity.this, EmailSearchActivity.class);
                    intent.putExtra("platform", program);
                    intent.putExtra("scrap", lead);
                    intent.putExtra("keyword", subject);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
