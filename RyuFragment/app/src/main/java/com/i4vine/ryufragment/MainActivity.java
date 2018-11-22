package com.i4vine.ryufragment;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.i4vine.serial.SerialTest;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.os.SystemClock.sleep;

public class MainActivity extends AppCompatActivity {
 //   KWS_Thread kws_thread;
 //   private final KWS_Handler kws_handler = new KWS_Handler(this);

    Thread t;

    SharePreferenceUtil share_main;
    Locale share_locale;
    String share_language;
    TextView customTextView3;
    TextView customTextView2;
    FragmentAir fragmentAir;
    FragmentCalendar fragmentCalendar;
    FragmentCamera fragmentCamera;
    FragmentCloth fragmentCloth;
    FragmentWeather fragmentWeather;
    FragmentWeb fragmentWeb;
    PopupPreview popupPreview;
    Bitmap bitmap;
    String nowTime;
    TimerTask tt1;
    int timer_cnt;
    int touch_cnt = 0;
    int change_setting = 0;
    int change_photo = 0;
    int speak_cnt = 0;
    int preview_cnt = 0;
    int thread_cnt=0;
    //STT
    private SpeechRecognizer speech;
    private Intent recognizerIntent;
    TextView sttResult;


    //TTS
    private TextToSpeech tts;
    AudioManager mAudioManager;
    int mStreamVolume = 0;

    SerialTest serial;
    boolean serial_enable = false;
    boolean speech_test = true;

    Calendar calendar = Calendar.getInstance();
    int kws_cnt=0;
    static byte[] kws_result = new byte[30];

    int hide_mode = 0;

    //  boolean is_msg = false;
    void no_title(){
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOption = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        newUiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        newUiOption |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        newUiOption |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        getWindow().getDecorView().setSystemUiVisibility(newUiOption);
        // Hide Status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

/*
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hide_mode > 0) {
            Log.d("================", "hide system bar");
            //super.onWindowFocusChanged(hasFocus);
            int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
            int newUiOption = uiOptions;
            boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
            newUiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            newUiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
            newUiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            newUiOption |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            newUiOption |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            getWindow().getDecorView().setSystemUiVisibility(newUiOption);
            // Hide Status bar
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

    }
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide navigation bar
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOption = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        newUiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        newUiOption |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        newUiOption |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        getWindow().getDecorView().setSystemUiVisibility(newUiOption);
        // Hide Status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Modify action bar
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        checkDangerousPermissions();

        setContentView(R.layout.activity_main);



        customTextView3 = (TextView) findViewById(R.id.customTextView3);
        getDate();
        customTextView3.setText(nowTime);

        customTextView2 = (TextView) findViewById(R.id.customTextView2);

        FragmentManager manager = getSupportFragmentManager();

        if(serial_enable != false) {
            int ret = 0;
            int ret1 = 0;
            serial = new SerialTest();
            ret = serial.open("/dev/ttySAC3", "115200", 0,1);
            ret1 = serial.open("/dev/ttySAC1", "115200", 0,1);
        }else{

        }

        sttResult = (TextView) findViewById(R.id.sttResult);
        fragmentWeather = (FragmentWeather) manager.findFragmentById(R.id.fragment_weather);
        fragmentCalendar = (FragmentCalendar) manager.findFragmentById(R.id.fragment_calendar);
        fragmentWeb = (FragmentWeb) manager.findFragmentById(R.id.fragment_web);
        fragmentAir = (FragmentAir) manager.findFragmentById(R.id.fragment_air);

        fragmentCamera = (FragmentCamera) manager.findFragmentById(R.id.fragment_camera);

        fragmentCloth = (FragmentCloth) manager.findFragmentById(R.id.fragment_cloth);



        fragmentWeather.setClick();
        fragmentWeather.setClick2();
        fragmentWeather.setClick1();

        fragmentCalendar.setClick4();

        share_main = new SharePreferenceUtil(MainActivity.this);
        share_language = Locale.getDefault().getLanguage(); //en,ko
        share_main.setSharedLanguage(share_language);

        share_locale = new Locale(share_language);
        Locale.setDefault(share_locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.setLocale(share_locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


        //STT
        SttInit();
       /* recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"ko-KR");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, share_locale);//Locale.getDefault());
        speech = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
        speech.setRecognitionListener(mListener);
        //speech.startListening(recognizerIntent);
        startListening();*/

        //TTS
        mAudioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        mStreamVolume = 30;
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mStreamVolume, 0);


