package com.i4vine.ryufragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentAir extends Fragment {
    ImageView air;
    TextView pm10_1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_air,container,false);

     //   air = (ImageView)rootView.findViewById(R.id.air);
     //   air.setImageResource(R.drawable.bg_air);
        SharePreferenceUtil share;

        share = new SharePreferenceUtil(getContext());
        String str_dust = share.getSharedDust();
        if(str_dust != null) {
            pm10_1 = (TextView) rootView.findViewById(R.id.pm10_1);
            pm10_1.setText(str_dust);
        }else{
            pm10_1.setText("154");
        }
        touchListener(rootView);




        return rootView;


        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void touchListener(ViewGroup view){
        view.setOnTouchListener(new ViewGroup.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){
                    Toast.makeText(getActivity(), "PopupFashionTab",Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent(getActivity(), PopupAir.class);
                    Intent i = new Intent(getActivity(), PopupFashionTab.class);
                    startActivity(i);
                }
                return true;//false;
            }
        });
    }
}
