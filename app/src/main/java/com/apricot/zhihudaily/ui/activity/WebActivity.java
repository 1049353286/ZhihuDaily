package com.apricot.zhihudaily.ui.activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.daimajia.numberprogressbar.NumberProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.apricot.zhihudaily.R;

/**
 * Created by Apricot on 2016/7/6.
 */
public class WebActivity extends BaseActivity{
    private static final String EXTRA_URL = "extra_url";
    private static final String EXTRA_TITLE = "extra_title";
    @Bind(R.id.progressbar)
    NumberProgressBar numberProgressBar;
    @Bind(R.id.tv_title)
    TextSwitcher textSwitcher;
    @Bind(R.id.web_view)
    WebView webView;

    private String mTitle,mUrl;



    public static void StartWebActivity(Context context,String extraUrl,String extraTitle){
        Intent intent=new Intent(context,WebActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_TITLE,extraTitle);
        intent.putExtra(EXTRA_URL,extraUrl);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        layoutResId=R.layout.activity_web;
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitle=getIntent().getStringExtra(EXTRA_TITLE);
        mUrl=getIntent().getStringExtra(EXTRA_URL);
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.setWebChromeClient(new ChromeClient());
        webView.setWebViewClient(new WebClient());
        webView.loadUrl(mUrl);

        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                final TextView textView=new TextView(WebActivity.this);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.setTextSize(18);
                textView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setSelected(true);
                    }
                },1000);
                return textView;
            }
        });
        textSwitcher.setInAnimation(this, android.R.anim.fade_in);
        textSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        if(mTitle!=null){
            setTitle(mTitle);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        textSwitcher.setText(title);
    }

    private class ChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            numberProgressBar.setProgress(newProgress);
            if(newProgress==100){
                numberProgressBar.setVisibility(View.GONE);
            }else{
                numberProgressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    }

    private class WebClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url!=null){
                view.loadUrl(url);
            }
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN){
            switch (keyCode){
                case KeyEvent.KEYCODE_BACK:
                    if(webView.canGoBack()){
                        webView.goBack();
                    }else{
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if(webView!=null){
            webView.destroy();
        }
    }
}
