package com.i4vine.ryufragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.i4vine.serial.SerialTest;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class PopupCloth extends Activity {
    BackgroundThread backgroundThread;
    private final UpdateHandler mHandler = new UpdateHandler(this);
    ImageView popup_cloth;
    ImageView popup_cloth_close;
    ImageView ctrl_img0;
    ImageView ctrl_img1;
    ImageView ctrl_img2;
    ImageView ctrl_img3;
    ImageView ctrl_img4;
    ImageView ctrl_img5;
    ImageView ctrl_img6;
    ImageView ctrl_img7;
    ImageView ctrl_img8;
    ImageView ctrl_img9;
    ImageView ctrl_img10;
    ImageView ctrl_img11;
    ImageView ctrl_img12;
    ImageView ctrl_img13;
    ImageView ctrl_img14;
    ImageView ctrl_img15;
    ImageView ctrl_img16;
    ImageView ctrl_img17;
    ImageView ctrl_img18;
    ImageView ctrl_img19;
    ImageView ctrl_img20;
    ImageView ctrl_img21;
    ImageView ctrl_img22;
    ImageView ctrl_img23;
    ImageView ctrl_img24;
    ImageView ctrl_img25;
    ImageView ctrl_img26;
    ImageView ctrl_img27;
    ImageView ctrl_img28;
    ImageView ctrl_img29;
    ImageView ctrl_img30;
    ImageView ctrl_img31;
    ImageView ctrl_img32;
    ImageView ctrl_img33;
    ImageView ctrl_img34;
    ImageView ctrl_img35;
    ImageView ctrl_img36;
    ImageView ctrl_img37;
    ImageView ctrl_img38;
    ImageView ctrl_img39;
    ImageView ctrl_img40;
    ImageView ctrl_img41;
    ImageView ctrl_img42;
    ImageView ctrl_img43;
    ImageView ctrl_img44;
    ImageView ctrl_img45;
    ImageView ctrl_img46;
    ImageView ctrl_img47;
    ImageView ctrl_img48;
    ImageView ctrl_img49;
    ImageView ctrl_img50;
    ImageView ctrl_img51;
    ImageView ctrl_img52;
    ImageView ctrl_img53;
    ImageView ctrl_img54;
    ImageView ctrl_img55;
    ImageView ctrl_img56;
    ImageView ctrl_img57;
    ImageView ctrl_img58;
    ImageView ctrl_img59;
    ImageView ctrl_img60;
    ImageView ctrl_img61;
    ImageView ctrl_img62;
    ImageView ctrl_img63;
    ImageView ctrl_img64;
    ImageView ctrl_img65;
    ImageView ctrl_img66;
    ImageView ctrl_img67;
    ImageView ctrl_img68;
    ImageView ctrl_img69;

    ImageView ctrl_img70;

    SerialTest serial = new SerialTest();
    TimerTask tt;

    int touch_flag = 0;
    static int touch_sel = 0; // image_view : 1, text_view : 2
    static int[] old_array = new int[80];
    static byte[] result = new byte[30];
    int seg_val = 0;

    int sel_re =0;
    int sel_air = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

     //   layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
     //   layoutParams.dimAmount = 0.7f;

        getWindow().setAttributes(layoutParams);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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


        setContentView(R.layout.popup_cloth);

        popup_cloth = (ImageView) findViewById(R.id.popup_cloth);
        popup_cloth_close = (ImageView)  findViewById(R.id.popup_cloth_close);

        ctrl_img0 = (ImageView) findViewById(R.id.ctrl_img0);
        ctrl_img1 = (ImageView) findViewById(R.id.ctrl_img1);
        ctrl_img2 = (ImageView) findViewById(R.id.ctrl_img2);
        ctrl_img3 = (ImageView) findViewById(R.id.ctrl_img3);
        ctrl_img4 = (ImageView) findViewById(R.id.ctrl_img4);
        ctrl_img5 = (ImageView) findViewById(R.id.ctrl_img5);
        ctrl_img6 = (ImageView) findViewById(R.id.ctrl_img6);
        ctrl_img7 = (ImageView) findViewById(R.id.ctrl_img7);
        ctrl_img8 = (ImageView) findViewById(R.id.ctrl_img8);
        ctrl_img9 = (ImageView) findViewById(R.id.ctrl_img9);
        ctrl_img10 = (ImageView) findViewById(R.id.ctrl_img10);
        ctrl_img11 = (ImageView) findViewById(R.id.ctrl_img11);
        ctrl_img12 = (ImageView) findViewById(R.id.ctrl_img12);
        ctrl_img13 = (ImageView) findViewById(R.id.ctrl_img13);
        ctrl_img14 = (ImageView) findViewById(R.id.ctrl_img14);
        ctrl_img15 = (ImageView) findViewById(R.id.ctrl_img15);
        ctrl_img16 = (ImageView) findViewById(R.id.ctrl_img16);
        ctrl_img17 = (ImageView) findViewById(R.id.ctrl_img17);
        ctrl_img18 = (ImageView) findViewById(R.id.ctrl_img18);
        ctrl_img19 = (ImageView) findViewById(R.id.ctrl_img19);
        ctrl_img20 = (ImageView) findViewById(R.id.ctrl_img20);
        ctrl_img21 = (ImageView) findViewById(R.id.ctrl_img21);
        ctrl_img22 = (ImageView) findViewById(R.id.ctrl_img22);
        ctrl_img23 = (ImageView) findViewById(R.id.ctrl_img23);
        ctrl_img24 = (ImageView) findViewById(R.id.ctrl_img24);
        ctrl_img25 = (ImageView) findViewById(R.id.ctrl_img25);
        ctrl_img26 = (ImageView) findViewById(R.id.ctrl_img26);
        ctrl_img27 = (ImageView) findViewById(R.id.ctrl_img27);
        ctrl_img28 = (ImageView) findViewById(R.id.ctrl_img28);
        ctrl_img29 = (ImageView) findViewById(R.id.ctrl_img29);
        ctrl_img30 = (ImageView) findViewById(R.id.ctrl_img30);
        ctrl_img31 = (ImageView) findViewById(R.id.ctrl_img31);
        ctrl_img32 = (ImageView) findViewById(R.id.ctrl_img32);
        ctrl_img33 = (ImageView) findViewById(R.id.ctrl_img33);
        ctrl_img34 = (ImageView) findViewById(R.id.ctrl_img34);
        ctrl_img35 = (ImageView) findViewById(R.id.ctrl_img35);
        ctrl_img36 = (ImageView) findViewById(R.id.ctrl_img36);
        ctrl_img37 = (ImageView) findViewById(R.id.ctrl_img37);
        ctrl_img38 = (ImageView) findViewById(R.id.ctrl_img38);
        ctrl_img39 = (ImageView) findViewById(R.id.ctrl_img39);
        ctrl_img40 = (ImageView) findViewById(R.id.ctrl_img40);
        ctrl_img41 = (ImageView) findViewById(R.id.ctrl_img41);
        ctrl_img42 = (ImageView) findViewById(R.id.ctrl_img42);
        ctrl_img43 = (ImageView) findViewById(R.id.ctrl_img43);
        ctrl_img44 = (ImageView) findViewById(R.id.ctrl_img44);
        ctrl_img45 = (ImageView) findViewById(R.id.ctrl_img45);
        ctrl_img46 = (ImageView) findViewById(R.id.ctrl_img46);
        ctrl_img47 = (ImageView) findViewById(R.id.ctrl_img47);
        ctrl_img48 = (ImageView) findViewById(R.id.ctrl_img48);
        ctrl_img49 = (ImageView) findViewById(R.id.ctrl_img49);
        ctrl_img50 = (ImageView) findViewById(R.id.ctrl_img50);
        ctrl_img51 = (ImageView) findViewById(R.id.ctrl_img51);
        ctrl_img52 = (ImageView) findViewById(R.id.ctrl_img52);
        ctrl_img53 = (ImageView) findViewById(R.id.ctrl_img53);
        ctrl_img54 = (ImageView) findViewById(R.id.ctrl_img54);
        ctrl_img55 = (ImageView) findViewById(R.id.ctrl_img55);
        ctrl_img56 = (ImageView) findViewById(R.id.ctrl_img56);
        ctrl_img57 = (ImageView) findViewById(R.id.ctrl_img57);
        ctrl_img58 = (ImageView) findViewById(R.id.ctrl_img58);
        ctrl_img59 = (ImageView) findViewById(R.id.ctrl_img59);
        ctrl_img60 = (ImageView) findViewById(R.id.ctrl_img60);
        ctrl_img61 = (ImageView) findViewById(R.id.ctrl_img61);
        ctrl_img62 = (ImageView) findViewById(R.id.ctrl_img62);
        ctrl_img63 = (ImageView) findViewById(R.id.ctrl_img63);
        ctrl_img64 = (ImageView) findViewById(R.id.ctrl_img64);
        ctrl_img65 = (ImageView) findViewById(R.id.ctrl_img65);
        ctrl_img66 = (ImageView) findViewById(R.id.ctrl_img66);
        ctrl_img67 = (ImageView) findViewById(R.id.ctrl_img67);
        ctrl_img68 = (ImageView) findViewById(R.id.ctrl_img68);
        ctrl_img69 = (ImageView) findViewById(R.id.ctrl_img69);
        ctrl_img70 = (ImageView) findViewById(R.id.ctrl_img70);



        popup_cloth_close.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    finish();
                }
                return true;//false;
            }
        });

        Arrays.fill(old_array,0xFFFFFFFF);

        Log.d("old arry",Integer.toHexString(old_array[0]));
        ctrl_img1.setImageResource(R.drawable.power_standby);
        for (int i = 0; i < 16; i++) {
            touchEvent(i);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tt != null) {
            tt.cancel();
            tt = null;
        }
    }


    private void handlerMessage(Message msg){



        if (result.length > 1) {
            Log.d("===========", Integer.toHexString(result[0]) + Integer.toHexString(result[1]));
            update(result);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        backgroundThread = new BackgroundThread();
        backgroundThread.setRunning(true);
        backgroundThread.start();

    }

    @Override
    protected void onStop() {
        super.onStop();

        boolean retry = true;
        backgroundThread.setRunning(false);
        while(retry){
            try{
                backgroundThread.join();
                retry = false;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public class BackgroundThread extends Thread{
        boolean running = false;
        void setRunning(boolean b){
            running = b;
        }

        @Override
        public void run() {
            //super.run();
            while(running){
                try{
                    sleep(20);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                byte[] arg= {0};
                int ret;

                result = serial.recvCallback2(arg);
        //        Log.d("===========len",Integer.toString(result.length));
                if(result.length > 1) {
                    mHandler.sendMessage(mHandler.obtainMessage());
                }

            }
        }
    }

    private static class UpdateHandler extends Handler{
        private final WeakReference<PopupCloth> mActivity;
        public UpdateHandler(PopupCloth activity){
            mActivity = new WeakReference<PopupCloth>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            PopupCloth activity = mActivity.get();
            if(activity != null){
                activity.handlerMessage(msg);
            }
        }
    }


    public void touchEvent(final int idx) {

        ImageView[] image_view = {
                ctrl_img49, ctrl_img44, ctrl_img38, ctrl_img19, ctrl_img16, ctrl_img14, ctrl_img12, ctrl_img10,
                ctrl_img62, ctrl_img53, ctrl_img51, ctrl_img69, ctrl_img3, ctrl_img0, ctrl_img2, ctrl_img1};

        image_view[idx].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int ret;
                int t;
                touch_sel = 1;
                if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    Toast.makeText(getApplicationContext(), "text" + Integer.toString(idx) + "touch", Toast.LENGTH_SHORT).show();
                    t = 1 << idx;
                    touch_flag |= t;
                    Log.d("========DOWN", "idx:" + Integer.toString(idx) + " t:" + Integer.toString(t) + " flag: " + Integer.toString(touch_flag));
                 //   ret = serial.write(Integer.toString(t), Integer.toString(t).length());
                    ret = serial.write(Integer.toString(touch_flag), Integer.toString(touch_flag).length());
                }
                if (motionEvent.getActionMasked() == MotionEvent.ACTION_UP) {
                    int t1;
                    t1 = touch_flag;
                    touch_flag = touch_flag & ~(1 << idx);
                    Log.d("==========UP", "idx:" + Integer.toString(idx) + " t:" + Integer.toString(t1) + " flag: " + Integer.toString(touch_flag));
                    ret = serial.write(Integer.toString(touch_flag), Integer.toString(touch_flag).length());
                }

                return true;//false;
            }
        });
    }


    int update(byte[] img_bytes) {
        ImageView[] img_view = {ctrl_img68, ctrl_img0, ctrl_img2, ctrl_img1,
                ctrl_img6, ctrl_img5, ctrl_img4, ctrl_img3,
                ctrl_img18, ctrl_img9, ctrl_img8, ctrl_img7,
                ctrl_img12, ctrl_img11, ctrl_img10, ctrl_img21,
                ctrl_img16, ctrl_img15, ctrl_img14, ctrl_img13,
                ctrl_img33, ctrl_img20, ctrl_img19, ctrl_img17,
                ctrl_img64, ctrl_img50, ctrl_img45, ctrl_img39,
                ctrl_img36, ctrl_img35, ctrl_img34, ctrl_img38,
                ctrl_img41, ctrl_img40, ctrl_img44, ctrl_img37,
                ctrl_img46, ctrl_img49, ctrl_img43, ctrl_img42,
                ctrl_img26, ctrl_img24, ctrl_img48, ctrl_img47,
                ctrl_img28, ctrl_img29, ctrl_img30, ctrl_img27,
                ctrl_img65, ctrl_img23, ctrl_img32, ctrl_img31,
                ctrl_img70, ctrl_img70, ctrl_img25, ctrl_img25,
                ctrl_img54, ctrl_img53, ctrl_img52, ctrl_img51,
                ctrl_img60, ctrl_img59, ctrl_img62, ctrl_img55,
                ctrl_img56, ctrl_img56, ctrl_img58, ctrl_img61,
                ctrl_img67, ctrl_img66, ctrl_img57, ctrl_img56,
                ctrl_img64, ctrl_img64, ctrl_img64, ctrl_img64,
                ctrl_img64, ctrl_img64, ctrl_img64, ctrl_img64};

        int[] img_id = {R.drawable.empty,R.drawable.empty,
                R.drawable.power_lock,R.drawable.power_lock,
                R.drawable.playpause,R.drawable.playpause,
                R.drawable.power_on, R.drawable.power_on,
                R.drawable.auto_70, R.drawable.auto_100,
                R.drawable.air_purification_70, R.drawable.air_purification_100,
                R.drawable.room_care_bar_50, R.drawable.room_care_bar_100,
                R.drawable.room_care_50, R.drawable.room_care_100,
                R.drawable.hr_2_70, R.drawable.hr_2_100,
                R.drawable.hr_1_70, R.drawable.hr_1_100,
                R.drawable.dehumidification_70, R.drawable.dehumidification_100,
                R.drawable.sleep_70, R.drawable.sleep_100,
                R.drawable.humi_60, R.drawable.humi_60,
                R.drawable.humi_40, R.drawable.humi_40,
                R.drawable.refresh_70, R.drawable.refresh_100,
                R.drawable.hr_4_70, R.drawable.hr_4_100,
                R.drawable.empty, R.drawable.empty,
                R.drawable.empty, R.drawable.empty,
                R.drawable.empty, R.drawable.room_sel3,
                R.drawable.humi_80, R.drawable.humi_80,
                R.drawable.dual_clean1,R.drawable.dual_clean,
                R.drawable.air_shower1,R.drawable.air_shower,
                R.drawable.clothing_care_bar_50,R.drawable.clothing_care_bar_100,
                R.drawable.clothing_care_50, R.drawable.clothing_care_100,
                R.drawable.left_bar, R.drawable.left_bar,
                R.drawable.left_bar, R.drawable.left_bar,
                R.drawable.air_smothing1, R.drawable.air_smoothing,
                R.drawable.air_dry1, R.drawable.air_dry,
                R.drawable.speed_70, R.drawable.speed_100,
                R.drawable.led, R.drawable.led,
                R.drawable.basic_70, R.drawable.basic_100,
                R.drawable.left_bar, R.drawable.left_bar,
                R.drawable.pro_70, R.drawable.pro_100,
                R.drawable.led, R.drawable.led,
                R.drawable.soft_70, R.drawable.soft_100,
                R.drawable.led, R.drawable.led,
                R.drawable.right_bar, R.drawable.right_bar,
                R.drawable.led, R.drawable.led,
                R.drawable.refresh_70, R.drawable.refresh_100,
                R.drawable.led, R.drawable.led,
                R.drawable.empty, R.drawable.empty,
                R.drawable.right_bar, R.drawable.right_bar,
                R.drawable.right_bar, R.drawable.right_bar,
                R.drawable.right_bar, R.drawable.right_bar,
                R.drawable.silk_70, R.drawable.silk_100,
                R.drawable.sweater_70, R.drawable.sweater_100,
                R.drawable.wool_70, R.drawable.wool_100,
                R.drawable.special_70, R.drawable.special_100,
                R.drawable.sterilization_70, R.drawable.sterilization_100,
                R.drawable.deodorization_70, R.drawable.deodorization_100,
                R.drawable.intensive_70, R.drawable.intensive_100,
                R.drawable.outdoor_70, R.drawable.outdoor_100,
                R.drawable.hat_70, R.drawable.hat_100,
                R.drawable.light_70, R.drawable.light_100,
                R.drawable.dry_70, R.drawable.dry_100,
                R.drawable.dust_70, R.drawable.dust_100,
                R.drawable.hr, R.drawable.hr,
                R.drawable.air_shot, R.drawable.air_shot,
                R.drawable.scarf_70, R.drawable.scarf_100,
                R.drawable.denim_70, R.drawable.denim_100,
                R.drawable.drain, R.drawable.drain,
                R.drawable.nano_mist, R.drawable.nano_mist,
                R.drawable.fill, R.drawable.fill,
                R.drawable.min, R.drawable.min,
                R.drawable.empty, R.drawable.empty,
                R.drawable.empty, R.drawable.cloth_sel3,
                R.drawable.service, R.drawable.service,
                R.drawable.waiting, R.drawable.waiting,
                R.drawable.num0, R.drawable.num0,
                R.drawable.num0, R.drawable.num0,
                R.drawable.num0, R.drawable.num0,
                R.drawable.num0, R.drawable.num0,
                R.drawable.empty, R.drawable.empty,
                R.drawable.empty, R.drawable.empty,
                R.drawable.empty, R.drawable.empty,
                R.drawable.empty, R.drawable.empty,
                R.drawable.empty, R.drawable.empty,
                R.drawable.empty, R.drawable.empty,
                R.drawable.empty, R.drawable.empty,
                R.drawable.empty, R.drawable.empty,
        };

        int[] update_bytes = new int[80];

      /*  int[] img_array = new int[20];
        for(int i = 0;  i < 20; i++){
            img_array[i] = img_bytes[i] | (img_bytes[i+1] << 8) | (img_bytes[i+2] << 16) | (img_bytes[i+3] << 24);
        }*/

        for(int i=0; i<20; i++){
            update_bytes[i*4] = (img_bytes[i]&0x03);
            update_bytes[i*4+1] = ((img_bytes[i]) >> 2) & 0x03;
            update_bytes[i*4+2] = ((img_bytes[i]) >> 4) & 0x03;
            update_bytes[i*4+3] = ((img_bytes[i]) >> 6) & 0x03;
        }
        Log.d("update image",update_bytes.toString());



    /*    if((update_bytes[0]%2) == 0) {
            img_view[4].setImageResource(img_id[4 * 2 + 1]);
            ctrl_img8.setImageResource(R.drawable.air_smoothing1);
        }else{
            img_view[4].setImageResource(img_id[4 * 2]);
            ctrl_img8.setImageResource(R.drawable.air_smoothing);
        }*/

        for(int i=0; i<80; i++){
            if((i < 52) || (i > 55)){
                if(update_bytes[i] != old_array[i]) {
                    int idx = update_bytes[i];
                    Log.d("======================", Integer.toString(idx));
                    if (idx == 0) {
                        img_view[i].setVisibility(View.INVISIBLE);
                        if (i == 3) {
                            img_view[i].setImageResource(R.drawable.power_standby);
                        } else if (i == 50) {
                            sel_air = 0;
                        } else if (i == 71) {
                            sel_re = 0;
                        }
                    } else {
                        img_view[i].setVisibility(View.VISIBLE);
                        if (idx == 1) {
                            img_view[i].setImageResource(img_id[i * 2]);
                            if (i == 50) {
                                sel_air = 0;
                            } else if (i == 71) {
                                sel_re = 0;
                            }
                        } else if (idx == 2) {
                            img_view[i].setImageResource(img_id[i * 2 + 1]);
                            if (i == 50) {
                                sel_air = 0;
                            } else if (i == 71) {
                                sel_re = 0;
                            }
                        } else if (idx == 3) {
                            if (i == 50) {
                                sel_air = 1;

                            } else if (i == 71) {
                                sel_re = 1;

                            }
                            //    if(img_view[i].getTag().equals() == img_id[i*2+1])
                        }
                    }

                    old_array[i] = update_bytes[i];

                }
            }else { //i=52-55,  7 segment
                int temp = 52;
                temp = i - temp;

                seg_val = seg_val | ((update_bytes[i] & 0x3) << (temp * 2));
                if (i == 55){
                    if (seg_val < 100) {
                        int unit;
                        int tens;
                        unit = seg_val % 10;
                        tens = (seg_val / 10);
                        switch (unit) {
                            case 0:
                                img_view[54].setImageResource(R.drawable.num0);
                                break;
                            case 1:
                                img_view[54].setImageResource(R.drawable.num1);
                                break;
                            case 2:
                                img_view[54].setImageResource(R.drawable.num2);
                                break;
                            case 3:
                                img_view[54].setImageResource(R.drawable.num3);
                                break;
                            case 4:
                                img_view[54].setImageResource(R.drawable.num4);
                                break;
                            case 5:
                                img_view[54].setImageResource(R.drawable.num5);
                                break;
                            case 6:
                                img_view[54].setImageResource(R.drawable.num6);
                                break;
                            case 7:
                                img_view[54].setImageResource(R.drawable.num7);
                                break;
                            case 8:
                                img_view[54].setImageResource(R.drawable.num8);
                                break;
                            case 9:
                                img_view[54].setImageResource(R.drawable.num9);
                                break;
                        }

                        switch (tens) {
                            case 0:
                                img_view[52].setImageResource(R.drawable.num0);
                                break;
                            case 1:
                                img_view[52].setImageResource(R.drawable.num1);
                                break;
                            case 2:
                                img_view[52].setImageResource(R.drawable.num2);
                                break;
                            case 3:
                                img_view[52].setImageResource(R.drawable.num3);
                                break;
                            case 4:
                                img_view[52].setImageResource(R.drawable.num4);
                                break;
                            case 5:
                                img_view[52].setImageResource(R.drawable.num5);
                                break;
                            case 6:
                                img_view[52].setImageResource(R.drawable.num6);
                                break;
                            case 7:
                                img_view[52].setImageResource(R.drawable.num7);
                                break;
                            case 8:
                                img_view[52].setImageResource(R.drawable.num8);
                                break;
                            case 9:
                                img_view[52].setImageResource(R.drawable.num9);

                                break;
                        }
                        //display_7seg();
                    } else if (seg_val == 100) {
                        img_view[52].setImageResource(R.drawable.d);
                        img_view[54].setImageResource(R.drawable.r);

                    } else if (seg_val == 255) {
                        img_view[52].setImageResource(R.drawable.empty);
                        //    img_view[53].setImageResource(R.drawable.empty);
                        img_view[54].setImageResource(R.drawable.empty);
                        //    img_view[55].setImageResource(R.drawable.empty);

                    }
                    seg_val = 0;
                }

            }
        }

        if(sel_re > 0){
            if(sel_re == 1) {
                img_view[71].setImageResource(R.drawable.room_sel1);
            } else if(sel_re == 2) {
                img_view[71].setImageResource(R.drawable.room_sel2);
            } else if(sel_re == 3) {
                img_view[71].setImageResource(R.drawable.room_sel3);
                sel_re = 0;
            }
            sel_re++;
        }

        if(sel_air > 0){
            if(sel_air == 1) {
                img_view[50].setImageResource(R.drawable.cloth_sel1);
            } else if (sel_air == 2) {
                img_view[50].setImageResource(R.drawable.cloth_sel2);
            } else if (sel_air == 3) {
                img_view[50].setImageResource(R.drawable.cloth_sel3);
                sel_re = 0;
            }
            sel_air++;

        }

/*      ctrl_img0.setVisibility(View.INVISIBLE);
        ctrl_img1.setVisibility(View.INVISIBLE);
        ctrl_img2.setVisibility(View.INVISIBLE);*/


        //      ctrl_img62.setImageResource(R.drawable.dehumidification_70);
        //      ctrl_img1.setImageResource(R.drawable.air_shot);


        return 0;
    }


    int disp(String data) {
        String[] str;
        str = data.split(",");

        if (touch_sel == 1) {

            ctrl_img62.setImageResource(R.drawable.dehumidification_70);
            ctrl_img1.setImageResource(R.drawable.air_shot);
            //     ctrl_img0.setVisibility(View.INVISIBLE);


        }
        return 0;
    }

    static int callback ( int[] length, byte[] data){
        //     byte[] recvData = data;
        Log.d("CallgackTest", "callback1 enter =======");
        Log.d("data ", Arrays.toString(data));
        Log.d("CallgackTest", "callback1 exit =======");
        // disp(Arrays.toString(data));
        return 0;
    }



}
