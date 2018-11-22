package com.i4vine.ryufragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.i4vine.ryufragment.FragmentCamera.loadScaledBitmap;

public class FragmentFashionTab1 extends Fragment{
    ImageView imgFashion1;
    ImageView imgFashion2;
    ImageView imgFashion3;
    ImageView imgFashion4;
    ImageView imgFashion5;
    ImageView imgFashion6;
    List<ImageView> image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_fashiontab1, container, false);
        imgFashion1 = (ImageView) rootView.findViewById(R.id.img_fashion1);
        imgFashion2 = (ImageView) rootView.findViewById(R.id.img_fashion2);
        imgFashion3 = (ImageView) rootView.findViewById(R.id.img_fashion3);
        imgFashion4 = (ImageView) rootView.findViewById(R.id.img_fashion4);
        imgFashion5 = (ImageView) rootView.findViewById(R.id.img_fashion5);
        imgFashion6 = (ImageView) rootView.findViewById(R.id.img_fashion6);


    /*    String folder = "Capture";
        String path = Environment.getExternalStorageDirectory().getPath();
        String subpath = path + "/" + folder + "/";
        String text = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Capture";

        File directory = new File(text);
        File dirs = new File(Environment.getExternalStorageDirectory(), folder);
        if (!dirs.exists()) {
            dirs.mkdir();
            Log.d("Camera test", "Directory created");
        }*/

        image = new ArrayList<>();
        image.add(0, imgFashion1);
        image.add(1, imgFashion2);
        image.add(2, imgFashion3);
        image.add(3, imgFashion4);
        image.add(4, imgFashion5);
        image.add(5, imgFashion6);

        reload_image();
  /*      File[] files = directory.listFiles();

        if (files == null) {//files.length == 0){
            imgFashion1.setImageResource(R.drawable.img_recommend_6);
            imgFashion2.setImageResource(R.drawable.img_recommend_7);
            imgFashion3.setImageResource(R.drawable.img_recommend_8);
            imgFashion4.setImageResource(R.drawable.img_recommend_9);
            imgFashion5.setImageResource(R.drawable.img_recommend_10);
            imgFashion6.setImageResource(R.drawable.img_recommend_11);

        }*/

        final ImageView.OnTouchListener onTouchListener = new ImageView.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //return false;
                if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {

                    switch (view.getId()) {

                        case R.id.img_fashion1:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview1", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), PopupPhoto.class);
                            i.putExtra("data", 0);
                            startActivity(i);
                            break;

                        case R.id.img_fashion2:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview2", Toast.LENGTH_SHORT).show();
                            Intent i2 = new Intent(getActivity(), PopupPhoto.class);
                            i2.putExtra("data", 1);
                            startActivity(i2);
                            break;

                        case R.id.img_fashion3:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview3", Toast.LENGTH_SHORT).show();
                            Intent i3 = new Intent(getActivity(), PopupPhoto.class);
                            i3.putExtra("data", 2);
                            startActivity(i3);
                            break;

                        case R.id.img_fashion4:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview4", Toast.LENGTH_SHORT).show();
                            Intent i4 = new Intent(getActivity(), PopupPhoto.class);
                            i4.putExtra("data", 4);
                            startActivity(i4);
                            break;

                        case R.id.img_fashion5:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview5", Toast.LENGTH_SHORT).show();
                            Intent i5 = new Intent(getActivity(), PopupPhoto.class);
                            i5.putExtra("data", 4);
                            startActivity(i5);
                            break;

                        case R.id.img_fashion6:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview6", Toast.LENGTH_SHORT).show();
                            Intent i6 = new Intent(getActivity(), PopupPhoto.class);
                            i6.putExtra("data", 5);
                            startActivity(i6);
                            break;
                    }
                }
                return true;
            }
        };

        for(int i=0; i<6; i++){
            image.get(i).setOnTouchListener(onTouchListener);
        }
    /*    imgFashion1.setOnTouchListener(onTouchListener);
        imgFashion2.setOnTouchListener(onTouchListener);
        imgFashion3.setOnTouchListener(onTouchListener);
        imgFashion4.setOnTouchListener(onTouchListener);
        imgFashion5.setOnTouchListener(onTouchListener);
        imgFashion6.setOnTouchListener(onTouchListener);*/

        return rootView;
    }
    public void reload_image() {
        String folder = "Capture";//saveImg";
        String path = Environment.getExternalStorageDirectory().getPath();
        String subpath = path + "/" + folder + "/";
        String text = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder;

        File directory = new File(text);
        File dirs = new File(Environment.getExternalStorageDirectory(), folder);
        if (!dirs.exists()) {
            dirs.mkdir();
            Log.d("Camera test", "Directory created");
        }

        File[] files = directory.listFiles();

        if (files == null) {
            saveImage(6);

        } else {
            int count = files.length;
            if (count < 6) {
                saveImage(6 - count);
            }

        }
        files = directory.listFiles();
        List<String> filesNameList = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            filesNameList.add(files[i].getName());
        }
        String[] array = filesNameList.toArray(new String[0]);
        int idx = 0;
        String paths;

        for (int i = 0; i < 6; i++) {
            int len = files.length;
            ImageView iv;
            paths = subpath + array[len - 1 - i];
            iv = image.get(i);
            int ww = 400; //iv.getWidth();
            int hh = 300; //iv.getHeight();
            try {
                if (iv.getDrawable() != null)
                    ((BitmapDrawable)iv.getDrawable()).getBitmap().recycle();
                iv.setImageBitmap(loadScaledBitmap(ww,hh, paths));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //imageVw.get(i).setImageURI(Uri.parse((paths)));
        }

    }


    public void saveImage(int cnt) {
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
        /*    for (int i = start+count; i < 12; i++) {
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
