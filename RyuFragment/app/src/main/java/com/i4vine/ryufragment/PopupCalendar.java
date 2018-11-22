package com.i4vine.ryufragment;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.calendar.CalendarScopes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PopupCalendar extends Activity{
    ScrollView scroll_cal;
    TextView day1;
    TextView day2;
    TextView day3;
    TextView day4;
    TextView day5;
    TextView day6;
    TextView day7;
    TextView cal_month;
    TextView day_1;
    TextView day_2;
    TextView day_3;
    TextView day_4;
    TextView day_5;
    TextView day_6;
    TextView day_7;
    TextView plan_time0;
    TextView plan_desc0;
    Button btn_fashion0;
    TextView plan_time1;
    TextView plan_desc1;
    Button btn_fashion1;
    TextView plan_time2;
    TextView plan_desc2;
    Button btn_fashion2;
    TextView plan_time3;
    TextView plan_desc3;
    Button btn_fashion3;
    TextView plan_time4;
    TextView plan_desc4;
    Button btn_fashion4;
    TextView plan_time5;
    TextView plan_desc5;
    Button btn_fashion5;
    TextView plan_time6;
    TextView plan_desc6;
    Button btn_fashion6;
    TextView plan_time7;
    TextView plan_desc7;
    Button btn_fashion7;
    TextView plan_time8;
    TextView plan_desc8;
    Button btn_fashion8;
    TextView plan_time9;
    TextView plan_desc9;
    Button btn_fashion9;
    TextView plan_time10;
    TextView plan_desc10;
    Button btn_fashion10;
    TextView plan_time11;
    TextView plan_desc11;
    Button btn_fashion11;
    TextView plan_time12;
    TextView plan_desc12;
    Button btn_fashion12;
    TextView plan_time13;
    TextView plan_desc13;
    Button btn_fashion13;
    TextView plan_time14;
    TextView plan_desc14;
    Button btn_fashion14;
    TextView plan_time15;
    TextView plan_desc15;
    Button btn_fashion15;
    TextView plan_time16;
    TextView plan_desc16;
    Button btn_fashion16;
    TextView plan_time17;
    TextView plan_desc17;
    Button btn_fashion17;
    TextView plan_time18;
    TextView plan_desc18;
    Button btn_fashion18;
    TextView plan_time19;
    TextView plan_desc19;
    Button btn_fashion19;
    TextView plan_time20;
    TextView plan_desc20;
    Button btn_fashion20;
    TextView plan_time21;
    TextView plan_desc21;
    Button btn_fashion21;
    TextView plan_time22;
    TextView plan_desc22;
    Button btn_fashion22;
    TextView plan_time23;
    TextView plan_desc23;
    Button btn_fashion23;

    ImageView popup_calendar_close;

    private com.google.api.services.calendar.Calendar mService = null;
    private GoogleAccountCredential mCredential;
    //   private ProgressDialog mProgress;
    private static final int REQUEST_ACCOUNT_PICKER = 1000;
    private static final int REQUEST_AUTHORIZATION = 1001;
    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    SharePreferenceUtil share;
    String event_str = "";
    int day_interval=0;
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


        setContentView(R.layout.popup_calendar);

        scroll_cal = (ScrollView) findViewById(R.id.scroll_cal);

        day1 = (TextView) findViewById(R.id.day1);
        day2 = (TextView) findViewById(R.id.day2);
        day3 = (TextView) findViewById(R.id.day3);
        day4 = (TextView) findViewById(R.id.day4);
        day5 = (TextView) findViewById(R.id.day5);
        day6 = (TextView) findViewById(R.id.day6);
        day7 = (TextView) findViewById(R.id.day7);


        cal_month = (TextView) findViewById(R.id.cal_month);
        day_1 = (TextView) findViewById(R.id.day_1);
        day_2 = (TextView) findViewById(R.id.day_2);
        day_3 = (TextView) findViewById(R.id.day_3);
        day_4 = (TextView) findViewById(R.id.day_4);
        day_5 = (TextView) findViewById(R.id.day_5);
        day_6 = (TextView) findViewById(R.id.day_6);
        day_7 = (TextView) findViewById(R.id.day_7);

        plan_time0 = (TextView) findViewById(R.id.plan_time0);
        plan_time1 = (TextView) findViewById(R.id.plan_time1);
        plan_time2 = (TextView) findViewById(R.id.plan_time2);
        plan_time3 = (TextView) findViewById(R.id.plan_time3);
        plan_time4 = (TextView) findViewById(R.id.plan_time4);
        plan_time5 = (TextView) findViewById(R.id.plan_time5);
        plan_time6 = (TextView) findViewById(R.id.plan_time6);
        plan_time7 = (TextView) findViewById(R.id.plan_time7);
        plan_time8 = (TextView) findViewById(R.id.plan_time8);
        plan_time9 = (TextView) findViewById(R.id.plan_time9);
        plan_time10 = (TextView) findViewById(R.id.plan_time10);
        plan_time11 = (TextView) findViewById(R.id.plan_time11);
        plan_time12 = (TextView) findViewById(R.id.plan_time12);
        plan_time13 = (TextView) findViewById(R.id.plan_time13);
        plan_time14 = (TextView) findViewById(R.id.plan_time14);
        plan_time15 = (TextView) findViewById(R.id.plan_time15);
        plan_time16 = (TextView) findViewById(R.id.plan_time16);
        plan_time17 = (TextView) findViewById(R.id.plan_time17);
        plan_time18 = (TextView) findViewById(R.id.plan_time18);
        plan_time19 = (TextView) findViewById(R.id.plan_time19);
        plan_time20 = (TextView) findViewById(R.id.plan_time20);
        plan_time21 = (TextView) findViewById(R.id.plan_time21);
        plan_time22 = (TextView) findViewById(R.id.plan_time22);
        plan_time23 = (TextView) findViewById(R.id.plan_time23);

        plan_desc0 = (TextView) findViewById(R.id.plan_desc0);
        plan_desc1 = (TextView) findViewById(R.id.plan_desc1);
        plan_desc2 = (TextView) findViewById(R.id.plan_desc2);
        plan_desc3 = (TextView) findViewById(R.id.plan_desc3);
        plan_desc4 = (TextView) findViewById(R.id.plan_desc4);
        plan_desc5 = (TextView) findViewById(R.id.plan_desc5);
        plan_desc6 = (TextView) findViewById(R.id.plan_desc6);
        plan_desc7 = (TextView) findViewById(R.id.plan_desc7);
        plan_desc8 = (TextView) findViewById(R.id.plan_desc8);
        plan_desc9 = (TextView) findViewById(R.id.plan_desc9);
        plan_desc10 = (TextView) findViewById(R.id.plan_desc10);
        plan_desc11 = (TextView) findViewById(R.id.plan_desc11);
        plan_desc12 = (TextView) findViewById(R.id.plan_desc12);
        plan_desc13 = (TextView) findViewById(R.id.plan_desc13);
        plan_desc14 = (TextView) findViewById(R.id.plan_desc14);
        plan_desc15 = (TextView) findViewById(R.id.plan_desc15);
        plan_desc16 = (TextView) findViewById(R.id.plan_desc16);
        plan_desc17 = (TextView) findViewById(R.id.plan_desc17);
        plan_desc18 = (TextView) findViewById(R.id.plan_desc18);
        plan_desc19 = (TextView) findViewById(R.id.plan_desc19);
        plan_desc20 = (TextView) findViewById(R.id.plan_desc20);
        plan_desc21 = (TextView) findViewById(R.id.plan_desc21);
        plan_desc22 = (TextView) findViewById(R.id.plan_desc22);
        plan_desc23 = (TextView) findViewById(R.id.plan_desc23);

        btn_fashion0 = (Button) findViewById(R.id.btn_fashion0);
        btn_fashion1 = (Button) findViewById(R.id.btn_fashion1);
        btn_fashion2 = (Button) findViewById(R.id.btn_fashion2);
        btn_fashion3 = (Button) findViewById(R.id.btn_fashion3);
        btn_fashion4 = (Button) findViewById(R.id.btn_fashion4);
        btn_fashion5 = (Button) findViewById(R.id.btn_fashion5);
        btn_fashion6 = (Button) findViewById(R.id.btn_fashion6);
        btn_fashion7 = (Button) findViewById(R.id.btn_fashion7);
        btn_fashion8 = (Button) findViewById(R.id.btn_fashion8);
        btn_fashion9 = (Button) findViewById(R.id.btn_fashion9);
        btn_fashion10 = (Button) findViewById(R.id.btn_fashion10);

        popup_calendar_close = (ImageView) findViewById(R.id.popup_calendar_close);

        popup_calendar_close.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                finish();
                return true;//false;
            }
        });
     //   finish_popup_calendar();

        share = new SharePreferenceUtil(getApplicationContext());
        display_day();
        display_event(day_interval);
        day_1.setOnTouchListener(new ViewGroup.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    //str = share.getSharedTest();
                    event_str = display_event(0);
                }
                //    plan_desc9.setText(str);
                return true;
                //return false;
            }
        });

        day_2.setOnTouchListener(new ViewGroup.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    event_str = display_event(1);
                }
                return true;
                //return false;
            }
        });

        day_3.setOnTouchListener(new ViewGroup.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    event_str = display_event(2);
                }

                return true;//return false;
            }
        });

        day_4.setOnTouchListener(new ViewGroup.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    event_str = display_event(3);
                }

                return true;//return false;
            }
        });

        day_5.setOnTouchListener(new ViewGroup.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    event_str = display_event(4);
                }
                return true;//return false;
            }
        });

        day_6.setOnTouchListener(new ViewGroup.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    event_str = display_event(5);
                }
                return true;//return false;
            }
        });

        day_7.setOnTouchListener(new ViewGroup.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    event_str = display_event(6);
                }
                return true;//return false;
            }
        });
        //   mCredential = GoogleAccountCredential.usingOAuth2(getApplicationContext(), Arrays.asList(SCOPES))
        //             .setBackOff(new ExponentialBackOff());

        String ret_str;
        ret_str = share.getTodayPlan();
        if(ret_str.length() == 0) {

            display_event(day_interval);//0);
        }else{
            //TextView[] desc1 = {plan_desc0, plan_desc1, plan_desc2, plan_desc3, plan_desc4, plan_desc5, plan_desc6, plan_desc7, plan_desc8, plan_desc9};//,
            TextView[] t2 = {day1,day2,day3,day4,day5,day6,day7};
            TextView[] t2_ = {day_1, day_2, day_3, day_4, day_5, day_6, day_7};
            if(ret_str.compareTo("no plan") == 0){
                ret_str = "no plan";
                plan_desc0.setText(ret_str);
            }

            t2[day_interval].setBackgroundColor(0xFF707070);
            t2_[day_interval].setBackgroundColor(0xFF707070);

        }



    }

    void finish_popup_calendar(){
        popup_calendar_close.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    finish();
                }
                return true;//false;
            }
        });
    }

    void display_day(){
        Calendar now = Calendar.getInstance();
        Date getToday = now.getTime();

        SimpleDateFormat monthtest = new SimpleDateFormat("MMMM", Locale.US);
        String monthTest = monthtest.format(getToday);

        int day_start = 0;

        SimpleDateFormat timetest1 = new SimpleDateFormat("E d", Locale.US);
        String dayTest1 = timetest1.format(getToday);
        String[] strlist1 = dayTest1.split(" ");
        if(strlist1[0].compareTo("Mon") == 0){
            day_start = -1;
            day_interval = 1;
        }else if(strlist1[0].compareTo("Tue") == 0){
            day_start = -2;
            day_interval = 2;
        }else if(strlist1[0].compareTo("Wed") == 0){
            day_start = -3;
            day_interval = 3;
        }else if(strlist1[0].compareTo("Thu") == 0){
            day_start = -4;
            day_interval = 4;
        }else if(strlist1[0].compareTo("Fri") == 0){
            day_start = -5;
            day_interval = 5;
        }else if(strlist1[0].compareTo("Sat") == 0){
            day_start = -6;
            day_interval = 6;
        }else if(strlist1[0].compareTo("Sun") == 0){
            day_start = 0;
            day_interval = 0;
        }

        now.add(Calendar.DATE,day_start);
        Date getDay1 = now.getTime();
        now.add(Calendar.DATE,1);
        Date getDay2  = now.getTime();
        now.add(Calendar.DATE,1);
        Date getDay3  = now.getTime();
        now.add(Calendar.DATE,1);
        Date getDay4  = now.getTime();
        now.add(Calendar.DATE,1);
        Date getDay5  = now.getTime();
        now.add(Calendar.DATE,1);
        Date getDay6  = now.getTime();
        now.add(Calendar.DATE,1);
        Date getDay7  = now.getTime();
        now.add(Calendar.DATE,1);

   /*     now.add(Calendar.DATE,1);
        Date getDay2  = now.getTime();
        now.add(Calendar.DATE,1);
        Date getDay3  = now.getTime();
        now.add(Calendar.DATE,1);
        Date getDay4  = now.getTime();
        now.add(Calendar.DATE,1);
        Date getDay5  = now.getTime();
        now.add(Calendar.DATE,1);
        Date getDay6  = now.getTime();
        now.add(Calendar.DATE,1);
        Date getDay7  = now.getTime();
        now.add(Calendar.DATE,1);
*/
        now = Calendar.getInstance();
        now.add(Calendar.DATE,-1);
        Date getYesterday = now.getTime();

 /*       SimpleDateFormat timeweek = new SimpleDateFormat("E", Locale.US);
        String timeWeek = timeweek.format(getToday);

        SimpleDateFormat timetoday = new SimpleDateFormat("d", Locale.US);
        String timeToday = timetoday.format(getToday);*/
        Date[] d = {getDay1,getDay2, getDay3, getDay4, getDay5, getDay6, getDay7};//{getToday, getDay2, getDay3, getDay4, getDay5, getDay6, getDay7};
        TextView[] t = {day1,day2,day3,day4,day5,day6,day7};
        TextView[] t_ = {day_1, day_2, day_3, day_4, day_5, day_6, day_7};

        SimpleDateFormat timetest = new SimpleDateFormat("E d", Locale.US);
    /*    String timeTest = timetest.format(getToday);
        String[] liststr = timeTest.split(" ");

        day_1.setText(liststr[0]);
        day1.setText(liststr[1]);*/

        cal_month.setText(monthTest);
        for(int i=0; i< 7; i++){
            String dayTest = timetest.format(d[i]);
            String[] strlist = dayTest.split(" ");
            t[i].setText(strlist[0]);
            t_[i].setText(strlist[1]);
        }
    }

    long cal_interval_day(Date d){
        Calendar now = Calendar.getInstance();
        Date getToday = now.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String monthTest = format.format(getToday);
        String monthTest2 = format.format(d);
        long cal_days = 0;
        try{
            Date firstDate = format.parse(monthTest);
            Date secondDate = format.parse(monthTest2);
            long cal_interval = secondDate.getTime() - firstDate.getTime() ;
            cal_days = cal_interval / (24*60*60*1000);
        }catch(ParseException e){
            e.printStackTrace();
        }



        cal_days = Math.abs(cal_days);

        cal_days = cal_days+day_interval;
    /*    cal_days = cal_days+1;
        if(cal_days > 7)
            return -1;
        else
            return 1;*/
        return cal_days;

    }

    String display_event(int touchDay){
        int index=0;
        int index_today = 0;
        int idx = 0;
        String str;
        str = share.getSharedTest();
    //    share.setSharedPlan("");
    //    share.setSharedPlanIdx(0);
        TextView[] arrTime = {plan_time0, plan_time1, plan_time2, plan_time3, plan_time4, plan_time5, plan_time6, plan_time7, plan_time8, plan_time9};//,
        //  plan_time10, plan_time11, plan_time12, plan_time13, plan_time14, plan_time15, plan_time16, plan_time17, plan_time18, plan_time19,
        //   plan_time20, plan_time21, plan_time22, plan_time23};
        TextView[] arrDesc = {plan_desc0, plan_desc1, plan_desc2, plan_desc3, plan_desc4, plan_desc5, plan_desc6, plan_desc7, plan_desc8, plan_desc9};//,
        //     plan_desc10, plan_desc11, plan_desc12, plan_desc13, plan_desc14, plan_desc15, plan_desc16, plan_desc17, plan_desc18, plan_desc19,
        //     plan_desc20, plan_desc21, plan_desc22, plan_desc23};
        TextView[] t1 = {day1,day2,day3,day4,day5,day6,day7};
        TextView[] t1_ = {day_1, day_2, day_3, day_4, day_5, day_6, day_7};

        if(str.length() > 0) {
            ArrayList<String> list = new ArrayList<String>(Arrays.asList(str.split("\n")));
            String[] arry = list.toArray(new String[list.size()]);

            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ");
            Date datet2 = null;
            Date datet1 = null;
            long ret = 0;

            for (int i = 0; i < 10; i++) {
                arrTime[i].setText("");
                arrDesc[i].setText("");
                if (i < 7) {
                    t1[i].setBackgroundColor(0xffdddddd);
                    t1_[i].setBackgroundColor(0xffdddddd);
                }
            }

            SimpleDateFormat timeh1 = new SimpleDateFormat("hh:mm a", Locale.US);
            SimpleDateFormat timeh2 = new SimpleDateFormat("hh:mm a", Locale.US);
            SimpleDateFormat timeh3 = new SimpleDateFormat("HH:mm", Locale.US);
            SimpleDateFormat timeh4 = new SimpleDateFormat("H", Locale.US);
            SimpleDateFormat timee = new SimpleDateFormat("E", Locale.US);
            SimpleDateFormat timed = new SimpleDateFormat("d", Locale.US);

            String share_plan = "";
            int nodate_start = -1;
            int nodate_end = -1;

            try {
                for (int i = 0; i < list.size() / 4; i++) {
                    t1[touchDay].setBackgroundColor(0xFF707070);
                    t1_[touchDay].setBackgroundColor(0xFF707070);
                    if(arry[2+i*4].compareTo("null") == 0){
                        nodate_start = 1;
                    }else{
                        datet1 = (Date) formatter.parse(arry[2 + i * 4]);
                        nodate_start = 0;

                    }
                    if(arry[3+i*4].compareTo("null") == 0){
                        nodate_end = 1;
                    }else{
                        datet2 = (Date) formatter.parse(arry[3 + i * 4]);
                        nodate_end = 0;
                    }

                    String timeH1="";
                    String timeH2="";
                    String timeH3="";
                    String timeH4="";
                    if(nodate_start == 1){
                        if(arry[3+i*4].compareTo("null") == 0) {
                            timeH3 = "00:00 AM";
                        }
                    }else {
                        timeH1 = timeh1.format(datet1);
                        timeH3 = timeh3.format(datet1);
                        timeH4 = timeh4.format(datet1);
                    }

                    if(nodate_end == 1){
                        if(nodate_start == 0){
                            timeH2 = "11:59 PM";
                        }

                    }else{
                        timeH2 = timeh2.format(datet2);
                    }

             /*       int time_H4 = Integer.parseInt(timeH4);
                    String timeE = timee.format(datet1);
                    String timeD = timed.format(datet1);
                    String plan = arry[0 + i * 4] + "\n" + timeH1 + " - " + timeH2 + "\n" + arry[1 + i * 4];
                    String desc = arry[0 + i * 4];*/

                    String locat = arry[1 + i * 4];


                    if(nodate_start == 1 ){
                        if(locat.compareTo("null") == 0){
                            locat ="no location";
                        }



                     //   i++;
                    }else {
                        ret = cal_interval_day(datet1);

                        if (ret == touchDay) {
                            //int[] loc = new int[2];
                            //int pos;
                            String[] split1 = arry[0 + i * 4].split("\\\\");
                            int slen = split1.length;
                            String split2 = "";
                            for (int j = 0; j < slen; ) {
                                split2 += split1[j];
                                j = j + 2;
                                if (j < slen)
                                    split2 += "\n";
                            }


                            //arrTime[time_H4].setText(timeH1 + " - " + timeH2);
                            //arrTime[time_H4].setText(timeH3);
                            //arrDesc[time_H4].setText(arry[0+i*4]+"\n@ "+arry[1+i*4]);
                            arrTime[index].setText(timeH3);
                            // arrDesc[index].setText(arry[0+i*4]+"\n@ "+arry[1+i*4]);
                            if(locat.compareTo("null")==0){
                                arrDesc[index].setText("no location");
                            }else {
                                arrDesc[index].setText(split2 + "\n@ " + locat);
                            }
                            // String sdesc = split2 + "@ " + locat;
                            //arrDesc[index].setText(split2);


                            index++;
                            if (ret == day_interval) {
                                share_plan += timeH3 + " " + split2 + "\n";
                                share.setSharedPlan("");
                                share.setSharedPlanIdx(0);
                                share.setSharedPlan(share_plan);
                                share.setSharedPlanIdx(index);
                            }
                   /* if(idx ==0) {
                        arrTime[time_H4].getLocationInWindow(loc);
                        //scroll_cal.smoothScrollTo(loc[0], loc[1]);
                        scroll_cal.smoothScrollTo(loc[0], 224*time_H4);
                    }
                    idx++;*/


                        }
                    }

                    if (index > 10)
                        break;

                    if (ret + 1 > 7) {

                        break;
                    }
                }

                //     datet1 = (Date)formatter.parse(arry[2]);
                //     datet2 = (Date)formatter.parse(arry[3]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            String ret_str;
            ret_str = share.getTodayPlan();
            if(ret_str.length() == 0) {
           //     display_event(day_interval);//0);
                //arrDesc[day_interval].setText("no plan");
                arrDesc[day_interval].setText("calendar error");
                t1[day_interval].setBackgroundColor(0xFF707070);
                t1_[day_interval].setBackgroundColor(0xFF707070);
            }else{
                ret_str = "";
                arrDesc[day_interval].setText(ret_str);
                t1[day_interval].setBackgroundColor(0xFF707070);
                t1_[day_interval].setBackgroundColor(0xFF707070);

            }
            Log.e("calendar error"," no event");
        }
        return str;

    }
}
