package com.i4vine.ryufragment;

import com.google.gson.annotations.SerializedName;

public class ResponseAqi {
    public class AqiTime{
        //    long v;
        //     String s;
        //    String tz;
    }

    public class AqiCity{
        String name;
        //   String url;
        //   @SerializedName("geo")
        //   List<Double> geo;
    }

    public class AqiData{
        //    public int idx;
        public int aqi;
        //     @SerializedName("time")
        //     public AqiTime aqiTime;
        @SerializedName("city")
        public AqiCity aqiCity;
    }



    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private AqiData data;



    public ResponseAqi(String status, AqiData data){
        this.status = status;
        this.data = data;
    }


    public String getAqiStatus() { return status; }
    public int getAqiValue() { return data.aqi; }
    //   public long getAqiTimeStamp() { return data.aqiTime.v; }
    //   public String getAqiTime() { return data.aqiTime.s; }
    //   public String getAqiTimeZone() { return data.aqiTime.tz; }

    public String getAqiCity()  { return data.aqiCity.name; }
    //  public String getAqiUrl() { return data.aqiCity.url; }
    //   public List<Double> getAqiGeo() { return data.aqiCity.geo; }


    public AqiData getAqiData() { return data; }

}
