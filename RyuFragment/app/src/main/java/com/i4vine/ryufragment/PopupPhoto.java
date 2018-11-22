package com.i4vine.ryufragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PopupPhoto extends Activity {
    ImageView popup_photo;
    ImageView popup_photo_close;
    ImageView dust_btn;
    ImageView right_btn;
    ImageView left_btn;
    int imgIdx = 0;
    float x1;
    float x2;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

    //    layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
    //    layoutParams.dimAmount = 0.7f;

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


        setContentView(R.layout.popup_photo);

        popup_photo = (ImageView) findViewById(R.id.popup_photo);
        popup_photo_close = (ImageView) findViewById(R.id.popup_photo_close);
        dust_btn = (ImageView)findViewById(R.id.dust_btn);
        right_btn = (ImageView)findViewById(R.id.right_btn);
        left_btn = (ImageView)findViewById(R.id.left_btn);
        int getInt =  getIntent().getIntExtra("data",1);
        imgIdx = getInt;
        setImageView(getInt);


        dust_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){

                    deleteImage(imgIdx);

                }
                return true;//false;
            }
        });
        popup_photo_close.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    finish();
                }
                return true;//false;
            }
        });


        popup_photo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int ret =0;
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1 = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = motionEvent.getX();
                        float deltaX = x2 - x1;
                        if(deltaX > 0){
                            imgIdx = imgIdx+1;
                            setImageView(imgIdx);
                        }else{
                            int tempIdx = imgIdx -1;
                            if(tempIdx < 0)
                                tempIdx = 0;

                            imgIdx = tempIdx;
                            setImageView(imgIdx);
                        }
                        break;
                }
                return true;//false;
            }
        });

        right_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){
                    int tempIdx = imgIdx - 1;
                    if(tempIdx < 0){
                        tempIdx = 0;
                    }
                    imgIdx = tempIdx;
                    setImageView(imgIdx);


                }
                return true;//false;
            }
        });

        left_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){
                    imgIdx = imgIdx+1;
                    setImageView(imgIdx);
                }
                return true;//false;
            }
        });
    }



    public void deleteImage(int index){
        String folder = "Capture";
        String path = Environment.getExternalStorageDirectory().getPath();
        String subpath = path+"/"+folder+"/";
        String text = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Capture";

        File directory = new File(text);

        File[] files = directory.listFiles();
        if(files == null){

        }else{

        }

        List<String> filesNameList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            filesNameList.add(files[i].getName());
        }

        String[] array = filesNameList.toArray(new String[0]);
        int idx = 0;
        String paths;
        int len = files.length;
        int len1 = len - 1 - index;

        if(len1 < 0){
            len1 = 0;
            Toast.makeText(getApplicationContext(), "Remove all images", Toast.LENGTH_SHORT).show();
            Log.e("==========delete image","invalid index");
        }else {
            paths = subpath + array[len - 1 - index];
            File f = new File(paths);
            f.delete();
            getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)));
            int temp_len = len - 1 - index;
            temp_len = len -1 -1 -index;
            if(temp_len < 0)
                temp_len = 0;
            paths = subpath + array[temp_len];
            popup_photo.setImageURI(Uri.parse(paths));
        }


    }

    public void setImageView(int index){
        String folder = "Capture";
        String path = Environment.getExternalStorageDirectory().getPath();
        String subpath = path+"/"+folder+"/";
        String text = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Capture";

        File directory = new File(text);
        File dirs = new File(Environment.getExternalStorageDirectory(), folder);
        if(!dirs.exists()){
            dirs.mkdir();
            Log.d("Popup Photo","Photo directory created");
        }

 /*
        File[] files = directory.listFiles();
        if(files == null){

        }else{

        }

        List<String> filesNameList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            filesNameList.add(files[i].getName());
        }

        String[] array = filesNameList.toArray(new String[0]);
        int idx = 0;
        String paths;

        idx = files.length - 6;
        if(idx < 0){
            if(idx < 6-files.length){
                int idx1 = files.length;
                //popup_photo.setImageResource(R.drawable.red);
                if(idx1 == 0)
                    popup_photo.setImageResource(R.drawable.img_recommend_6);
                else if(idx1 == 1)
                    popup_photo.setImageResource(R.drawable.img_recommend_7);
                else if(idx1 == 2)
                    popup_photo.setImageResource(R.drawable.img_recommend_8);
                else if(idx1 == 3)
                    popup_photo.setImageResource(R.drawable.img_recommend_9);
                else if(idx1 == 4)
                    popup_photo.setImageResource(R.drawable.img_recommend_10);
                else if(idx1 == 5)
                    popup_photo.setImageResource(R.drawable.img_recommend_11);
            }
        }else {
            int len = files.length;
            if(index < 0) {
                index = 0;
                imgIdx = index;
            }

            if(index > files.length -1){
                index = files.length-1;
                imgIdx = index;
            }

            paths = subpath + array[len -1 -index];
            popup_photo.setImageURI(Uri.parse(paths));
        }*/


        File[] files = directory.listFiles();


        if (files == null) {
            saveImage(6);
            List<String> filesNameList = new ArrayList<>();
            //for (int i = 0; i < files.length; i++) {
            for(int i=0; i<6; i++){
                filesNameList.add(files[i].getName());
            }

            String[] array = filesNameList.toArray(new String[0]);
            int idx = 0;
            String paths;
            int len = files.length;

            paths = subpath + array[len -1 -index];
            popup_photo.setImageURI(Uri.parse(paths));


          //  for(int i=0; i<6; i++){
           //     paths = subpath + array[i];
           //     image.get(6 - files.length + i).setImageURI(Uri.parse((paths)));
           // }
           // for (int i = 0; i < 6; i++) {
             //   image.get(i).setImageResource(R.drawable.red);
           // }
        } else {
            int idx = 0;
            idx = files.length - 6;
            if(idx < 0) {
                int cnt = 0;
                cnt = 6 - files.length;
                saveImage(cnt);
            }
            files = directory.listFiles();
            List<String> filesNameList = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                filesNameList.add(files[i].getName());
            }

            String[] array = filesNameList.toArray(new String[0]);

            String paths;

            int len = files.length;
            if(index < 0) {
                index = 0;
                imgIdx = index;
            }

            if(index > files.length -1){
                index = files.length-1;
                imgIdx = index;
            }


            paths = subpath + array[len -1 -index];
            popup_photo.setImageURI(Uri.parse(paths));

        //    if (idx < 0) {
         //       for (int i = 0; i < files.length; i++) {
          //          paths = subpath + array[i];
           //         image.get(6 - files.length + i).setImageURI(Uri.parse((paths)));
            //    }
           // } else{
             //  for (int i = 0; i < 6; i++) {
              //      int len = files.length;
               //     paths = subpath + array[len - 1 -i];
                //    image.get(i).setImageURI(Uri.parse((paths)));

                //}
           // }
        }




        //popup_photo.setImageResource(R.drawable.ryu_control);
    }

    private void saveImage(int cnt) {

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.img_recommend_6);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.img_recommend_7);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.img_recommend_8);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.img_recommend_9);
        Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(), R.drawable.img_recommend_10);
        Bitmap bitmap6 = BitmapFactory.decodeResource(getResources(), R.drawable.img_recommend_11);

        FileOutputStream out = null;
        String fname = null;

        int start = 6;
        int count = 6-cnt;
        try {
            out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Capture/" + "20170000000001.png");
            bitmap1.compress(Bitmap.CompressFormat.PNG, 100, out);
            out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Capture/" + "20170000000002.png");
            bitmap2.compress(Bitmap.CompressFormat.PNG, 100, out);
            out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Capture/" + "20170000000003.png");
            bitmap3.compress(Bitmap.CompressFormat.PNG, 100, out);
            out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Capture/" + "20170000000004.png");
            bitmap4.compress(Bitmap.CompressFormat.PNG, 100, out);
            out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Capture/" + "20170000000005.png");
            bitmap5.compress(Bitmap.CompressFormat.PNG, 100, out);
            out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Capture/" + "20170000000006.png");
            bitmap6.compress(Bitmap.CompressFormat.PNG, 100, out);
         /*   for (int i = start+count; i < 12; i++) {
                if (i == 6) {
                    out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Capture/" + "20170000000001.png");
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 100, out);
                } else if (i == 7) {
                    out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Capture/" + "20170000000002.png");
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 100, out);
                } else if (i == 8) {
                    out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Capture/" + "20170000000003.png");
                    bitmap3.compress(Bitmap.CompressFormat.PNG, 100, out);
                } else if (i == 9) {
                    out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Capture/" + "20170000000004.png");
                    bitmap4.compress(Bitmap.CompressFormat.PNG, 100, out);
                } else if (i == 10) {
                    out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Capture/" + "20170000000005.png");
                    bitmap5.compress(Bitmap.CompressFormat.PNG, 100, out);
                } else if (i == 11) {
                    out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Capture/" + "20170000000006.png");
                    bitmap6.compress(Bitmap.CompressFormat.PNG, 100, out);
                }
            }*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
