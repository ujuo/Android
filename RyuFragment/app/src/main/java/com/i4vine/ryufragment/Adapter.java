package com.i4vine.ryufragment;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends PagerAdapter{
    private LayoutInflater inflater;
    private Context context;

    public Adapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        String folder = "Capture";
        String text = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder;//"/Capture";

        File directory = new File(text);
        File[] files = directory.listFiles();

        return files.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout)object);
        //return false;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //return super.instantiateItem(container, position);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slider, container, false);
        TextView sliderText = (TextView)v.findViewById(R.id.sliderText);
        ImageView sliderImgView = (ImageView)v.findViewById(R.id.sliderImgView);

        String folder = "Capture";
        String text = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder;//"/Capture";
        File directory = new File(text);
        File[] files = directory.listFiles();
        int len = files.length;
        List<String> filesNameList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            filesNameList.add(files[i].getName());
        }
        String[] array = filesNameList.toArray(new String[0]);

        String paths;
        String path = Environment.getExternalStorageDirectory().getPath();
        String subpath = path+"/"+folder+"/";
        paths = subpath + array[len - 1 - position];
        sliderImgView.setImageURI(Uri.parse(paths));

        String txt = (position+1)+"번째 이미지";
        sliderText.setText(txt);
        container.addView(v);

        return v;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        ViewGroup view = (ViewGroup) object;
        container.removeView(view);
        view = null;
        container.invalidate();
    }
}
