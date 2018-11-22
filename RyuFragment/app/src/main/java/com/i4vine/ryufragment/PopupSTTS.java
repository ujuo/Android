package com.i4vine.ryufragment;

import android.Manifest;
import android.annotation.TargetApi;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class PopupSTTS extends Activity {
    SpeechRecognizer mSpeechRecognizer;
    TextToSpeech tts;
    Button sttBtn;
    Button ttsBtn;
    EditText sttText;
    EditText ttsText;
    private static final int REQUEST_MICROPHONE = 3;

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


        setContentView(R.layout.popup_stts);

        checkPermission();
        sttText = findViewById(R.id.sttText);
        ttsText = findViewById(R.id.ttsText);
        sttBtn = (Button) findViewById(R.id.sttBtn);
        ttsBtn = (Button) findViewById(R.id.ttsBtn);

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                Toast.makeText(getApplicationContext(), "onReadyForSpeech", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBeginningOfSpeech() {
                Toast.makeText(getApplicationContext(), "onBeginningOfSpeech", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRmsChanged(float v) {
                Toast.makeText(getApplicationContext(), "onRmsChanged", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBufferReceived(byte[] bytes) {
                Toast.makeText(getApplicationContext(), "onBufferReceived", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEndOfSpeech() {
                Toast.makeText(getApplicationContext(), "onEndOfSpeech", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int error) {

                String message=null;
                switch(error){
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
                Toast.makeText(getApplicationContext(), "onError: "+ message , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null) {
                    sttText.setText(matches.get(0));
                }else {
                    Toast.makeText(getApplicationContext(), "onResults not matches", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {
                Toast.makeText(getApplicationContext(), "onPartialResults", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onEvent(int i, Bundle bundle) {
                Toast.makeText(getApplicationContext(), "onEvent", Toast.LENGTH_SHORT).show();
            }
        });



        sttBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                sttText.setText("");
                sttText.setHint("Listening...");
            }
        });



        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });

        ttsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String text = ttsText.getText().toString();
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ttsGreater21(text);
                }else{
                    ttsUnder20(text);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
        if(mSpeechRecognizer != null){
            mSpeechRecognizer.stopListening();
            mSpeechRecognizer.destroy();
        }



    }

    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text){
        HashMap<String, String>map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,"MessageId");
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text){
        String utteranceId = this.hashCode() + "";
        tts.speak(text,TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23){//Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }

}
