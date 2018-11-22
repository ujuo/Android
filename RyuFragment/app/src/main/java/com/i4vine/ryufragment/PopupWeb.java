package com.i4vine.ryufragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import java.util.Locale;

public class PopupWeb extends Activity {
    SharePreferenceUtil share_web;
    WebView webView;
    ImageView popup_web_close;
    static String defaultUrl = "https://www.amazon.com/Womens-Fashion/b/?ie=UTF8&node=7147440011&ref_=sv_sl_1";
    //http://m.trend.shopping.naver.com/trendpick/index.nhn?tab=true#ap1";//https://swindow.naver.com/style/style/home";
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;

        getWindow().setAttributes(layoutParams);

        // Hide navigation bar
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOption = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)==uiOptions);
        newUiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOption);
        // Hide Status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.popup_web);
        webView = (WebView)findViewById(R.id.webView);
        share_web = new SharePreferenceUtil(getApplicationContext());

        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
     /*   webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webSettings.setUseWideViewPort(true);*/
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);

        popup_web_close = (ImageView)findViewById(R.id.popup_web_close);
        popup_web_close.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    finish();
                }
                return true;//false;
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
               // return super.shouldOverrideUrlLoading(view, url);
            }
        });
     //   webView.setWebChromeClient(new WebBrowserClient());

        String lan = share_web.getSharedLanguage();
        if(lan.compareTo("ko") == 0){
            defaultUrl = "http://m.trend.shopping.naver.com/trendpick/index.nhn?tab=true#ap1";//https://swindow.naver.com/style/style/home";;
        }else{
            defaultUrl = "https://www.amazon.com/Womens-Fashion/b/?ie=UTF8&node=7147440011&ref_=sv_sl_1";
        }
        webView.loadUrl(defaultUrl);

   //     popup_web = (ImageView) findViewById(R.id.popup_web);
   //     popup_web.setImageResource(R.drawable.img_amazon);
    }
}
