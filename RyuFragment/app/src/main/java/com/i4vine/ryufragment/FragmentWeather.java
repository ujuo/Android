package com.i4vine.ryufragment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class FragmentWeather extends Fragment {
    SharePreferenceUtil share;
    TextView city;
    TextView weatherStatus;
    TextView temperature;
    TextView tempUnit;
    TextView humidity;
    TextView wind;
    TextView uv;
    TextView dust;
    ImageView tempImage;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    //    ImageView imageView5;
    TextView latitude;
    TextView longitude;
    Button btnGetWeather;
    Button btnGetDust;
    Button btnGetUV;
    private static final String TAG = "MainActivity";
    Integer idx=0;
    String strUV;
    String strDust;
    String strTemp;
    String strHumi;
    String strWind;
    String strCity;
    String strWeatherStatus;
    String location_lat;
    String location_lot;
    String city_en;
    String city_ko;
    String get_desc="Clear Sky";
    String get_icon1="01d";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_weather,container,false);

        city = (TextView) rootView.findViewById(R.id.city);
        weatherStatus = (TextView) rootView.findViewById(R.id.weatherStatus);
        temperature = (TextView) rootView.findViewById(R.id.temperature);
        tempUnit = (TextView) rootView.findViewById(R.id.tempUnit);
        humidity = (TextView) rootView.findViewById(R.id.humidity);
        wind = (TextView) rootView.findViewById(R.id.wind);
        uv = (TextView) rootView.findViewById(R.id.uv);
        dust = (TextView) rootView.findViewById(R.id.dust);
        tempImage = (ImageView) rootView.findViewById(R.id.tempImage);
        imageView1 = (ImageView) rootView.findViewById(R.id.imageView1);
        imageView2 = (ImageView) rootView.findViewById(R.id.imageView2);
        imageView3 = (ImageView) rootView.findViewById(R.id.imageView3);
        imageView4 = (ImageView) rootView.findViewById(R.id.imageView4);
        // imageView5 = (ImageView) findViewById(R.id.imageView5);
        latitude = (TextView) rootView.findViewById(R.id.latitude);
        longitude = (TextView) rootView.findViewById(R.id.longitude);
        btnGetWeather = (Button) rootView.findViewById(R.id.btnGetWeather);
        btnGetDust = (Button) rootView.findViewById(R.id.btnGetDust);
        btnGetUV = (Button) rootView.findViewById(R.id.btnGetUV);
        share = new SharePreferenceUtil(getContext());
        getLocation();

        Log.d("==================",city_en+city_ko);

        btnGetWeather.setOnClickListener(new ViewGroup.OnClickListener(){
            @Override
            public void onClick(View view) {
               // setGetWeatherBtn(view);
                getWeather(view);

            }
        });
        btnGetDust.setOnClickListener(new ViewGroup.OnClickListener(){
            @Override
            public void onClick(View view) {
                getDust(view);
            }
        });

        btnGetUV.setOnClickListener(new ViewGroup.OnClickListener(){
            @Override
            public void onClick(View view) {
                getUV(view);
            }
        });




        touchListener(rootView);

        return rootView;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }


    public void setClick(){
        btnGetWeather.performClick();
    }
    public void setClick1(){
        btnGetDust.performClick();
    }
    public void setClick2(){
        btnGetUV.performClick();
    }

    public void getLocation(){
  /*      String folder = "temp";
        String path = Environment.getExternalStorageDirectory().getPath();
        String subpath = path+"/"+folder+"/";
        String text = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp";
        File directory = new File(text,"location.txt");
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
                    location_lat = lat_loc[0];
                    location_lot = lat_loc[1];
                }else{
                    location_lat = "36.171005";
                location_lot = "-115.170768";
                city_en = "LasVegas / NV";
                city_ko = "라스베가스 / 네바다주";
                share.setSharedCityEn(city_en);
                share.setSharedCityKo(city_ko);
                share.setSharedLat(location_lat);
                share.setSharedLot(location_lot);
                }
            }else{*/
                location_lat = "36.171005";
                location_lot = "-115.170768";
                city_en = "LasVegas / NV";
                city_ko = "라스베가스 / 네바다주";
                share.setSharedCityEn(city_en);
                share.setSharedCityKo(city_ko);
                share.setSharedLat(location_lat);
                share.setSharedLot(location_lot);

         //   }

          //  buffer.close();

     /*   }catch(FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "Location file not found.",Toast.LENGTH_SHORT).show();
        }catch(IOException e){
            e.printStackTrace();
        }*/

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
        if (s.contains("01")) {
            get_desc = "맑음";
        } else if (s.contains("02")) {
            get_desc = "구름 조금";
        } else if (s.contains("03")) {
            get_desc = "구름 많음";
        } else if (s.contains("04")) {
            get_desc = "흐림";
        } else if (s.contains("09")) {
            get_desc = "한때 비";
        } else if (s.contains("10")) {
            get_desc = "비";
        } else if (s.contains("11")) {
            get_desc = "천둥번개";
        } else if (s.contains("13")) {
            get_desc = "눈";
        } else if (s.contains("50")) {
            get_desc = "안개";
        } else {
            get_desc = "맑음";
        }
    }


    public void getWeather(View v){
        //    Double lat = 49.22;
        //    Double lot = 24.409;
        Geocoder mGeoCoder = new Geocoder(getActivity().getApplicationContext());
        List<Address> address = null;
        String lat1 = share.getSharedLat();//location_lat;//latitude.getText().toString();
        String lot1 = share.getSharedLot();//location_lot;// longitude.getText().toString();
        Double lat = Double.valueOf(lat1).doubleValue();
        Double lot = Double.valueOf(lot1).doubleValue();
        String unit = "Imperial";
        String key = "EditYourKey";

     /*   Retrofit client = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(2,TimeUnit.MINUTES)
                .writeTimeout(15,TimeUnit.SECONDS)
                .build();
        okHttpClient.dispatcher().setMaxRequests(3);

        Retrofit client = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface service = client.create(ApiInterface.class);
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


        Call<WeatherForecast> callForecast = service.getForecast(key,lat,lot,unit);
        callForecast.enqueue(new Callback<WeatherForecast>(){
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                Log.e("enter","onResponse");
                WeatherForecast data = response.body();

                long millis = new Date().getTime();
                int i=0;

                if(response.isSuccessful()){
                    for(WeatherDay day : data.getItems()){

                        String getdata2 = data.getItems().get(i).getTimetxt();
                        DateFormat formatter;

                        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date datet = (Date)formatter.parse(getdata2);
                            long output = datet.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        Locale systemLocale = getActivity().getApplicationContext().getResources().getConfiguration().locale;
                        String lan = systemLocale.getLanguage();
                        get_desc = day.getDesc();
                        get_icon1 = day.getIcon();
                        if(lan.compareTo("ko") == 0) {
                           // get_icon1 = day.getIcon();
                            get_description(get_icon1);
                        }
                        weatherStatus.setText(get_desc);
                        share.setSharedWeather(get_desc);
                        share.setSharedMaxTemp(day.getTempMax());
                        share.setSharedMinTemp(day.getTempMin());
                        share.setSharedHumi(day.getHumi());
                        Log.d("=============weather",get_desc);
                        Log.d("===========tempmax",day.getTempMax());
                        Log.d("=============tempmin",day.getTempMin());
                        Log.d("========humi", day.getHumi());

                        //weatherStatus.setText(day.getDesc());
                        temperature.setText(String.valueOf(day.getTempInteger()));
                        humidity.setText(String.valueOf(day.getHumi()));
                        wind.setText(String.valueOf(day.getSpeed()));
                     /*   strWeatherStatus = day.getDesc();
                        strTemp = String.valueOf(day.getTempInteger());
                        strHumi = String.valueOf(day.getHumi());
                        strWind = String.valueOf(day.getSpeed());*/
                        //  city.setText(String.valueOf(day.getCity()));
                        String str = day.getTimetxt();
                        int img_id = set_icon(get_icon1);
                        tempImage.setImageResource(img_id);
                      //  Glide.with(getActivity().getApplicationContext()).load(day.getIconUrl()).into(tempImage);
                        break;

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

    public void getDust(View v){
        String lat = share.getSharedLat();//location_lat;//"37.51";
        String lot = share.getSharedLot();//location_lot;//"127.04377";
        String geo = lat+";"+lot;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//BASIC);//BODY);


        OkHttpClient okHttpClient1 = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(1,TimeUnit.MINUTES)
                .readTimeout(2,TimeUnit.MINUTES)
                .writeTimeout(50,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        okHttpClient1.dispatcher().setMaxRequests(3);

        Retrofit client1 = new Retrofit.Builder()
                .baseUrl("https://api.waqi.info/")
                .client(okHttpClient1)
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterfaceDust service1 = client1.create(ApiInterfaceDust.class);
        Call<ResponseAqi> callAqi = service1.getAQI(geo);


        callAqi.enqueue(new Callback<ResponseAqi>() {
            @Override
            public void onResponse(Call<ResponseAqi> call, Response<ResponseAqi> response) {
                Log.e("enter", "=========================================onResponse");
                ResponseAqi data = response.body();

                if(response.isSuccessful()){
                    String str1 = data.getAqiStatus();
                    if(str1.compareTo("ok") == 0) {

                        strDust = Integer.toString(data.getAqiValue());
                        share.setSharedDust(strDust);
                        dust.setText(strDust);
                        ResponseAqi.AqiData str = data.getAqiData();
                        Locale systemLocale = getActivity().getApplicationContext().getResources().getConfiguration().locale;
                        String lan = systemLocale.getLanguage();
                        /*///////////////


                        String str6 = data.getAqiCity();
                        String str7="";
                        String str8="";
                        if(lan.compareTo("ko") == 0){
                            String[] strArray = str6.split("South Korea ");
                            if(strArray.length >= 2){
                                String[] test = strArray[1].split(" ");
                                String[] c = test[0].split("대한민국");
                                str7 = test[1] + ", " + c[1];
                                str8 = test[1] + " /" + c[1];
                            }else{
                                String[] strArray1 = str6.split(",");
                                str7 = strArray1[0]+", "+strArray1[1];
                                str8 = strArray1[0]+" /"+strArray1[1];
                            }

                        }else{
                            String[] strArray = str6.split(",");
                            str7 = strArray[0]+", "+strArray[1];
                            str8 = strArray[0]+" /"+strArray[1];
                        }

                        city.setText(str7);
                        share.setSharedCity(str8);
                        ///////////////////////////*/
                        String str_city_ko = share.getSharedCityKo();
                        String str_city_en = share.getSharedCityEn();
                        if(lan.compareTo("ko") == 0) {
                            String city_arr[] = str_city_ko.split("/");
                            city.setText(city_arr[0] + ", " + city_arr[1]);
                        }else{

                            String city_arr[] = str_city_en.split("/");
                            city.setText(city_arr[0] + ", " + city_arr[1]);

                        }

                        // strCity = data.getAqiCity();

                        //String str2 = data.getAqiTime();
                        //String str3 = data.getAqiTimeZone();
                        //String str4 = data.getAqiUrl();
                        //long l = data.getAqiTimeStamp();
                        //List<Double> str5 = data.getAqiGeo();

                        Log.e("===============Success", new Gson().toJson(response.body()));
                    }else{
                        Log.e("=============success","=========response fail");
                    }

                }
                else{
                    Log.e("==============unSuccess", new Gson().toJson(response.errorBody()));
                }

                if (response.errorBody() != null) {

                    try {
                        Log.e("errorResponse","" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (response.message() != null){

                    Log.e("responseMessage","" + response.message());

                }
            }

            @Override
            public void onFailure(Call<ResponseAqi> call, Throwable t) {
                Log.e("=============test error",t.toString());
            }
        });
    }

    public void getUV(View v){
        location_lot = share.getSharedLot();
        location_lat = share.getSharedLat();
        double lat = Double.valueOf(location_lat).doubleValue();//37.51;
        double lot = Double.valueOf(location_lot).doubleValue();//127.04377;
        String dt = "2018-10-07:19:20:59.000Z";
        String token = "EditYourKey";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        Date currentTime_1 = new Date();
        String dt1 = formatter.format(currentTime_1);
        String[] dt2 = dt1.split(" ");
        dt = dt2[0]+"T"+dt2[1];


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//BASIC);//BODY);


        OkHttpClient okHttpClient2 = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(1,TimeUnit.MINUTES)
                .readTimeout(2,TimeUnit.MINUTES)
                .writeTimeout(15,TimeUnit.SECONDS)
                .build();
        okHttpClient2.dispatcher().setMaxRequests(3);

        Retrofit client2 = new Retrofit.Builder()
                .baseUrl("https://api.openuv.io/")
                .client(okHttpClient2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterfaceUV service2 = client2.create(ApiInterfaceUV.class);

        Call<ResponseUV> callUV = service2.getUV(lat,lot,dt,token);
        callUV.enqueue(new Callback<ResponseUV>() {
            @Override
            public void onResponse(Call<ResponseUV> call, Response<ResponseUV> response) {
                Log.e("enter", "onResponse");
                ResponseUV data = response.body();

                if(response.isSuccessful()){
                    String str3 = Double.toString(data.getUvValue());
                    share.setSharedUV(str3);
                    uv.setText(str3);
                    //    strUV = Double.toString(data.getUvValue());
                    String str = data.getUvMax();
                    String str1 = data.getUvMax_time();
                    String str2 = data.getUvTime();
                    Log.e("Success", new Gson().toJson(response.body()));
                }else{
                    Log.e("unSuccess", new Gson().toJson(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<ResponseUV> call, Throwable t) {
                Log.e("test error",t.toString());
            }
        });



    }
    private void touchListener(ViewGroup view){
        view.setOnTouchListener(new ViewGroup.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){
                    Toast.makeText(getActivity(), "Popup weather",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), PopupWeather.class);
                    startActivity(i);
                }
                return true;//false;
            }
        });
    }
}
