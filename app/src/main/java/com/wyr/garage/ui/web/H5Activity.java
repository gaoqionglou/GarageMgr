package com.wyr.garage.ui.web;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.wyr.garage.databinding.ActivityH5Binding;

public class H5Activity extends AppCompatActivity {

    private String mUrl = "";
    private String mTitle = "";

    private ActivityH5Binding activityH5Binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityH5Binding = ActivityH5Binding.inflate(LayoutInflater.from(this));

        setContentView(activityH5Binding.getRoot());
        ActionBar actionBar = getSupportActionBar();

        initWebView();

        mUrl = getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("title");

        if (actionBar != null) {
            actionBar.setTitle(mTitle);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Router.loadPage(activityH5Binding.webView, mUrl);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initWebView() {
        new WebViewInitializer().setUpWebView(activityH5Binding.webView);
        activityH5Binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                view.loadUrl();
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityH5Binding.webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityH5Binding.webView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityH5Binding.webView.removeAllViews();
        activityH5Binding.webView.destroy();
    }

}
