package com.i4vine.ryufragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {
    public static final String APP_SHARED_PREFS = "thisApp.SharedPreference";

    private SharedPreferences sharePreferences;
    private SharedPreferences.Editor editor;

    public SharePreferenceUtil(Context context){
        this.sharePreferences = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.editor = sharePreferences.edit();
    }

    public void setSharedTest(String test){
        editor.putString("test",test);
        editor.commit();
    }

    public String getSharedTest(){
        return sharePreferences.getString("test","default");
    }

    public void setTodayPlan(String todayPlan){
        editor.putString("todayPlan", todayPlan);
        editor.commit();
    }

    public String getTodayPlan() { return sharePreferences.getString("todayPlan", "no plan"); }

    public void setSharedDust(String test){
        editor.putString("dust",test);
        editor.commit();
    }

    public String getSharedDust(){
        return sharePreferences.getString("dust","65");
    }

    public void setSharedUV(String test){
        editor.putString("uv",test);
        editor.commit();
    }

    public String getSharedUV(){
        return sharePreferences.getString("uv","0");
    }

    public void setSharedCityKo(String test){
        editor.putString("city_ko",test);
        editor.commit();
    }

    public String getSharedCityKo(){
        return sharePreferences.getString("city_ko","라스베가스 / 네바다 주");
    }

    public void setSharedCityEn(String test){
        editor.putString("city_en",test);
        editor.commit();
    }

    public String getSharedCityEn(){
        return sharePreferences.getString("city_en","Las Vegas / NV");
    }


    public void setSharedLat(String test){
        editor.putString("lat",test);
        editor.commit();
    }

    public String getSharedLat(){
        return sharePreferences.getString("lat","36.171005");
    }

    public void setSharedLot(String test){
        editor.putString("lot",test);
        editor.commit();
    }

    public String getSharedLot(){
        return sharePreferences.getString("lot","-115.170768");
    }


    public void setSharedLanguage(String test){
        editor.putString("language",test);
        editor.commit();
    }

    public String getSharedLanguage(){
        return sharePreferences.getString("language","en");
    }

    public void setSharedPlanIdx(int test){
        editor.putInt("planidx",test);
        editor.commit();
    }

    public int getSharedPlanIdx(){
        return sharePreferences.getInt("planidx",0);
    }

    public void setSharedPlan(String test){
        editor.putString("plan", test);
        editor.commit();
    }

    public String getSharedPlan() { return sharePreferences.getString("plan", "no plan"); }

    public void setSharedWeather(String test){
        editor.putString("state", test);
        editor.commit();
    }

    public String getSharedWeather() { return sharePreferences.getString("state", "sunny"); }

    public void setSharedMinTemp(String test){
        editor.putString("minTemp", test);
        editor.commit();
    }

    public String getSharedMinTemp() { return sharePreferences.getString("minTemp", "0"); }

    public void setSharedMaxTemp(String test){
        editor.putString("maxTemp", test);
        editor.commit();
    }

    public String getSharedMaxTemp() { return sharePreferences.getString("maxTemp", "10"); }

    public void setSharedHumi(String test){
        editor.putString("humidity", test);
        editor.commit();
    }

    public String getSharedHumi() { return sharePreferences.getString("humidity", "30"); }

    public void setSharedLive(int test){
        editor.putInt("live", test);
        editor.commit();
    }

    public int getSharedLive() { return sharePreferences.getInt("live", 1); }



}
