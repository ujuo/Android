package com.i4vine.ryufragment;

import android.app.Fragment;
import android.content.Intent;
import android.media.Image;
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
import java.util.ArrayList;
import java.util.List;

public class FragmentFashionTab2 extends Fragment {
    ImageView imgFashion7;
    ImageView imgFashion8;
    ImageView imgFashion9;
    ImageView imgFashion10;
    ImageView imgFashion11;
    ImageView imgFashion12;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_fashiontab2, container,false);

        imgFashion7 = (ImageView) rootView.findViewById(R.id.img_fashion7);
        imgFashion8 = (ImageView) rootView.findViewById(R.id.img_fashion8);
        imgFashion9 = (ImageView) rootView.findViewById(R.id.img_fashion9);
        imgFashion10 = (ImageView) rootView.findViewById(R.id.img_fashion10);
        imgFashion11 = (ImageView) rootView.findViewById(R.id.img_fashion11);
        imgFashion12 = (ImageView) rootView.findViewById(R.id.img_fashion12);

        String folder = "Capture";
        String path = Environment.getExternalStorageDirectory().getPath();
        String subpath = path + "/" + folder + "/";
        String text = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Capture";

        File directory = new File(text);
        File dirs = new File(Environment.getExternalStorageDirectory(), folder);
        if (!dirs.exists()) {
            dirs.mkdir();
            Log.d("Camera test", "Directory created");
        }

        List<ImageView> image = new ArrayList<>();
        image.add(0, imgFashion7);
        image.add(1, imgFashion8);
        image.add(2, imgFashion9);
        image.add(3, imgFashion10);
        image.add(4, imgFashion11);
        image.add(5, imgFashion12);

        File[] files = directory.listFiles();

        if (files == null) {//files.length == 0){
            imgFashion7.setImageResource(R.drawable.img_recommend_6);
            imgFashion8.setImageResource(R.drawable.img_recommend_7);
            imgFashion9.setImageResource(R.drawable.img_recommend_8);
            imgFashion10.setImageResource(R.drawable.img_recommend_9);
            imgFashion11.setImageResource(R.drawable.img_recommend_10);
            imgFashion12.setImageResource(R.drawable.img_recommend_11);
         /*   for(int i=0; i<6; i++){
                image.get(i).setImageResource(R.drawable.red);
            }*/
        }

        final ImageView.OnTouchListener onTouchListener = new ImageView.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){

                    switch (view.getId()) {

                        case R.id.img_fashion7:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview1", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), PopupPhoto.class);
                            i.putExtra("data",0);
                            startActivity(i);
                            break;

                        case R.id.img_fashion8:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview2", Toast.LENGTH_SHORT).show();
                            Intent i2 = new Intent(getActivity(), PopupPhoto.class);
                            i2.putExtra("data",1);
                            startActivity(i2);
                            break;

                        case R.id.img_fashion9:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview3", Toast.LENGTH_SHORT).show();
                            Intent i3 = new Intent(getActivity(), PopupPhoto.class);
                            i3.putExtra("data",2);
                            startActivity(i3);
                            break;

                        case R.id.img_fashion10:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview4", Toast.LENGTH_SHORT).show();
                            Intent i4 = new Intent(getActivity(), PopupPhoto.class);
                            i4.putExtra("data",4);
                            startActivity(i4);
                            break;

                        case R.id.img_fashion11:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview5", Toast.LENGTH_SHORT).show();
                            Intent i5 = new Intent(getActivity(), PopupPhoto.class);
                            i5.putExtra("data",4);
                            startActivity(i5);
                            break;

                        case R.id.img_fashion12:
                            Toast.makeText(getActivity().getApplicationContext(), "Touch imageview6", Toast.LENGTH_SHORT).show();
                            Intent i6 = new Intent(getActivity(), PopupPhoto.class);
                            i6.putExtra("data",5);
                            startActivity(i6);
                            break;

                    }
                }
                return true;
            }
        };

        imgFashion7.setOnTouchListener(onTouchListener);
        imgFashion8.setOnTouchListener(onTouchListener);
        imgFashion9.setOnTouchListener(onTouchListener);
        imgFashion10.setOnTouchListener(onTouchListener);
        imgFashion11.setOnTouchListener(onTouchListener);
        imgFashion12.setOnTouchListener(onTouchListener);
        return rootView;
    }
}
