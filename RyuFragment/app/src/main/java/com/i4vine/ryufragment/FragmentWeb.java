package com.i4vine.ryufragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

public class FragmentWeb extends Fragment {
 /* //  WebView webView;
    final static String defaultUrl = "http://m.trend.shopping.naver.com/trendpick/index.nhn?tab=true#ap1";//https://swindow.naver.com/style/style/home";
    private Handler handler = new Handler();
*/
    ImageView webImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_web,container,false);

        webImage = (ImageView)rootView.findViewById(R.id.webImage);
        webImage.setImageResource(R.drawable.bg_web);

        touchListener(rootView);
   /*//     webView = (WebView)rootView.findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();*/
     /*   webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);*/
   /*//     webSettings.setJavaScriptEnabled(true);


        webView.setWebViewClient(new WebViewClient());
    //    webView.setWebChromeClient(new WebBrowserClient());

        webView.loadUrl(defaultUrl);*/
        return rootView;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    final class WebBrowserClient extends WebChromeClient{
        public boolean onJsAlert(WebView view, String url, String message, JsResult result){
            result.confirm();

            return true;
        }
    }

    private void touchListener(ViewGroup view){
        view.setOnTouchListener(new ViewGroup.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){
                    Toast.makeText(getActivity(), "Popup WEB",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), PopupWeb.class);
                    startActivity(i);
                }
                return true;//false;
            }
        });
    }

}
