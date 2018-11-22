package com.i4vine.ryufragment;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.List;

import static java.lang.Math.round;

public class WeatherDay {
    public class WeatherTemp{
        Double temp;
        Double temp_min;
        Double temp_max;
        Double pressure;
        Integer humidity;
    }

    public class WeatherDescription{
        String icon;
        String description;
    }

    public class WeatherWind{
        Double speed;
        Double deg;
    }


    @SerializedName("main")
    private WeatherTemp temp;

    @SerializedName("weather")
    private List<WeatherDescription> description;

    @SerializedName("wind")
    private WeatherWind wind;

    @SerializedName("city")
    private String name;

    @SerializedName("dt")
    private long timestamp;

    @SerializedName("dt_txt")
    private String timetxt;

    public WeatherDay(WeatherTemp temp, List<WeatherDescription> desc, WeatherWind wind, String name,String timetxt, long timestamp){
        this.temp = temp;
        this.description = desc;
        this.wind = wind;
        this.name = name;
        this.timetxt = timetxt;
        this.timestamp = timestamp;
    }

    public Calendar getDate(){
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(timestamp * 1000);
        return date;
    }

    public String getTemp() { return String.valueOf(temp.temp); }

    public String getTempMin() { return String.valueOf(temp.temp_min.intValue()); }

    public String getTempMax() { return String.valueOf(temp.temp_max.intValue()); }

    public String getTempInteger(){
        //return String.valueOf(round((temp.temp.intValue()-273.15)*1.8+32.00)); //default
        return String.valueOf(round(temp.temp.intValue())); //imperial
    }

    public String getTempWithDegree() { return String.valueOf(temp.temp.intValue()) + "\00B0"; }

    public String getHumi() { return String.valueOf(temp.humidity); }

    public String getSpeed() { return String.valueOf(wind.speed); }

    public String getDeg() { return String.valueOf(wind.deg); }

    public String getCity() { return name; }

    public String getTimetxt() { return timetxt; }

    public long getTimestamp() { return timestamp; }

    public String getDesc() { return description.get(0).description; }

    public String getIcon() { return description.get(0).icon; }

    public String getIconUrl(){
        return "http://openweathermap.org/img/w/" + description.get(0).icon + ".png";
    }
}
