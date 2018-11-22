package com.i4vine.ryufragment;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class FragmentCalendar extends Fragment implements EasyPermissions.PermissionCallbacks{
  //  TextView status;
    TextView result_cal;
  //  TextView location;
  //  TextView date;
    Button get_event;
    private static final String TAG = "FragmentCalendar";
    private com.google.api.services.calendar.Calendar mService = null;
    GoogleAccountCredential mCredential;
    ProgressDialog mProgress;
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    SharePreferenceUtil share;
    int choose_cnt = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_calendar,container,false);

        //status = (TextView) rootView.findViewById(R.id.status);
        result_cal = (TextView) rootView.findViewById(R.id.result_cal);
    //    location = (TextView) rootView.findViewById(R.id.location);
    //    date = (TextView) rootView.findViewById(R.id.date);
        get_event = (Button) rootView.findViewById(R.id.get_event);



        result_cal.setVerticalScrollBarEnabled(true);
        result_cal.setMovementMethod(new ScrollingMovementMethod());

  /*      status.setVerticalScrollBarEnabled(true);
        status.setMovementMethod(new ScrollingMovementMethod());
        status.setText("Press Button to Start");
*/
        mProgress = new ProgressDialog(getContext());  //the ProgressDialog display on main activity. if i don't want it  remove it.
        mProgress.setMessage("Call Google Calendar Api"); //the message display on main activity. if i don't want it  remove it.

        mCredential = GoogleAccountCredential.usingOAuth2(getActivity().getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());

        share = new SharePreferenceUtil(getContext());
       // touchListener(rootView);
        get_event.setOnClickListener(new ViewGroup.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResultsFromApi();

            }
        });


        setClick4();
        result_cal.setOnTouchListener(new ViewGroup.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // screen board don't understand work, so i need it.
            /*    if(choose_cnt > 5){
                    choose_cnt = 0;
                    getResultsFromApi();

                }*/
                ///
                if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){
                    Toast.makeText(getActivity(), "Popup Calendar",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), PopupCalendar.class);
                    startActivity(i);
                }
                return true;//false;
            }
        });
        return rootView;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setClick4(){
        get_event.performClick();
    }

 /*   private void touchListener(ViewGroup view){
        view.setOnTouchListener(new ViewGroup.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){
                    Toast.makeText(getActivity(), "Popup Calendar",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), PopupCalendar.class);
                    startActivity(i);
                }
                return true;//false;
            }
        });
    }*/

    public void getResultsFromApi(){
        if(!isGooglePlayServicesAvailable()){
            acquireGooglePlayServices();
        }else if(mCredential.getSelectedAccountName() == null){

            chooseAccount();

        }else if(!isDeviceOnline()){
            result_cal.setText("no network connection available");

            Log.e("Fragment Calendar", "no network connection available");
        }else{
            new MakeRequestTask(mCredential).execute();
        }
    }

    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount(){
        if(EasyPermissions.hasPermissions(getActivity().getApplicationContext(), Manifest.permission.GET_ACCOUNTS)){
            String accountName = getActivity().getPreferences(Context.MODE_PRIVATE).getString(PREF_ACCOUNT_NAME, null);
            if(accountName != null){
                mCredential.setSelectedAccountName(accountName);
               // getResultsFromApi();
                choose_cnt++;

                if(choose_cnt > 5){ //for screen board
                    Log.d("============choose_cnt", "> 5 ================");
                    if(!isDeviceOnline()){
                        result_cal.setText("no network connection available");
                        Log.e("Fragment Calendar", "no network connection available");
                    }else{
                        new MakeRequestTask(mCredential).execute();
                        choose_cnt = 0;
                    }
                }else{
                    getResultsFromApi(); //original code
                }

       /*         if(!isDeviceOnline()){
                    result_cal.setText("no network connection available");
                    Log.e("Fragment Calendar", "no network connection available");
                }else{
                    new MakeRequestTask(mCredential).execute();
                }
*/
            }else {
                startActivityForResult(mCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
            }
        }else{
            EasyPermissions.requestPermissions(this,"This app needs to access your Google account",
                    REQUEST_PERMISSION_GET_ACCOUNTS, Manifest.permission.GET_ACCOUNTS);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if(resultCode != RESULT_OK){
                    result_cal.setText("This app requires Google Play Services. Please install " +
                            "Google Play Services on your device and relaunch this app.");
                } else {
                    getResultsFromApi();
                }
                break;

            case REQUEST_ACCOUNT_PICKER:
                if(resultCode == RESULT_OK && data != null && data.getExtras() != null){
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if(accountName != null){
                        SharedPreferences settings = getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;

            case REQUEST_AUTHORIZATION:
                if(resultCode == RESULT_OK){
                    getResultsFromApi();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    private boolean isDeviceOnline(){
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private boolean isGooglePlayServicesAvailable(){
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(getActivity().getApplicationContext());
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    private void acquireGooglePlayServices(){
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(getActivity().getApplicationContext());
        if(apiAvailability.isUserResolvableError(connectionStatusCode)){
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode){
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                getActivity(),
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
            // return null;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            result_cal.setText("");
     //       mProgress.show();
        }


        @Override
        protected void onPostExecute(List<String> strings) {
            //super.onPostExecute(strings);
            //String[] str = strings.toArray(new String[strings.size()]);

            String str;
            str = TextUtils.join("\n", strings);
            long cal_ret = -1;
            share.setSharedTest(str);

     //       mProgress.hide();
            if (strings == null || strings.size() == 0) {
                result_cal.setText("No Plan Week");//No results returned");
                share.setTodayPlan("No Plan Week");

            } else {

                String[] arry = strings.toArray(new String[strings.size()]);
                String[] sp = arry[0].split("\\\\");
                String res = "";
                if(sp[0].compareTo("null")==0){
                    //     strings.add(0, "Data retrieved using the Google Calendar API:");

               // if(TextUtils.join("\n", strings).compareTo("null") == 0){
                    result_cal.setText("No Plan");//No results returned");
                    share.setTodayPlan("No Plan");
                    res = "No Plan";
                }else {
                    //result.setText(TextUtils.join("\n", strings));
                  //  String[] arry = strings.toArray(new String[strings.size()]);

                    //    for(int i=0; i< strings.size()/4; i++){
                    for (int i = 0; i < 1; i++) {
                        String[] split1 = arry[i * 4].split("\\\\");
                        int slen = split1.length;

                        for (int j = 0; j < slen; ) {
                            res += split1[j];
                            j = j + 2;
                            //  if(j < slen)
                            res += "\n";
                        }

                    }


                    //result.setText(arry[0].toString());
                    result_cal.setText(res);
                    //  location.setText(arry[1].toString());
                    //  date.setText(arry[2].toString());


                    DateFormat formatter;
                    formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ");
                    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                    Date datet2 = null;
                    Date datet1 = null;

                    SimpleDateFormat timeh1 = new SimpleDateFormat("hh:mm a", Locale.US);
                    SimpleDateFormat timeh2 = new SimpleDateFormat("hh:mm a", Locale.US);
                    SimpleDateFormat timee = new SimpleDateFormat("E", Locale.US);
                    SimpleDateFormat timed = new SimpleDateFormat("d", Locale.US);

                    String timeH1 = "";
                    String timeH2 = "";
                    String timeE = "";
                    String timeD = "";
                    int noday = 0;

                    try {
                        if ((arry[2].compareTo("null") != 0) && (datet1 == null) && arry[3].compareTo("null") == 0) {
                            datet1 = (Date) fmt.parse(arry[2]);
                            datet2 = datet1;
                            noday = 1;

                        } else if ((arry[2].compareTo("null") == 0) && (arry[3].compareTo("null") != 0)) {
                            datet2 = (Date) fmt.parse(arry[3]);
                            datet1 = datet2;
                            noday = 1;
                            //today ~ arry[3]
                        } else if((arry[2].compareTo("null") == 0) && (arry[3].compareTo("null") == 0)){
                            noday = 2;


                        } else {
                            Log.e("FragmentCalendar", "=========get event day ");

                            datet1 = (Date) formatter.parse(arry[2]);
                            datet2 = (Date) formatter.parse(arry[3]);
                        }


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (noday == 1) {
                        //timeH1 = timeh1.format(datet1);
                        timeH1 = "00:00 AM";
                        //timeH2 = timeh2.format(datet2);
                        timeH2 = "11:59 PM";

                    } else if(noday == 2){
                        timeH1 = "";
                        timeH2 = "";
                    } else {
                        timeH1 = timeh1.format(datet1);
                        timeH1 = timeH1 + " - ";
                        timeH2 = timeh2.format(datet2);

                    }


                    if(datet1 != null) {
                        cal_ret = calendar_interval_day(datet1);
                        if (cal_ret == 0) {
                            //String plan = arry[0] + "\n" + timeH1 +" - " + timeH2 + "\n" + arry[1];
                            String plan = res + "\n" + timeH1 + timeH2 + "\n" + "@ " + arry[1];
                            share.setTodayPlan(plan);
                            result_cal.setText(plan);
                        } else {
                            result_cal.setText("no plan");
                            share.setTodayPlan("no plan");

                        }
                    }else if(res.length() > 0){
                        String sPlan="";
                        if(res.compareTo("No Plan")==0) {
                            sPlan = res;
                        } else if(arry[1].compareTo("null")==0){
                            sPlan = res;
                        }else{
                            sPlan = res + "\n" + "@ " + arry[1];
                        }

                        share.setTodayPlan(sPlan);
                        result_cal.setText(sPlan);
                    }
                }
            }
        }

        @Override
        protected void onCancelled() {
            // super.onCancelled();
     //       mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            FragmentCalendar.REQUEST_AUTHORIZATION);
                } else {
                    result_cal.setText("The following error occured:\n" + mLastError.getMessage());
                    startActivityForResult(mCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER); //screen보드에서는 이상하게 안되서 추가한 코드
                }
            } else {
                result_cal.setText("Request cancelled");
            }
        }

        private List<String> getDataFromApi() throws IOException {
            DateTime now = new DateTime(System.currentTimeMillis());
            List<String> eventStrings = new ArrayList<String>();
            Events events = mService.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();

            for (Event event : items) {

                String s = event.getDescription();
                String s2 = "";
                if(s == null){
                    s2 = "null\\\\";
                    eventStrings.add(s2);
                }else {
                    String[] s1 = s.split("\n");
                    int s1len = s1.length;
                    s2 = "";
                    for (int i = 0; i < s1.length; i++) {
                        s2 += s1[i];
                        s2 += "\\\\";
                    }
                }
                //eventStrings.add(String.format("%s ", event.getDescription()));
                if(s != null)
                    eventStrings.add(String.format("%s", s2));

                String sLoc = event.getLocation();
                if(sLoc == null){
                    sLoc = "null";
                }else {
                   // eventStrings.add(String.format("%s ", event.getLocation()));
                }
                eventStrings.add(String.format("%s ", sLoc));
           /*     if(end == null){
                    eventStrings.add(String.format("%s",start));
                }else {
                    eventStrings.add(String.format("%s - %s", start, end));
                }*/
                DateTime start = event.getStart().getDateTime();
                DateTime end = event.getEnd().getDateTime();
                if (start != null) {
                    //   start = event.getStart().getDate();
                    //   end = event.getEnd().getDateTime();
                    eventStrings.add(String.format("%s", start));
                }else{
                    start = event.getStart().getDate();
                    if (start != null) {
                        eventStrings.add(String.format("%s", start));
                    }else {
                        eventStrings.add("null");
                    }
                }

                if (end != null){
                    eventStrings.add(String.format("%s", end));
                    //    end = event.getEnd().getDate();
                }else{
                    /*end = event.getEnd().getDate();
                    if(end != null){
                        eventStrings.add(String.format("%s", end));
                    }else */{
                        eventStrings.add("null");
                    }
                }
                //   eventStrings.add(String.format("%s (%s:", event.getSummary(), start));


            }
            //    String[] arry = eventStrings.toArray(new String[eventStrings.size()]);
            return eventStrings;
        }

        long calendar_interval_day(Date d) {
            Calendar now = Calendar.getInstance();
            Date getToday = now.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String monthTest = format.format(getToday);
            String monthTest2 = format.format(d);
            long cal_days = 0;
            try {
                Date firstDate = format.parse(monthTest);
                Date secondDate = format.parse(monthTest2);
                long cal_interval = secondDate.getTime() - firstDate.getTime();
                cal_days = cal_interval / (24 * 60 * 60 * 1000);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            cal_days = Math.abs(cal_days);

            return cal_days;
        }
    }

    int interval_d() {
        Calendar now = Calendar.getInstance();
        Date getToday = now.getTime();

        SimpleDateFormat monthtest = new SimpleDateFormat("MMMM", Locale.US);
        String monthTest = monthtest.format(getToday);

        int day_start = 0;

        SimpleDateFormat timetest1 = new SimpleDateFormat("E d", Locale.US);
        String dayTest1 = timetest1.format(getToday);
        String[] strlist1 = dayTest1.split(" ");
        if (strlist1[0].compareTo("Mon") == 0) {
            day_start = -1;
        } else if (strlist1[0].compareTo("Tue") == 0) {
            day_start = -2;
        } else if (strlist1[0].compareTo("Wed") == 0) {
            day_start = -3;
        } else if (strlist1[0].compareTo("Thu") == 0) {
            day_start = -4;
        } else if (strlist1[0].compareTo("Fri") == 0) {
            day_start = -5;
        } else if (strlist1[0].compareTo("Sat") == 0) {
            day_start = -6;
        } else if (strlist1[0].compareTo("Sun") == 0) {
            day_start = 0;
        }
        day_start =  day_start*(24 * 60 * 60 * 1000);
        return day_start;
    }
}
