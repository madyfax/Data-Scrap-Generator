package com.data.extract.generator;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<String> resultList;
    ExtendedFloatingActionButton save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultactivity);
        rv = findViewById(R.id.rv);
        save = findViewById(R.id.save);
        resultList = getIntent().getStringArrayListExtra("resultList");
        if (resultList == null || resultList.isEmpty()) {
            Toast.makeText(this, "No results to export", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            rv.setLayoutManager(new LinearLayoutManager(ResultActivity.this));
            ResultAdapter adapter = new ResultAdapter(resultList, ResultActivity.this);
            rv.setAdapter(adapter);


            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestStoragePermission();


                }
            });
        }
    }

    private static final int REQUEST_MANAGE_STORAGE_PERMISSION = 1;

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, REQUEST_MANAGE_STORAGE_PERMISSION);
            } else {
                startDownload();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_MANAGE_STORAGE_PERMISSION);
            } else {
                startDownload();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_MANAGE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                startDownload();
            } else {
                // Permission denied
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startDownload() {
        exportcontacts();
    }


    void exportcontacts() {
        StringBuffer stringBuffer = new StringBuffer();

        if (resultList != null) {
            for (int i = 0; i < resultList.size(); i++) {

                stringBuffer.append(resultList.get(i) + "\n");

            }
        }

        createFile(stringBuffer.toString());
    }


    public void createFile(String name) {

        File file;
        File fileDir = new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()) + File.separator + getString(R.string.app_name));
        if (!fileDir.exists()) {
            try {
                fileDir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        file = new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()) + File.separator + getString(R.string.app_name) + File.separator + "EMAIL_" + System.currentTimeMillis() + ".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        if (file.exists()) {
            try {
                System.out.println("contacts no =" + name);
                BufferedWriter bfWriter = new BufferedWriter(new FileWriter(file));
                bfWriter.write(name);
                bfWriter.close();
                Toast.makeText(ResultActivity.this, "Suucesssfully Export", Toast.LENGTH_LONG).show();

            } catch (IOException e22) {
                e22.printStackTrace();
            }
        }
    }
}
