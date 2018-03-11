package com.example.liu.eparty.activity.news;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;

public class NewsActivity extends BaseActivity {

    @Override
    protected String setTitle() {
        return "新闻";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    protected void init() {
        String url = getIntent().getStringExtra("newsLink");
        WebView webview = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webview.getSettings();
        webSettings.setSupportZoom(true);//支持缩放
        webview.setWebViewClient(new webViewClient());
        webview.loadUrl(url);
    }

    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
