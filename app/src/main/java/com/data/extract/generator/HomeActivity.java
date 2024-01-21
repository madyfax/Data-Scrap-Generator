package com.data.extract.generator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void email(View view) {
       startActivity(new Intent(this, EmailActivity.class));
    }

    public void number(View view) {
        startActivity(new Intent(this,CountryActivity.class));
    }

}



