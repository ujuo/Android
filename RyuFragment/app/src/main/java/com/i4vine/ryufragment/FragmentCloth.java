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

public class FragmentCloth extends Fragment {
    ImageView cloth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_cloth ,container,false);
    //    cloth = (ImageView)rootView.findViewById(R.id.cloth);
    //    cloth.setImageResource(R.drawable.bg_cloth);

        touchListener(rootView);

        return rootView;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void touchListener(ViewGroup view){
        view.setOnTouchListener(new ViewGroup.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){
                    Toast.makeText(getActivity(), "controller touch event",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), PopupCloth.class);
                    startActivity(i);
                }
                return true;//false;
            }
        });
    }
}
