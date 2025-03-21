package com.example.android_news_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BrowserActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private WebView browser;

    private String url;

    public BrowserActivity() {
    }
    public BrowserActivity(String url) {
        this.url = url;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_browser);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progressBar);
        browser = findViewById(R.id.browser);


        browser.loadUrl(url);

        browser.setWebViewClient(new WebViewClient());

        browser.getSettings().setJavaScriptEnabled(true);

        browser.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);

            }


        });



    }


    @Override
    public void onBackPressed() {
        if (browser.canGoBack()){
            browser.goBack();
        } else {
            super.onBackPressed();
        }
    }
}