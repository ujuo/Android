package com.i4vine.ryufragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class PopupSetting extends Activity {
    TextView local1;
    TextView local2;
    TextView local3;
    TextView local4;
    TextView local5;
    TextView local6;
    TextView local7;
    TextView local8;
    TextView local9;
    TextView local10;
    TextView local11;
    TextView local12;
    TextView local13;
    TextView local14;
    TextView local15;
    TextView local16;
    TextView local17;
    TextView local18;
    TextView local19;
    TextView local20;
    TextView local21;
    TextView local22;
    TextView local23;
    TextView local24;
    TextView local25;
    TextView local26;
    TextView local27;
    TextView local28;
    TextView lan_ko;
    TextView lan_en;
    double set_lat;
    double set_lot;
    String local_str_ko;
    String local_str_en;
    SharePreferenceUtil share_setting;
    ImageView popup_setting_close;

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


        setContentView(R.layout.popup_setting);
        share_setting = new SharePreferenceUtil(getApplicationContext());

        local1 = (TextView)findViewById(R.id.local1);
        local2 = (TextView)findViewById(R.id.local2);
        local3 = (TextView)findViewById(R.id.local3);
        local4 = (TextView)findViewById(R.id.local4);
        local5 = (TextView)findViewById(R.id.local5);
        local6 = (TextView)findViewById(R.id.local6);
        local7 = (TextView)findViewById(R.id.local7);
        local8 = (TextView)findViewById(R.id.local8);
        local9 = (TextView)findViewById(R.id.local9);
        local10 = (TextView)findViewById(R.id.local10);
        local11 = (TextView)findViewById(R.id.local11);
        local12 = (TextView)findViewById(R.id.local12);
        local13 = (TextView)findViewById(R.id.local13);
        local14 = (TextView)findViewById(R.id.local14);
        local15 = (TextView)findViewById(R.id.local15);
        local16 = (TextView)findViewById(R.id.local16);
        local17 = (TextView)findViewById(R.id.local17);
        local18 = (TextView)findViewById(R.id.local18);
        local19 = (TextView)findViewById(R.id.local19);
        local20 = (TextView)findViewById(R.id.local20);
        local21 = (TextView)findViewById(R.id.local21);
        local22 = (TextView)findViewById(R.id.local22);
        local23 = (TextView)findViewById(R.id.local23);
        local24 = (TextView)findViewById(R.id.local24);
        local25 = (TextView)findViewById(R.id.local25);
        local26 = (TextView)findViewById(R.id.local26);
        local27 = (TextView)findViewById(R.id.local27);
        local28 = (TextView)findViewById(R.id.local28);

        lan_en = (TextView)findViewById(R.id.lan_en);
        lan_ko = (TextView)findViewById(R.id.lan_ko);
        popup_setting_close = (ImageView) findViewById(R.id.popup_setting_close);

        popup_setting_close.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    finish();
                }
                return true;//false;
            }
        });

        lan_en.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                   // share_setting.setSharedLanguage("en");
                    Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                 //   startActivityForResult(intent, 0);
                    startActivity(intent);
                  //  finish();*
               /*     Configuration config = getBaseContext().getResources().getConfiguration();
                    Locale locale = new Locale("en");
                    Locale.setDefault(locale);
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    share_setting.setSharedLanguage("en");
                    recreate();*/
                }
                return true;//false;
            }
        });

        lan_ko.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                 //   share_setting.setSharedLanguage("ko");
                    Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                   // startActivityForResult(intent, 0);
                    startActivity(intent);
                 //   finish();
           /*         Configuration config = getBaseContext().getResources().getConfiguration();
                    Locale locale = new Locale("ko");
                    Locale.setDefault(locale);
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    share_setting.setSharedLanguage("ko");
                    recreate();*/
                }
                return true;//false;
            }
        });

        for(int i=0; i<28; i++) {
            local_touch_event(i);
        }


    }

    public void local_touch_event(final int idx){
        final TextView setting_text[] = {local1,local2, local3, local4, local5, local6, local7, local8, local9, local10,
                           local11, local12, local13, local14, local15, local16, local17, local18, local19, local20,
                           local21, local22, local23, local24, local25, local26, local27,local28};

        setting_text[idx].setBackgroundColor(0xFFFFFFFF);
        setting_text[idx].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    switch (idx+1) {
                        case 1:
                            set_lat = 37.496978;
                            set_lot = 127.055923;
                            local_str_ko = "강남구 / 서울";
                            local_str_en = "Gangnam-gu / Seoul";


                            break;

                        case 2:
                            set_lat = 37.551740;
                            set_lot = 127.143510;
                            local_str_ko = "강동구 / 서울";
                            local_str_en = "Gangdong-gu / Seoul";
                            break;


                        case 3:
                            set_lat = 37.633795;
                            set_lot = 127.018288;
                            local_str_ko = "강북구 / 서울";
                            local_str_en = "Gangbuk-gu / Seoul";
                            break;

                        case 4:
                            set_lat = 37.563607;
                            set_lot = 126.814311;
                            local_str_ko = "강서구 / 서울";
                            local_str_en = "Gangseo-gu / Seoul";
                            break;

                        case 5:
                            set_lat = 37.474845;
                            set_lot = 126.940475;
                            local_str_ko = "관악구 / 서울";
                            local_str_en = "Gwanak-gu / Seoul";
                            break;

                        case 6:
                            set_lat = 37.546270;
                            set_lot= 127.068318;
                            local_str_ko = "광진구 / 서울";
                            local_str_en = "Gwangjin-gu / Seoul";
                            break;

                        case 7:
                            set_lat= 37.503105;
                            set_lot= 126.881948;
                            local_str_ko = "구로구 / 서울";
                            local_str_en = "Gurogu-gu / Seoul";
                            break;

                        case 8:
                            set_lat = 37.464484;
                            set_lot = 126.899637;
                            local_str_ko = "금천구 / 서울";
                            local_str_en = "Geumcheon-gu / Seoul";
                            break;

                        case 9:
                            set_lat = 37.646847;
                            set_lot = 127.075764;
                            local_str_ko = "노원구 / 서울";
                            local_str_en = "Nowon-gu / Seoul";
                            break;

                        case 10:
                            set_lat = 37.661465;
                            set_lot = 127.034162;
                            local_str_ko = "도봉구 / 서울";
                            local_str_en = "Dobong-gu / Seoul";
                            break;

                        case 11:
                            set_lat = 37.582620;
                            set_lot = 127.054758;
                            local_str_ko = "동대문구 / 서울";
                            local_str_en = "Dongdaemun-gu / Seoul";
                            break;

                        case 12:
                            set_lat = 37.504453;
                            set_lot = 126.952622;
                            local_str_ko = "동작구 / 서울";
                            local_str_en = "Dongjak-gu / Seoul";
                            break;

                        case 13:
                            set_lat = 37.555460;
                            set_lot = 126.916708;
                            local_str_ko = "마포구 / 서울";
                            local_str_en = "Mapo-gu / Seoul";
                            break;

                        case 14:
                            set_lat = 37.571874;
                            set_lot = 126.937371;
                            local_str_ko = "서대문구 / 서울";
                            local_str_en = "Seodaemun-gu / Seoul";
                            break;

                        case 15:
                            set_lat = 37.480107;
                            set_lot = 127.016431;
                            local_str_ko = "서초구 / 서울";
                            local_str_en = "Seocho-gu / Seoul";
                            break;

                        case 16:
                            set_lat = 37.558216;
                            set_lot = 127.040212;
                            local_str_ko = "성동구 / 서울";
                            local_str_en = "Seongdong-gu / Seoul";
                            break;

                        case 17:
                            set_lat = 37.600826;
                            set_lot = 127.015451;
                            local_str_ko = "성북구 / 서울";
                            local_str_en = "Seongbuk-gu / Seoul";
                            break;

                        case 18:
                            set_lat = 37.505411;
                            set_lot = 127.116354;
                            local_str_ko = "송파구 / 서울";
                            local_str_en = "Songpa-gu / Seoul";
                            break;

                        case 19:
                            set_lat = 37.519524;
                            set_lot = 126.861196;
                            local_str_ko = "양천구 / 서울";
                            local_str_en = "Yangcheon-gu / Seoul";
                            break;

                        case 20:
                            set_lat = 37.514289;
                            set_lot = 126.912293;
                            local_str_ko = "영등포구 / 서울";
                            local_str_en = "Yeongdeungpo-gu / Seoul";
                            break;

                        case 21:
                            set_lat = 37.536090;
                            set_lot = 126.987494;
                            local_str_ko = "용산구 / 서울";
                            local_str_en = "Yongsan-gu / Seoul";
                            break;

                        case 22:
                            set_lat = 37.609479;
                            set_lot = 126.920450;
                            local_str_ko = "은평구 / 서울";    local_str_en = "Eunpyeong-gu / Seoul";
                            break;

                        case 23:
                            set_lat = 37.581995;
                            set_lot = 126.983401;
                            local_str_ko = "종로구 / 서울";
                            local_str_en = "Jongno-gu / Seoul";
                            break;

                        case 24:
                            set_lat = 37.561477;
                            set_lot = 126.985755;
                            local_str_ko = "중구 / 서울";
                            local_str_en = "Jung-gu / Seoul";
                            break;

                        case 25:
                            set_lat = 37.595115;
                            set_lot = 127.092445;
                            local_str_ko = "중랑구 / 서울";
                            local_str_en = "Jungnang-gu / Seoul";
                            break;

                        case 26:
                            set_lat = 34.040667;
                            set_lot = -118.271569;
                            local_str_ko = "로스앤젤레스 / 캘리포니아 주";
                            local_str_en = "Los Angeles / CA";
                            break;

                        case 27:
                            set_lat = 36.171005;
                            set_lot = -115.170768;
                            local_str_ko = "라스베가스 / 네바다 주";
                            local_str_en = "Las Vegas / NV";
                            break;

                        case 28:
                            set_lat = 40.767074;
                            set_lot = -73.958577;
                            local_str_ko = "뉴욕 / 뉴욕주";
                            local_str_en = "Newyork City/NY";
                            break;

                        default:
                            set_lat = 36.171005;
                            set_lot = -115.170768;
                            local_str_ko = "라스베가스 / 네바다 주";
                            local_str_en = "Las Vegas / NV";

                            break;

                    }
                    setting_text[idx].setBackgroundColor(0xFF707070);
                    share_setting.setSharedLot(Double.toString(set_lot));
                    share_setting.setSharedLat(Double.toString(set_lat));
                    Log.d("==================lot",share_setting.getSharedLot());
                    Log.d("==================lat",share_setting.getSharedLat());
                    share_setting.setSharedCityKo(local_str_ko);
                    share_setting.setSharedCityEn(local_str_en);
                }
                return true;//false;
            }

        });
    }




}
