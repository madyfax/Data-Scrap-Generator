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

import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class CountryActivity extends AppCompatActivity {

    Spinner platform;
    EditText keyword;

    TextView scrap;
    CountryCodePicker country;
    Button search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        platform = findViewById(R.id.platfrom);
        country = findViewById(R.id.country);
        keyword = findViewById(R.id.keyword);
        scrap = findViewById(R.id.scrap);
        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String program = platform.getSelectedItem().toString().trim();
                String subject = keyword.getText().toString().trim();
                String con = country.getSelectedCountryCode();
                String lead = scrap.getText().toString();

                if (program.equals("")) {
                    Toast.makeText(CountryActivity.this, "select field", Toast.LENGTH_SHORT).show();
                }else if (con == null) {
                    Toast.makeText(CountryActivity.this, "Please select a country", Toast.LENGTH_SHORT).show();
                }  else if (subject.equals("")) {
                    keyword.setError("Enter proper subject");
                } else {
                    Intent intent = new Intent(CountryActivity.this, PhonenumbersearchActivity.class);
                    intent.putExtra("platform", program);
                    intent.putExtra("country", con);
                    intent.putExtra("scrap", lead);
                    intent.putExtra("keyword", subject);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
