package com.i4vine.ryufragment;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class PopupWeather extends Activity {
    TextView sub_city;
    TextView sub_temp;
    TextView sub_tempUnit;
    TextView sub_tempUnit1;
    ImageView sub_tempImage;
    TextView sub_weatherStatus;
    ImageView sub_img_humi;
    TextView sub_text_humi;
    ImageView sub_img_wind;
    TextView sub_text_wind;
    TextView sub_text_wind_unit;
    ImageView sub_img_uv;
    TextView sub_text_uv;
    ImageView sub_img_dust;
    TextView sub_text_dust;
    TextView sub_time1;
    TextView sub_time2;
    TextView sub_time3;
    TextView sub_time4;
    ImageView sub_time1_weather_icon;
    TextView sub_time1_weather;
    TextView sub_time1_temp;
    ImageView sub_time1_humi;
    TextView sub_time1_text_humi;
    ImageView sub_time1_dust;
    TextView sub_time1_text_dust;

    ImageView sub_time2_weather_icon;
    TextView sub_time2_weather;
    TextView sub_time2_temp;
    ImageView sub_time2_humi;
    TextView sub_time2_text_humi;
    ImageView sub_time2_dust;
    TextView sub_time2_text_dust;

    ImageView sub_time3_weather_icon;
    TextView sub_time3_weather;
    TextView sub_time3_temp;
    ImageView sub_time3_humi;
    TextView sub_time3_text_humi;
    ImageView sub_time3_dust;
    TextView sub_time3_text_dust;

    ImageView sub_time4_weather_icon;
    TextView sub_time4_weather;
    TextView sub_time4_temp;
    ImageView sub_time4_humi;
    TextView sub_time4_text_humi;
    ImageView sub_time4_dust;
    TextView sub_time4_text_dust;

    TextView sub_latitude;
    TextView sub_longitude;
    Button sub_btnGetWeather;
    String strDust="";
    String strUV="";
    String strCity="";
    String lat1="";
    String lot1="";
    String get_desc1="Clear Sky";
    String get_icon="01d";

    ImageView popup_weather_close;
    SharePreferenceUtil share;



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

        setContentView(R.layout.popup_weather);

        sub_city = (TextView) findViewById(R.id.sub_city);
        sub_temp = (TextView) findViewById(R.id.sub_temp);
        sub_tempUnit = (TextView) findViewById(R.id.sub_tempUnit);
        sub_tempUnit1 = (TextView) findViewById(R.id.sub_tempUnit1);
        sub_tempImage = (ImageView) findViewById(R.id.sub_tempImage);
        sub_weatherStatus = (TextView) findViewById(R.id.sub_weatherStatus);
        sub_img_humi = (ImageView) findViewById(R.id.sub_img_humi);
        sub_text_humi = (TextView) findViewById(R.id.sub_text_humi);
        sub_img_wind = (ImageView) findViewById(R.id.sub_img_wind);
        sub_text_wind = (TextView) findViewById(R.id.sub_text_wind);
        sub_text_wind_unit = (TextView) findViewById(R.id.sub_text_wind_unit);
        sub_img_uv = (ImageView) findViewById(R.id.sub_img_uv);
        sub_text_uv = (TextView) findViewById(R.id.sub_text_uv);
        sub_img_dust = (ImageView) findViewById(R.id.sub_img_dust);
        sub_text_dust = (TextView) findViewById(R.id.sub_text_dust);
        sub_time1 = (TextView) findViewById(R.id.sub_time1);
        sub_time2 = (TextView) findViewById(R.id.sub_time2);
        sub_time3 = (TextView) findViewById(R.id.sub_time3);
        sub_time4 = (TextView) findViewById(R.id.sub_time4);
        sub_time1_weather_icon = (ImageView) findViewById(R.id.sub_time1_weather_icon);
        sub_time1_weather = (TextView) findViewById(R.id.sub_time1_weather);
        sub_time1_temp = (TextView) findViewById(R.id.sub_time1_temp);
        sub_time1_humi = (ImageView) findViewById(R.id.sub_time1_humi);
        sub_time1_text_humi = (TextView) findViewById(R.id.sub_time1_text_humi);
        sub_time1_dust = (ImageView) findViewById(R.id.sub_time1_dust);
        sub_time1_text_dust = (TextView) findViewById(R.id.sub_time1_text_dust);

        sub_time2_weather_icon = (ImageView) findViewById(R.id.sub_time2_weather_icon);
        sub_time2_weather = (TextView) findViewById(R.id.sub_time2_weather);
        sub_time2_temp = (TextView) findViewById(R.id.sub_time2_temp);
        sub_time2_humi = (ImageView) findViewById(R.id.sub_time2_humi);
        sub_time2_text_humi = (TextView) findViewById(R.id.sub_time2_text_humi);
        sub_time2_dust = (ImageView) findViewById(R.id.sub_time2_dust);
        sub_time2_text_dust = (TextView) findViewById(R.id.sub_time2_text_dust);

        sub_time3_weather_icon = (ImageView) findViewById(R.id.sub_time3_weather_icon);
        sub_time3_weather = (TextView) findViewById(R.id.sub_time3_weather);
        sub_time3_temp = (TextView) findViewById(R.id.sub_time3_temp);
        sub_time3_humi = (ImageView) findViewById(R.id.sub_time3_humi);
        sub_time3_text_humi = (TextView) findViewById(R.id.sub_time3_text_humi);
        sub_time3_dust = (ImageView) findViewById(R.id.sub_time3_dust);
        sub_time3_text_dust = (TextView) findViewById(R.id.sub_time3_text_dust);

        sub_time4_weather_icon = (ImageView) findViewById(R.id.sub_time4_weather_icon);
        sub_time4_weather = (TextView) findViewById(R.id.sub_time4_weather);
        sub_time4_temp = (TextView) findViewById(R.id.sub_time4_temp);
        sub_time4_humi = (ImageView) findViewById(R.id.sub_time4_humi);
        sub_time4_text_humi = (TextView) findViewById(R.id.sub_time4_text_humi);
        sub_time4_dust = (ImageView) findViewById(R.id.sub_time4_dust);
        sub_time4_text_dust = (TextView) findViewById(R.id.sub_time4_text_dust);
        sub_latitude = (TextView) findViewById(R.id.sub_latitude);
        sub_longitude = (TextView) findViewById(R.id.sub_longitude);
        sub_btnGetWeather = (Button) findViewById(R.id.sub_btnGetWeather);

        popup_weather_close = (ImageView) findViewById(R.id.popup_weather_close);
        share = new SharePreferenceUtil(getApplicationContext());

        popup_weather_close.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    finish();
                }
                return true;//false;
            }
        });

        sub_btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                strDust = share.getSharedDust();
                strUV = share.getSharedUV();
                String lan = share.getSharedLanguage();
                if(lan.compareTo("ko")==0) {
                    strCity = share.getSharedCityKo();
                }else{
                    strCity = share.getSharedCityEn();
                }
                sub_text_dust.setText(strDust);
                sub_text_uv.setText(strUV);
                sub_city.setText(strCity);
                lat1 = share.getSharedLat();
                lot1 = share.getSharedLot();

                getWeather(view);
                //     getUV(view);
                //     getDust(view);
            }
        });
        this.setClick();
    }

    private void setClick(){
        sub_btnGetWeather.performClick();
    }

    private void timeZone(){

    }


    private void getWeather(View v){
        //    Double lat = 49.22;
        //    Double lot = 24.409;
        Geocoder mGeoCoder = new Geocoder(getApplicationContext());
        List<Address> address = null;
      //  String lat1 = sub_latitude.getText().toString();
      //  String lot1 = sub_longitude.getText().toString();
        String lat1 = share.getSharedLat();
        String lot1 = share.getSharedLot();
        Double lat = Double.valueOf(lat1).doubleValue();
        Double lot = Double.valueOf(lot1).doubleValue();
        String unit = "Imperial";
        String key = "EditYourKey";

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient4 = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(2,TimeUnit.MINUTES)
                .writeTimeout(15,TimeUnit.SECONDS)
                .build();
        okHttpClient4.dispatcher().setMaxRequests(3);

        Retrofit client4 = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .client(okHttpClient4)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface service4 = client4.create(ApiInterface.class);
//friendlyarm에서 못읽어와서 디폴트로 일단 함.
/*        try{
            address = mGeoCoder.getFromLocation(lat,lot,1);
            Log.d("geo", "onComplete:"+address.get(0).getLocality());
            Log.d("geo", "onComplete:"+address.get(0).getSubLocality());
            city.setText(address.get(0).getLocality()+"/"+address.get(0).getSubLocality());
        }catch(IOException e){
            e.printStackTrace();
            Log.d("error","onComplete:주소변환 실패");
        }*/
 /*       Call<WeatherDay> callToday = service.getToday(key,lat,lot);
        callToday.enqueue(new Callback<WeatherDay>(){
            @Override
          public void onResponse(Response<WeatherDay> response) {
                Log.e("enter", "onResponse");
                WeatherDay data = response.body();

                if(response.isSuccess()){
                    temperature.setText(String.valueOf(data.getTempInteger()));
                    Glide.with(MainActivity.this).load(data.getIconUrl()).into(tempImage);
                    Log.e("Success", new Gson().toJson(response.body()));
                }else{
                    Log.e("unSuccess", new Gson().toJson(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("test error",t.toString());
            }
        });*/


        Call<WeatherForecast> callForecast = service4.getForecast(key,lat,lot,unit);
        callForecast.enqueue(new Callback<WeatherForecast>(){
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                Log.e("enter","onResponse");
                WeatherForecast data = response.body();

                long millis = new Date().getTime();

                long output=0;

                int i=0;
                int j=0;

                if(response.isSuccessful()){

                    for(WeatherDay day : data.getItems()){
                        String getdata2 = data.getItems().get(i).getTimetxt();
                        DateFormat formatter;
                        String datet2 = null;
                        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date datet = (Date) formatter.parse(getdata2);
                            output = datet.getTime();

                            Date datet1 = new Date(output);
                            SimpleDateFormat datet3 = new SimpleDateFormat("h a", Locale.US);//getDefault());//Locale.KOREAN);
                            SimpleDateFormat timeko = new SimpleDateFormat("H", Locale.US);
                            SimpleDateFormat timeam = new SimpleDateFormat("a", Locale.US);
                            SimpleDateFormat timez = new SimpleDateFormat("Z");
                            datet2 = datet3.format(datet1);
                            String timeKo = timeko.format(datet1);
                            String timeAm = timeam.format(datet1);
                            String timeZ = timez.format(datet1);
                            byte[] timeb = timeZ.getBytes();
                            byte[] timeb1 = {0, 0, 0};
                            timeb1[0] = timeb[1];
                            timeb1[1] = timeb[2];
                            String times = timeb1.toString();
                            int temp = Integer.parseInt(timeKo);
                            //int temp1 = Integer.parseInt(times);
                            int temp1 = 0;
                            if (timeb[0] == 45) {
                                temp1 = -(timeb1[0] - 48) * 10 + -(timeb1[1] - 48);
                            } else {
                                temp1 = (timeb1[0] - 48) * 10 + (timeb1[1] - 48);
                            }
                            //if(Locale.get)
                           // temp = (temp+9);
                            temp = (temp+temp1);
                            temp = temp %24;
                            if(temp >= 12) {
                                if (temp != 12) {
                                    temp = temp % 12;
                                }
                                datet2 = Integer.toString(temp) + " PM";
                            }else{
                                if(temp == 0){
                                    datet2 = "12 AM";
                                }else{
                                    datet2 = Integer.toString(temp) + " AM";
                                }
                            }

                      /*      int sub_time = Integer.parseInt(datet2);
                            if(sub_time>12){
                                sub_time = sub_time - 12;
                                datet2 = Integer.toString(sub_time)+"PM";
                            }else if(sub_time == 12){
                                datet2 = Integer.toString(sub_time)+"PM";

                            }else{
                                datet2 = Integer.toString(sub_time)+"AM";
                            }*/

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        Locale systemLocale = getApplicationContext().getResources().getConfiguration().locale;
                        String lan = systemLocale.getLanguage();
                        //     if(millis <= output)
                        {
                            if(j==0) {

                                get_desc1 = day.getDesc();
                                get_icon = day.getIcon();
                                if(lan.compareTo("ko") == 0) {
                                 //   get_icon = day.getIcon();
                                    get_description(get_icon);
                                }
                                sub_weatherStatus.setText(get_desc1);
                                //sub_weatherStatus.setText(day.getDesc());
                                sub_temp.setText(String.valueOf(day.getTempInteger()));
                                sub_text_humi.setText(String.valueOf(day.getHumi()));
                                sub_text_wind.setText(String.valueOf(day.getSpeed()));
                                //   city.setText(String.valueOf(day.getCity()));
                                String str = day.getTimetxt();
                                int img_id = set_icon(get_icon);
                                sub_tempImage.setImageResource(img_id);
                                //Glide.with(getApplicationContext()).load(day.getIconUrl()).into(sub_tempImage);

                                sub_time1_weather.setText(get_desc1);
                                //sub_time1_weather.setText(day.getDesc());
                                sub_time1_weather_icon.setImageResource(img_id);
                                //Glide.with(getApplicationContext()).load(day.getIconUrl()).into(sub_time1_weather_icon);
                                sub_time1_temp.setText(day.getTempMin() +"°/" + day.getTempMax() +"°");
                                sub_time1_text_humi.setText(day.getHumi());
                                //sub_time1_humi.setImageResource();
                                sub_time1_text_dust.setText(strDust);
                                sub_time1.setText(datet2);


                            } if(j == 1){
                                get_desc1 = day.getDesc();
                                get_icon = day.getIcon();
                                if(lan.compareTo("ko") == 0) {
                               //     get_icon = day.getIcon();
                                    get_description(get_icon);
                                }
                                sub_time2_weather.setText(get_desc1);
                                //sub_time2_weather.setText(day.getDesc());
                                int img_id = set_icon(get_icon);
                                sub_time2_weather_icon.setImageResource(img_id);
                                //Glide.with(getApplicationContext()).load(day.getIconUrl()).into(sub_time2_weather_icon);
                                sub_time2_temp.setText(day.getTempMin() +"°/" + day.getTempMax() +"°");
                                sub_time2_text_humi.setText(day.getHumi());
                                //sub_time1_humi.setImageResource();
                                // sub_time1_text_dust.setText();
                                sub_time2.setText(datet2);
                            } if (j == 2){
                                get_desc1 = day.getDesc();
                                get_icon = day.getIcon();
                                if(lan.compareTo("ko") == 0) {
                                //    get_icon = day.getIcon();
                                    get_description(get_icon);
                                }
                                sub_time3_weather.setText(get_desc1);
                               // sub_time3_weather.setText(day.getDesc());
                                int img_id = set_icon(get_icon);
                                sub_time3_weather_icon.setImageResource(img_id);
                            //    Glide.with(getApplicationContext()).load(day.getIconUrl()).into(sub_time3_weather_icon);
                                sub_time3_temp.setText(day.getTempMin() +"°/" + day.getTempMax() +"°");
                                sub_time3_text_humi.setText(day.getHumi());
                                //sub_time1_humi.setImageResource();
                                // sub_time1_text_dust.setText();
                                sub_time3.setText(datet2);
                            } if (j == 3){
                                get_desc1 = day.getDesc();
                                get_icon = day.getIcon();
                                if(lan.compareTo("ko") == 0) {
                                //    get_icon = day.getIcon();
                                    get_description(get_icon);
                                }
                                sub_time4_weather.setText(get_desc1);
                              //  sub_time4_weather.setText(day.getDesc());
                                int img_id = set_icon(get_icon);
                                sub_time4_weather_icon.setImageResource(img_id);
                          //      Glide.with(getApplicationContext()).load(day.getIconUrl()).into(sub_time4_weather_icon);
                                sub_time4_temp.setText(day.getTempMin() +"°/" + day.getTempMax() +"°");
                                sub_time4_text_humi.setText(day.getHumi());
                                //sub_time1_humi.setImageResource();
                                // sub_time1_text_dust.setText();
                                sub_time4.setText(datet2);
                            } if (j >= 4){
                                break;
                            }
                                j++;
                            }


                            //i++;
                            i=i+2;
                        // }
                    }
                    Log.d("Success", new Gson().toJson(response.body()));
                }else{
                    Log.e("unSuccess", new Gson().toJson(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                Log.e("test error",t.toString());
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

    public void get_description(String s) {
        if(s.contains("01")) {
            get_desc1 = "맑음";
        } else if(s.contains("02")) {
            get_desc1 = "구름 조금";
        } else if(s.contains("03")) {
            get_desc1 = "구름 많음";
        } else if(s.contains("04")) {
            get_desc1 = "흐림";
        } else if(s.contains("09")) {
            get_desc1 = "한때 비";
        } else if(s.contains("10")) {
            get_desc1 = "비";
        } else if(s.contains("11")) {
            get_desc1 = "천둥번개";
        } else if(s.contains("13")) {
            get_desc1 = "눈";
        } else if(s.contains("09")) {
            get_desc1 = "안개";
        } else {
            get_desc1 = "맑음";
        }

        /*if (s.compareTo("clear sky") == 0) {
            get_desc1 = "맑음";
        } else if (s.compareTo("few clouds") == 0) {
            get_desc1 = "구름 조금";
        } else if (s.compareTo("scattered clouds") == 0) {
            get_desc1 = "구름 많음";
        } else if (s.compareTo("broken clouds") == 0) {
            get_desc1 = "흐림";
        } else if (s.compareTo("shower rain") == 0) {
            get_desc1 = "한때 비 ";
        } else if (s.compareTo("rain") == 0) {
            get_desc1 = "비";
        } else if (s.compareTo("thunderstorm") == 0) {
            get_desc1 = "천둥번개";
        } else if (s.compareTo("snow") == 0) {
            get_desc1 = "눈";
        } else if (s.compareTo("mist") == 0) {
            get_desc1 = "안개";
        } else {
            get_desc1 = "맑음";
        }*/
    }


}
