package com.i4vine.ryufragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.graphics.Bitmap.Config.RGB_565;
import static android.os.SystemClock.sleep;

public class FragmentCamera extends Fragment {
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    List<ImageView> image;

    int thumbSize = 500;
    private final int GALLERY_CODE=1112;
    private int PICK_IMAGE_REQUEST = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_camera,container,false);
   //     checkDangerousPermissions();
    //    touchListener(rootView);
        imageView1 = (ImageView) rootView.findViewById(R.id.imageView1);
        imageView2 = (ImageView) rootView.findViewById(R.id.imageView2);
        imageView3 = (ImageView) rootView.findViewById(R.id.imageView3);
        imageView4 = (ImageView) rootView.findViewById(R.id.imageView4);
        imageView5 = (ImageView) rootView.findViewById(R.id.imageView5);
        imageView6 = (ImageView) rootView.findViewById(R.id.imageView6);

   /*     String folder = "saveImg";
        String path = Environment.getExternalStorageDirectory().getPath();
        String subpath = path+"/"+folder+"/";
        String text = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder;

        File directory = new File(text);
        File dirs = new File(Environment.getExternalStorageDirectory(), folder);
        if(!dirs.exists()){
            dirs.mkdir();
            Log.d("Camera test", "Directory created");
        }*/

        image = new ArrayList<>();
        image.add(0,imageView1);
        image.add(1,imageView2);
        image.add(2,imageView3);
        image.add(3,imageView4);
        image.add(4,imageView5);
        image.add(5,imageView6);



  /*      File[] files = directory.listFiles();

        if (files == null) {
            saveImage(6);
            List<String> filesNameList = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                filesNameList.add(files[i].getName());
            }

            String[] array = filesNameList.toArray(new String[0]);
            int idx = 0;
            String paths;

            for(int i=0; i<6; i++){
                paths = subpath + array[i];

             //   Bitmap thumbBitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(paths),thumbSize,thumbSize);
             //   image.get(6-files.length+i).setImageBitmap(thumbBitmap);
                image.get(6 - files.length + i).setImageURI(Uri.parse((paths)));
            }

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


            for (int i = 0; i < 6; i++) {
                int len = files.length;

                paths = subpath + array[len - 1 -i];
     //           Bitmap thumbBitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(paths),thumbSize,thumbSize);
     //           image.get(i).setImageBitmap(thumbBitmap);
                image.get(i).setImageURI(Uri.parse((paths)));

            }

        }*/

        checkDangerousPermissions();

        final ImageView.OnTouchListener onTouchListener = new ImageView.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){

                    switch (view.getId()) {

                        case R.id.imageView1:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview1", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), PopupPhoto.class);
                            i.putExtra("data",0);
                            startActivity(i);
                            break;

                        case R.id.imageView2:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview2", Toast.LENGTH_SHORT).show();
                            Intent i2 = new Intent(getActivity(), PopupPhoto.class);
                            i2.putExtra("data",1);
                            startActivity(i2);
                            break;

                        case R.id.imageView3:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview3", Toast.LENGTH_SHORT).show();
                            Intent i3 = new Intent(getActivity(), PopupPhoto.class);
                            i3.putExtra("data",2);
                            startActivity(i3);
                            break;

                        case R.id.imageView4:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview4", Toast.LENGTH_SHORT).show();
                            Intent i4 = new Intent(getActivity(), PopupPhoto.class);
                            i4.putExtra("data",3);
                            startActivity(i4);
                            break;

                        case R.id.imageView5:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview5", Toast.LENGTH_SHORT).show();
                            Intent i5 = new Intent(getActivity(), PopupPhoto.class);
                            i5.putExtra("data",4);
                            startActivity(i5);
                            break;

                        case R.id.imageView6:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview6", Toast.LENGTH_SHORT).show();
                            Intent i6 = new Intent(getActivity(), PopupPhoto.class);
                            i6.putExtra("data",5);
                            startActivity(i6);
                            break;

                    }}
                return true;//false
            }
        };


        for(int i=0; i<6; i++){
            image.get(i).setOnTouchListener(onTouchListener);
        }
      /*  imageView1.setOnTouchListener(onTouchListener);
        imageView2.setOnTouchListener(onTouchListener);
        imageView3.setOnTouchListener(onTouchListener);
        imageView4.setOnTouchListener(onTouchListener);
        imageView5.setOnTouchListener(onTouchListener);
        imageView6.setOnTouchListener(onTouchListener);*/


        reload_image();
        return rootView;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }


    private void checkDangerousPermissions(){
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for(int i=0; i<permissions.length; i++){
            permissionCheck = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED){
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getActivity().getApplicationContext(), "PERMISSION_GRANTED", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getActivity().getApplicationContext(),"!PERMISSION_GRANTED", Toast.LENGTH_LONG).show();
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),permissions[0])){
                Toast.makeText(getActivity().getApplicationContext(),"should show request permission rationale", Toast.LENGTH_LONG).show();
            }else{
                ActivityCompat.requestPermissions(getActivity(),permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            for(int i=0; i<permissions.length; i++){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getActivity().getApplicationContext(),permissions[i]+"Permission_granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), permissions[i]+"!Permission_granted", Toast.LENGTH_LONG).show();
                }
            }
        }else{
            Log.d("====fragment camera","requestCode != 1");
        }
    }


    public static Bitmap loadScaledBitmap(int width, int height, String imgFilePath) throws Exception, OutOfMemoryError {

        //if (!FileUtil.exists(imgFilePath)) {

        //    throw new FileNotFoundException("image file not found : " + imgFilePath);
        //}



        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = RGB_565;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgFilePath, options);

        float widthScale = options.outWidth / width;
        float heightScale = options.outHeight / height;

        float scale = widthScale > heightScale ? widthScale : heightScale;

        if(scale >= 8) {
            options.inSampleSize = 8;
        } else if(scale >= 4) {
            options.inSampleSize = 4;
        } else if(scale >= 2) {
            options.inSampleSize = 2;
        } else {
            options.inSampleSize = 1;
        }

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(imgFilePath, options);
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
      //      files = directory.listFiles();


        } else {
            int count = files.length;

            if (count < 6) {
                saveImage(6 - count);
            }

        }

        files = directory.listFiles();
        int count = files.length;
        List<String> filesNameList = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            filesNameList.add(files[i].getName());
        }
        String[] array = filesNameList.toArray(new String[0]);
        int idx = 0;
        String paths;
        int len = files.length;
        if(len < 6){
            saveImage(6-len);
        }
        for (int i = 0; i < 6; i++) {
            len = files.length;
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
        checkDangerousPermissions();
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

    public Bitmap resizeBitmapImage(Bitmap source, int maxResolution)
    {
        int width = source.getWidth();
        int height = source.getHeight();
        int newWidth = width;
        int newHeight = height;
        float rate = 0.0f;

        if(width > height)
        {
            if(maxResolution < width)
            {
                rate = maxResolution / (float) width;
                newHeight = (int) (height * rate);
                newWidth = maxResolution;
            }
        }
        else
        {
            if(maxResolution < height)
            {
                rate = maxResolution / (float) height;
                newWidth = (int) (width * rate);
                newHeight = maxResolution;
            }
        }

        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true);
    }
}



