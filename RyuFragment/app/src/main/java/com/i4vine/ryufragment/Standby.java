package com.i4vine.ryufragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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

public class Standby extends Activity {
    Handler handler = new Handler();
    TextView textView;
    TextView textView1;
    TextView standbyText;
    ImageView weatherImage;
    Calendar now = Calendar.getInstance();

    String get_lot;
    String get_lat;
    String now_time;
    String now_ampm;
    Runnable r = new Runnable(){
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

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
        getLocation();
        getWeather();
        getDate1();
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

    public void getDate1(){
        Calendar now = Calendar.getInstance();
        Date getToday = now.getTime();

        SimpleDateFormat nTime = new SimpleDateFormat("hh:mm", Locale.US);
        SimpleDateFormat nAMPM = new SimpleDateFormat("a", Locale.US);
        now_time = nTime.format(getToday);
        now_ampm = nAMPM.format(getToday);
        textView1.setText(now_ampm);
        textView.setText(now_time);

    }

    public void getDate(){
        int isAMPM = now.get(Calendar.AM_PM);

        SimpleDateFormat fmt = new SimpleDateFormat("hh:mm a", Locale.US);
        Date currentTime_1 = new Date();
        String dt1 = fmt.format(currentTime_1);

        textView1.setText(dt1);

        switch(isAMPM){
            case Calendar.AM:
             //   Toast.makeText(this,"am", Toast.LENGTH_LONG).show();
                textView1.setText("AM  ");
                textView.setText(now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE));
                break;
            case Calendar.PM:
              //  Toast.makeText(this,"pm", Toast.LENGTH_LONG).show();
                textView1.setText("PM  ");
                textView.setText(now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE));
                break;
            default:
              //  Toast.makeText(this,"default"+isAMPM, Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void getWeather() {
        //    Double lat = 49.22;
        //    Double lot = 24.409;
        Geocoder mGeoCoder = new Geocoder(getApplicationContext());
        List<Address> address = null;
        String lat1 = get_lat;//"37.517331";//latitude.getText().toString();
        String lot1 = get_lot;//"127.047377";//longitude.getText().toString();
        Double lat = Double.valueOf(lat1);
        Double lot = Double.valueOf(lot1);
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
                            String d = day.getIconUrl();
                            int img_id = set_icon(d);
                            weatherImage.setImageResource(img_id);
                          //  Glide.with(getApplicationContext()).load(day.getIconUrl()).into(weatherImage);
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


    private int set_icon(String s){
        int id = 0;
        if (s.contains("01")) {
            id = R.drawable.img_weather_01_b;
        } else if (s.contains("02")) {
            id = R.drawable.img_weather_02_b;
        } else if (s.contains("03")) {
            id = R.drawable.img_weather_03_b;
        } else if (s.contains("04")) {
            id = R.drawable.img_weather_04_b;
        } else if (s.contains("09")) {
            id = R.drawable.img_weather_05_b;
        } else if (s.contains("10")) {
            id = R.drawable.img_weather_06_b;
        } else if (s.contains("11")) {
            id = R.drawable.img_weather_07_b;
        } else if (s.contains("13")) {
            id = R.drawable.img_weather_08_b;
        } else if (s.contains("50")) {
            id = R.drawable.img_weather_09_b;
        } else {
            id = R.drawable.img_weather_01_b;
        }
        return id;
    }

    public void getLocation(){
        get_lat = "36.171005";
        get_lot = "-115.170768";
    /*    String folder = "temp";
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

        }*/
      /*     try {
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
           // }

            //buffer.close();

        }catch(FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Location file not found.",Toast.LENGTH_SHORT).show();
        }catch(IOException e){
            e.printStackTrace();
        }*/

    }


}