        tts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    Toast.makeText(getApplicationContext(), "tts init success",
                            Toast.LENGTH_SHORT).show();
                    Log.d("============getDefault", Locale.getDefault().getDisplayLanguage());
                    int result = tts.setLanguage(Locale.getDefault());//ENGLISH);

                    //언어 데이터가 없거나 혹은 언어가 지원하지 않으면...
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(MainActivity.this, "이 언어는 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        //음성 톤
                        tts.setPitch(1.0f);
                        //읽는 속도
                        tts.setSpeechRate(1.0f);
                    }


                } else {
                    Log.e("tts", "initialized failed");
                }

            }
        });


        customTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_preview = new Intent(getApplicationContext(), PopupPreview.class);
                change_photo = 2;
                startActivity(i_preview);
            }
        });
        customTextView3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    touch_cnt++;
                    if (touch_cnt > 4) {
                        touch_cnt = 0;
                        change_setting = 2;
                        Intent i9 = new Intent(getApplicationContext(), PopupSetting.class);
                      //  i9.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i9);


                    }
                }
                return true;//false;
            }
        });

        tt1 = timerTaskMaker();
        timer_cnt = 0;

        final Timer timer = new Timer();
        timer.schedule(tt1, 10000, 5000);//10000);//30000);//30000);//120000);




        //checkDangerousPermissions();
        //      fragmentCamera.selectGallery();

    }

    void SttInit(){


        Log.d("================","============sttInit");
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"ko-KR");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);//LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, share_locale);//Locale.getDefault());
        speech = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());//MainActivity.this);
        speech.setRecognitionListener(mListener);
        //speech.startListening(recognizerIntent);
        startListening();
    }

     Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
           // if (t == )
            switch (msg.what){
                case 1:
                   // Log.d("=============handler", "==================msg 1");
                    if(kws_result[0] == 0x55) {//&& (kws_cnt > 50)){

                        if(share_language.compareTo("en") == 0){
                            Log.d("==="+share_locale+"==="+share_language,"====================lan en");
                          //  recognizerIntent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"en"});
                            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, share_locale);
                        }else{
                            Log.d("==="+share_locale+"==="+share_language,"====================lan ko");
                            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
                        }
                        speech.startListening(recognizerIntent);

                        //startListening();


                    }
                    break;

                case 2:


                    if(share_language.compareTo("en") == 0){
                        Log.d("==="+share_locale+"==="+share_language,"====================lan en");
                        //  recognizerIntent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"en"});

                        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, share_locale);
                        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US");

                         recognizerIntent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"en"});
                    }else{
                        Log.d("==="+share_locale+"==="+share_language,"====================lan ko");
                        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
                    }
                    if(speech != null) {
                        speech.startListening(recognizerIntent);
                    }






                    break;
                default:
                    Log.d("================handler", "===========default");

                    break;
            }
        }
    };

    class Thread extends java.lang.Thread{
        public Thread(){

        }

        @Override
        public void run() {
            super.run();

            while(!(t.isInterrupted())) {
                Message msg = handler.obtainMessage();
                msg.what = 0;


                if (serial_enable != false) {
                    byte[] arg = {0};
                    kws_result = serial.recvCallback1(arg);

                    if (kws_result.length > 0) {
                        if (kws_result[0] == 0x5A) {
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }
                    }
                } else {
                    if((speech_test == true) && ((thread_cnt % 5) == 0)){
                        msg.what = 2;
                        thread_cnt = 0;
                        handler.sendMessage(msg);
                    }
                  //  Log.d("==============thread", "================run");
                  //  msg.what = 0;
                  //  handler.sendMessage(msg);
                }


                try {
                    sleep(1000);//20);//1000); //20ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                if(speech_test == true)
                    thread_cnt++;

            }

        }
    }


    void speak_fashion(){
        Intent i_fashion = new Intent(getApplicationContext(), PopupFashionTab.class);
        i_fashion.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(i_fashion);


        if (share_language.compareTo("ko") == 0) {
            Speech("패션 테스트");
        } else if (share_language.compareTo("en") == 0) {
            Speech("fashion test");
        }
    }

    void speak_plan() {
        Intent i_plan = new Intent(getApplicationContext(), PopupCalendar.class);
        i_plan.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i_plan);

        String get_plan = share_main.getSharedPlan();
        int get_plan_idx = share_main.getSharedPlanIdx();
        String plan[] = get_plan.split("\n");
        if (share_language.compareTo("ko") == 0) {
            if (get_plan_idx == 0) {
                Speech("오늘 일정은 없습니다.");
            } else if (get_plan_idx > 0) {
                String my_plan = "";
                for (int j = 0; j < get_plan_idx; j++) {
                    my_plan += plan[j];
                    my_plan += ".";
                }
                String sentence = "오늘은 " + Integer.toString(get_plan_idx) + "건의 일정이 있습니다.";
                        //+ my_plan;
                Speech(sentence);

            }
        } else if (share_language.compareTo("en") == 0) {
            String my_plan = "";
            for (int j = 0; j < get_plan_idx; j++) {
                my_plan += plan[j];
                my_plan += ".";
            }
            String sentence = "There are" + Integer.toString(get_plan_idx) + "events today.";

            Speech(sentence);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("main destroy", "=============================");
        if (t != null){
            Log.e("t != null", "=============================");
            t.interrupt();
        }
        if (speech != null) {
            Log.e("speech != null", "=============================");
            speech.destroy();
            speech.cancel();
            speech = null;
        }

        if (tts != null) {
            tts = null;
        }

        if (tt1 != null) {
            tt1.cancel();
            tt1 = null;
        }
    }

    public void startListening() {
        //Toast.makeText(getApplicationContext(), "startListening", Toast.LENGTH_SHORT).show();

        if (speech == null) {
            Log.d("========startListening", "speech == null");
            speech = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
            speech.setRecognitionListener(mListener);
        }

        if (speech.isRecognitionAvailable(getApplicationContext())) {
            Log.d("isRecognitionAvailable","lan"+share_language+"===locale" + share_locale);
            recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
         /*       recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"ko-KR");
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);*/
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);///LANGUAGE_MODEL_FREE_FORM);
      //      recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            //     recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,10000);
            // startActivityForResult(recognizerIntent,RESULT_SPEECH);

            //recognizerIntent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"en"});
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, share_locale);

        //    recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

       ////////kws     speech.startListening(recognizerIntent);
        }
    }

    private RecognitionListener mListener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle bundle) {


        }

        @Override
        public void onBeginningOfSpeech() {
            //    Log.d("==onBeginningOfSpeech","===================");
        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {
            //  Log.d("======bufferreceived","===================");
            //  speech.cancel();
            //  speech.startListening(recognizerIntent);
        }

        @Override
        public void onEndOfSpeech() {
            //Log.d("======endofspeech","===================");
        }

        @Override
        public void onError(int i) {
            String message = null;

            switch (i) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트워크 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "Recognizer busy";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "speech 시간 초과";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "알수없는 오류";
                    break;
                default:
                    message = "default";
                    break;

            }
            Log.e("========error", message);
            if ((i == SpeechRecognizer.ERROR_SPEECH_TIMEOUT) || (i == SpeechRecognizer.ERROR_NO_MATCH)) {
                speech.cancel();
             //   sleep(100);
             //   speech.startListening(recognizerIntent);
                //startListening();
            } else {
                speech.cancel();
            //    sleep(10);
            //    speech.startListening(recognizerIntent);
            }

        }

        @Override
        public void onResults(Bundle bundle) {
            ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            //   Log.d("====onresult","========================");
            int play_flag = 0;
            if (matches != null) {
                speech.cancel();//stopListening();//cancel();
                //     sttText.setText(matches.get(0));
                sttResult.setVisibility(View.VISIBLE);
                sttResult.setText(matches.get(0));
                    Log.d("====result",matches.get(0));
                String str = matches.get(0);// matches.toString();

                if(share_language.compareTo("ko") == 0){
                    if ((str.contains("일정")) || (str.contains("스케쥴"))|| (str.contains("스케줄"))) {
                        //Speech("오늘은 일정이 없습니다.");
                        play_flag = 1;

                    } else if (str.contains("날씨")) {
                        //Speech("오늘 날씨는 맑습니다.");
                        play_flag = 2;

                    } else if ((str.contains("사진")) || (str.contains("찍어줘"))) {
                        //Speech("카메라 활성화 합니다.");
                        play_flag = 3;

                    } else if ((str.contains("뭐 입을까")) ) {
                        play_flag = 4;


                    }else if((str.contains("동작")) || (str.contains("시작"))){
                        if (str.contains("베이직")) {
                            play_flag = 5;
                        } else if (str.contains("스피드")) {
                            play_flag = 6;
                        } else if (str.contains("소프트")) {
                            play_flag = 7;
                        } else if (str.contains("프로")) {
                            play_flag = 8;
                        } else if (str.contains("청정")) {
                            play_flag = 9;
                        } else if (str.contains("제습")) {
                            play_flag = 10;
                        }
                    } else if (str.contains("정지")) {
                        if (str.contains("베이직")) {
                            play_flag = 11;
                        } else if (str.contains("스피드")) {
                            play_flag = 12;
                        } else if (str.contains("소프트")) {
                            play_flag = 13;
                        } else if (str.contains("프로")) {
                            play_flag = 14;
                        } else if (str.contains("청정")) {
                            play_flag = 15;
                        } else if (str.contains("제습")) {
                            play_flag = 16;
                        }
                    }
                } else {
                    str.toLowerCase();
                    if ((str.contains("how is the weather")) || (str.contains("what's the weather"))) {
                        play_flag = 2;
                    } else if ( (str.contains("what's the schedule"))) {
                        play_flag = 1;
                    } else if ((str.contains("take a picture")) ) {
                        play_flag = 3;
                    } else if ((str.contains("what do i wear")) || (str.contains("what do I wear"))) {
                        play_flag = 4;
                    } else if ((str.contains("turn on ")) || (str.contains("start"))) {
                        if (str.contains("basic")) {
                            play_flag = 5;
                        } else if (str.contains("speed")) {
                            play_flag = 6;
                        } else if (str.contains("soft")) {
                            play_flag = 7;
                        } else if (str.contains("pro")) {
                            play_flag = 8;
                        } else if (str.contains("air purifier")) {
                            play_flag = 9;
                        } else if (str.contains("dehumidifier")) {
                            play_flag = 10;
                        }
                    } else if (str.contains("stop the mode")) {
                        if (str.contains("basic")) {
                            play_flag = 11;
                        } else if (str.contains("speed")) {
                            play_flag = 12;
                        } else if (str.contains("soft")) {
                            play_flag = 13;
                        } else if (str.contains("pro")) {
                            play_flag = 14;
                        } else if (str.contains("air purifier")) {
                            play_flag = 15;
                        } else if (str.contains("dehumidifier")) {
                            play_flag = 16;
                        }
                    }
                }
            } else {
                Log.e("==========Onresult ", "not matches");
                Toast.makeText(getApplicationContext(), "onResults not matches", Toast.LENGTH_SHORT).show();
            }
            //    Log.d("===========result", Integer.toString(play_flag));
            if (play_flag > 0) {
                switch (play_flag) {
                    case 1:
                        Intent i = new Intent(getApplicationContext(), PopupCalendar.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        String get_plan = share_main.getSharedPlan();
                        int get_plan_idx = share_main.getSharedPlanIdx();
                        String plan[] = get_plan.split("\n");
                        if (share_language.compareTo("ko") == 0) {
                            if (get_plan_idx == 0) {
                                Speech("오늘 일정은 없습니다.");
                            } else if (get_plan_idx > 0) {
                                String my_plan="";
                                for(int j=0; j < get_plan_idx; j++){
                                    my_plan += plan[j];
                                    my_plan += ".";
                                }
                                String sentence = "오늘은 " + Integer.toString(get_plan_idx) + "건의 일정이 있습니다."
                                        + my_plan;
                                Speech(sentence);

                            }
                        } else if (share_language.compareTo("en") == 0) {
                            String my_plan="";
                            for(int j=0; j < get_plan_idx; j++){
                                my_plan += plan[j];
                                my_plan += ".";
                            }
                            String sentence = "There are" + Integer.toString(get_plan_idx) + "events today."
                                    + my_plan;
                            Speech(sentence);
                        }
                        break;
                    case 2:
                        Intent i3 = new Intent(getApplicationContext(), PopupWeather.class);
                        i3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i3);
                        String state = share_main.getSharedWeather();
                        String maxTemp = share_main.getSharedMaxTemp();
                        String minTemp = share_main.getSharedMinTemp();
                        String humi = share_main.getSharedHumi();
                        if (share_language.compareTo("ko") == 0) {
                            Speech("오늘의 날씨는 " + state + " 입니다." +
                                    " 최고기온 화씨 " + maxTemp + " 도" + " 최저기온 화씨 " + minTemp + " 도," +
                                    "비올 확률 " + humi + " % 입니다."
                            );
                        } else if (share_language.compareTo("en") == 0) {
                            Speech("Today's weather. The highs lows today temperature is" +
                                    maxTemp +"degrees F" + minTemp + "degrees Fahrenheit ." +
                                    "Rain probability is" + humi + "%.");
                        }
                        break;
                    case 3:
                        Intent i4 = new Intent(getApplicationContext(), PopupPreview.class);


                        i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i4);
                        Speech("3");
                        change_photo = 2;

                        break;
                    case 4:

                        String state1 = share_main.getSharedWeather();
                        String maxTemp1 = share_main.getSharedMaxTemp();
                        String minTemp1 = share_main.getSharedMinTemp();
                        String humi1 = share_main.getSharedHumi();
                      //  HashMap<String, String> map = new HashMap<String, String>();
                      //  map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "51");

                        if (share_language.compareTo("ko") == 0) {
                            Speech("이주 동안 입었던 옷들입니다.");
                        } else if (share_language.compareTo("en") == 0) {
                            Speech("It's the clothes you were for 2 weeks");
                        }


                        Intent i5 = new Intent(getApplicationContext(), PopupFashionTab.class);
                        i5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(i5);

                        if (share_language.compareTo("ko") == 0) {
                            Speech("패션 테스트");
                        } else if (share_language.compareTo("en") == 0) {
                            Speech("Fashion test.");
                        }
                        break;

                    case 5:
                        if (share_language.compareTo("ko") == 0) {
                            ctrlController(7,3);
                            Speech("베이직모드를 동작합니다.");
                        } else if (share_language.compareTo("en") == 0) {
                            ctrlController(7,3);
                            Speech("Works in basic mode");
                        }
                        break;

                    case 6:
                        if (share_language.compareTo("ko") == 0) {
                            ctrlController(6,3);
                            Speech("스피드 모드를 동작합니다.");
                        } else if (share_language.compareTo("en") == 0) {
                            ctrlController(6,3);
                            Speech("Works in speed mode");
                        }
                        break;

                    case 7:
                        if (share_language.compareTo("ko") == 0) {
                            Speech("소프트 모드를 동작합니다. ");
                            ctrlController(5,3);
                        } else if (share_language.compareTo("en") == 0) {
                            Speech("Works in soft mode.");
                            ctrlController(5,3);
                        }
                        break;

                    case 8:
                        if (share_language.compareTo("ko") == 0) {
                            Speech("프로 모드를 동작합니다.");
                            ctrlController(4,3);
                        } else if (share_language.compareTo("en") == 0) {
                            Speech("Works in pro mode");
                            ctrlController(4,3);
                        }
                        break;

                    case 9:
                        if (share_language.compareTo("ko") == 0) {
                            Speech("청정 모드 동작합니다.");
                            ctrlController(9,3);
                        } else if (share_language.compareTo("en") == 0) {
                            ctrlController(9,3);
                            Speech("Works air purifier");
                        }
                        break;

                    case 10:
                        if (share_language.compareTo("ko") == 0) {
                            Speech("제습 모드 동작합니다.");
                            ctrlController(8,3);
                        } else if (share_language.compareTo("en") == 0) {
                            ctrlController(8,3);
                            Speech("Works dehumidifier");
                        }
                        break;

                    case 11:
                        if (share_language.compareTo("ko") == 0) {
                            ctrlController(14,3);
                            Speech("베이직 모드를 정지합니다.");
                        } else if (share_language.compareTo("en") == 0) {
                            ctrlController(14,3);
                            Speech("Stop basic operation.");
                        }
                        break;
                    case 12:
                        if (share_language.compareTo("ko") == 0) {
                            ctrlController(14,3);
                            Speech("스피드 모드를 정지합니다.");
                        } else if (share_language.compareTo("en") == 0) {
                            ctrlController(14,3);
                            Speech("Stop speed operation.");
                        }
                        break;

                    case 13:
                        if (share_language.compareTo("ko") == 0) {
                            ctrlController(14,3);
                            Speech("소프트 모드를 정지합니다.");
                        } else if (share_language.compareTo("en") == 0) {
                            ctrlController(14,3);
                            Speech("Stop soft operation.");
                        }
                        break;
                    case 14:
                        if (share_language.compareTo("ko") == 0) {
                            ctrlController(14,3);
                            Speech("프로 모드를 정지합니다.");
                        } else if (share_language.compareTo("en") == 0) {
                            ctrlController(14,3);
                            Speech("Stop pro operation.");
                        }
                        break;

                    case 15:
                        if (share_language.compareTo("ko") == 0) {
                            ctrlController(14,3);
                            Speech("청정 모드를 정지합니다.");
                        } else if (share_language.compareTo("en") == 0) {
                            ctrlController(14,3);
                            Speech("Stop air purifier operation");
                        }
                        break;

                    case 16:
                        if (share_language.compareTo("ko") == 0) {
                            ctrlController(14,3);
                            Speech("제습 모드를 정지합니다.");
                        } else if (share_language.compareTo("en") == 0) {
                            ctrlController(14,3);
                            Speech("Stop dehumidifier operation.");
                        }
                        break;

                    default:
                        break;
                }
            //kws    speech.startListening(recognizerIntent);

            } else {
             //kws   speech.startListening(recognizerIntent);
            }
        }

        @Override
        public void onPartialResults(Bundle bundle) {
            Log.d("======partial result", "===================");
            //  speech.cancel();
            //  speech.startListening(recognizerIntent);
        }

        @Override
        public void onEvent(int i, Bundle bundle) {
            Log.d("======onEvent", "===================");
        }
    };


    @Override
    protected void onStart() {
        super.onStart();

        Log.d("=====================","=========onstart");


        Log.d("=====" + share_locale + "====" + share_language, "onStart done");

        if(speech == null) {
            Log.d("=====================","onStart speech == null");
            SttInit();
        }else{
            Log.d("====================","onStart speech != null");
        }

        t = new Thread();
        t.start();



     //   kws_thread = new KWS_Thread();
     //   kws_thread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        boolean retry = true;
        Log.d("====================","=========onstop");
        while(retry){



            if(t != null) {
                Log.d("=============onStop","==========t != null");
                t.interrupt();
            }

            if (speech != null) {
                Log.d("=============onStop","==========speech != null");
                speech.destroy();
                speech.cancel();
                speech = null;
            }
            retry = false;

        }
    }


    private void Speech(String s) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            tts.speak(s, TextToSpeech.QUEUE_FLUSH, null, null);
            // API 20
        else
            tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);


    }

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {

                Log.e("===========main", "permission dinied");
                ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
                Log.d("===========main","requestpermissions");

                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PERMISSION_GRANTED", Toast.LENGTH_LONG).show();
        } else {
                Toast.makeText(this,"!PERMISSION_GRANTED", Toast.LENGTH_LONG).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this,"should show request permission rationale", Toast.LENGTH_LONG).show();
                Log.d("==================main","requestpermissionrationale");
            } else {

                ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
                Log.d("==================main","requestpermissions");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("==========main","permission granted");
                              Toast.makeText(this,permissions[i]+"Permission_granted", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("==========main","permission not granted");
                              Toast.makeText(this, permissions[i]+"!Permission_granted", Toast.LENGTH_LONG).show();
                }

            }
        }
    }


    public void onView1Clicked(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //     Toast.makeText(getApplicationContext(), "STT TTS", Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(getApplicationContext(), PopupSTTS.class);
                startActivity(i2);
            }
        });
    }

    public void getDate() {
        Calendar now = Calendar.getInstance();
        Date getToday = now.getTime();

        SimpleDateFormat nTime = new SimpleDateFormat("yyyy.MM.dd E h:mm  a", Locale.US);
        nowTime = nTime.format(getToday);

    }

    public void update_fragment() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            //    Log.d("================",Integer.toString(timer_cnt));
               // if(is_msg == false) {

                    timer_cnt++;

                    if ((timer_cnt % 6)==0 ) { //30s
                        no_title();
                        getDate();

                        customTextView3.setText(nowTime);

                        //    fragmentCamera.reload_image();
                        if (change_setting == 1){
                            fragmentWeather.setClick();
                            fragmentWeather.setClick2();
                            fragmentWeather.setClick1();
                            change_setting = 0;
                        }
                    }

                    if ((timer_cnt % 120) == 0 ) { //10m
                        fragmentCalendar.setClick4();
                        fragmentCamera.reload_image();
                    }
                    if (timer_cnt > 360) { //30m interval
                        timer_cnt = 0;
                        fragmentWeather.setClick();
                        fragmentWeather.setClick2();
                        fragmentWeather.setClick1();
                    }
                    if ((timer_cnt % 2) == 0) {

                        if(change_setting == 2){
                            Log.d("====="+ share_locale +"===="+share_language,"change_setting = 2 enter");
                            fragmentWeather.setClick();
                            fragmentWeather.setClick2();
                            fragmentWeather.setClick1();

                            share_language = share_main.getSharedLanguage();
                            share_locale = new Locale(share_language);
                            Locale.setDefault(share_locale);
                            Configuration config = getBaseContext().getResources().getConfiguration();
                            config.setLocale(share_locale);
                            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                            Log.d("====="+ share_locale +"===="+share_language,"change_setting = 2 exit");

                            change_setting = 1;
                        }
                    }


                    if(change_photo > 0){
                        fragmentCamera.reload_image();
                        change_photo--;
                        if(change_photo <= 0){
                            change_photo = 0;
                        }
                    }
             /*       if (share_main.getSharedLive() == 0) {
                        if (timer_cnt >= 60) { //5m interval
                            //sleep
                            Intent i13 = new Intent(getApplicationContext(), SleepMode.class);
                            startActivity(i13);
                        }
                    }*/
                    if (speak_cnt > 0) {
                        speak_cnt++;
                        if (speak_cnt == 4) {
                            speak_plan();
                        } else if (speak_cnt == 5) {
                            speak_fashion();
                            speak_cnt = 0;
                        }
                    }


            }
        });
    }

    public TimerTask timerTaskMaker() {
        TimerTask tempTask = new TimerTask() {
            @Override
            public void run() {
                update_fragment();
            }
        };
        return tempTask;
    }

    public void ctrlController(final int idx, int onoff) {
        int t=0;
        int ret;
        int set_flag = 0;

        if (onoff == 1) {
            Toast.makeText(getApplicationContext(), "text" + Integer.toString(idx) + "touch", Toast.LENGTH_SHORT).show();
            t = 1 << idx;
            set_flag |= t;
            Log.d("========DOWN", "idx:" + Integer.toString(idx) + " t:" + Integer.toString(t) + " flag: " + Integer.toString(set_flag));
            //   ret = serial.write(Integer.toString(t), Integer.toString(t).length());
            if(serial_enable != false)
                ret = serial.write(Integer.toString(set_flag), Integer.toString(set_flag).length());
        }
        if (onoff == 2) {
            int t1;
            t1 = set_flag;
            set_flag = set_flag & ~(1 << idx);
            Log.d("==========UP", "idx:" + Integer.toString(idx) + " t:" + Integer.toString(t1) + " flag: " + Integer.toString(set_flag));
            if(serial_enable != false)
                ret = serial.write(Integer.toString(set_flag), Integer.toString(set_flag).length());
        }
        if (onoff == 3){
            Toast.makeText(getApplicationContext(), "text" + Integer.toString(idx) + "touch", Toast.LENGTH_SHORT).show();
            t = 1 << idx;
            set_flag = 1 << 15;

            set_flag |= t;
            if((idx == 9) ){
                set_flag |= (1 << 10);
            }
            Log.d("========DOWN", "idx:" + Integer.toString(idx) + " t:" + Integer.toString(t) + " flag: " + Integer.toString(set_flag));
            //   ret = serial.write(Integer.toString(t), Integer.toString(t).length());
            if(serial_enable != false) {
                ret = serial.write(Integer.toString(set_flag), 77);// Integer.toString(set_flag).length());

            }
        }
    }


}
