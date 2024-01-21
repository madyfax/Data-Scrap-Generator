package com.data.extract.generator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

import im.delight.android.webview.AdvancedWebView;

public class PhonenumbersearchActivity extends AppCompatActivity {

    AdvancedWebView webView;
    ExtendedFloatingActionButton action;

    ArrayList<String> stringArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_search);
        webView = findViewById(R.id.webview);
        action = findViewById(R.id.action);

        String program = getIntent().getStringExtra("platform");
        String con = getIntent().getStringExtra("country");
        String subject = getIntent().getStringExtra("keyword");
        String lead = getIntent().getStringExtra("scrap");

        webView.getSettings().setJavaScriptEnabled(true);
        String url = "https://www.google.com/search?q=" + "site:" + program + ".com " +subject+ " "+lead+ " .+" +con;
        webView.loadUrl(url);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loader loader = new Loader(PhonenumbersearchActivity.this, false);
                loader.show();


                webView.reload();
                webView.evaluateJavascript(
                        "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String html) {

                                html = html.replaceAll("\u003Chtml>", "").replaceAll("\u003C/html>", "");
                                html = html.replaceAll("\\\\u003C", "<");
                                html = html.replaceAll("\\\\", "");
                                Log.d("HTML", html);
                                String finalHtml = html;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        stringArrayList.addAll(extractphonenumber(finalHtml));
                                        HashSet<String> hashSet = new HashSet<String>();
                                        hashSet.addAll(stringArrayList);
                                        stringArrayList.clear();
                                        stringArrayList.addAll(hashSet);
                                        action.setText("Getting: " + "done");
                                        Log.d("TAG", "run: " + stringArrayList.toString());
                                        Intent intent = new Intent(PhonenumbersearchActivity.this, ResultActivity.class);
                                        intent.putStringArrayListExtra("resultList", stringArrayList);
                                        startActivity(intent);
                                        loader.dismiss();
                                    }
                                });
                            }
                        });


            }
        });

    }


    private ArrayList<String> extractphonenumber(String htmlData) {
        ArrayList<String> phonenumber = new ArrayList<>();
        Document doc = Jsoup.parse(htmlData);
        String html = doc.text();
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Iterable<PhoneNumberMatch> matches = phoneNumberUtil.findNumbers(html, Locale.getDefault().getCountry());

        for (PhoneNumberMatch match : matches) {
            String phoneNumber = match.rawString();
            if (phoneNumber.contains("+")) {
                phonenumber.add(phoneNumber);
            }
        }
        return phonenumber;
    }
}
