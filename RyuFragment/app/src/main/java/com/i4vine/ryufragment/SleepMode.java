package com.i4vine.ryufragment;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Math.round;

public class SleepMode extends Activity {
    TextView textView;
    TextView textView1;
    TextView standbyText;
    ImageView weatherImage;
    LinearLayout standby;
    Calendar now = Calendar.getInstance();

    String get_lot;
    String get_lat;
    String now_time;
    String now_ampm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOption = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)==uiOptions);
        newUiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOption);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.standby);
        textView = (TextView)findViewById(R.id.textView);
        textView1 = (TextView)findViewById(R.id.textView1);
        weatherImage = (ImageView)findViewById(R.id.weatherImage);
        standbyText = (TextView)findViewById(R.id.standbyText);
        getLocation1();
        getWeather1();
        getDate2();
        standby.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                return true;//false;
            }
        });


    }

    public void getDate2(){
        Calendar now = Calendar.getInstance();
        Date getToday = now.getTime();

        SimpleDateFormat nTime = new SimpleDateFormat("hh:mm", Locale.US);
        SimpleDateFormat nAMPM = new SimpleDateFormat("a", Locale.US);
        now_time = nTime.format(getToday);
        now_ampm = nAMPM.format(getToday);
        textView1.setText(now_ampm);
        textView.setText(now_time);

    }
    public void getWeather1() {
        //    Double lat = 49.22;
        //    Double lot = 24.409;
        Geocoder mGeoCoder = new Geocoder(getApplicationContext());
        List<Address> address = null;
        String lat1 = get_lat;//"37.517331";//latitude.getText().toString();
        String lot1 = get_lot;//"127.047377";//longitude.getText().toString();
        Double lat = Double.valueOf(lat1).doubleValue();
        Double lot = Double.valueOf(lot1).doubleValue();
        String unit = "Imperial";
        String key = "EditYourKey";



        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        okHttpClient.dispatcher().setMaxRequests(3);

        Retrofit client = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface service = client.create(ApiInterface.class);


        Call<WeatherForecast> callForecast = service.getForecast(key, lat, lot, unit);
        callForecast.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                Log.e("enter", "onResponse");
                WeatherForecast data = response.body();

                long millis = new Date().getTime();
                int i = 0;

                if (response.isSuccessful()) {
                    for (WeatherDay day : data.getItems()) {

                        String getdata2 = data.getItems().get(i).getTimetxt();
                        DateFormat formatter;

                        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date datet = (Date) formatter.parse(getdata2);
                            long output = datet.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        {
                            //    weatherStatus.setText(day.getDesc());
                            //   temperature.setText(String.valueOf(day.getTempInteger()));
                            //  humidity.setText(String.valueOf(day.getHumi()));
                            // wind.setText(String.valueOf(day.getSpeed()));

                            //  city.setText(String.valueOf(day.getCity()));
                            String str = day.getTimetxt();
                            String te = day.getTemp();
                            double ite = Double.parseDouble(te);
                            long te1 = round((ite-32) / 1.8) ;
                            String ste = String.valueOf(te1);
                            standbyText.setText(ste);
                            Glide.with(getApplicationContext()).load(day.getIconUrl()).into(weatherImage);
                            break;
                        }
                    }
                    Log.e("Success", new Gson().toJson(response.body()));

                } else {
                    Log.e("unSuccess", new Gson().toJson(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                Log.e("test error", t.toString());
            }
        });
    }


    public void getLocation1(){
        String folder = "temp";
        String path = Environment.getExternalStorageDirectory().getPath();
        String subpath = path+"/"+folder+"/";
        String text = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp";
        File directory = new File(text,"location.txt");
        String text1 = "36.171005,-115.170768\n";

        File dirs = new File(Environment.getExternalStorageDirectory(), folder);
        if(!dirs.exists()) {
            dirs.mkdir();
            Log.d("standby", "temp directory created");
        }

        if(directory.length() > 0) {

        }else {

            try {
                FileOutputStream fos = new FileOutputStream(directory);
                fos.write(text1.getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*
            FileWriter fw = null;
            String text1 = "37.516382,127.019818\n";
            try {
                fw = new FileWriter(directory);
                fw.write(text1);
            }catch(Exception e){
                e.printStackTrace();
            }
            if(fw != null){
                try{
                    fw.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
             */
        }
        try {
            StringBuffer data = new StringBuffer();
            BufferedReader buffer = new BufferedReader(new FileReader(directory));
            String str = buffer.readLine();
            while(str != null){
                data.append(str+"\n");
                str = buffer.readLine();
            }
            String readStr = new String(data);
            String[] loc = readStr.split("\n");
            if(loc != null) {
                String[] lat_loc = loc[0].split(",");
                if (lat_loc != null) {
                    get_lat = lat_loc[0];
                    get_lot = lat_loc[1];
                }else{
                    get_lat = "36.171005";
                    get_lot = "-115.170768";
                }

            }else{
                get_lat = "36.171005";
                get_lot = "-115.170768";
            }

            buffer.close();

        }catch(FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Location file not found.",Toast.LENGTH_SHORT).show();
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
