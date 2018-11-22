package com.i4vine.ryufragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

public class Intro extends Activity {
    Handler handler = new Handler();
    Runnable r = new Runnable(){
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), Standby.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOption = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        newUiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOption);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.intro);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(r,2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(r);
    }
}
