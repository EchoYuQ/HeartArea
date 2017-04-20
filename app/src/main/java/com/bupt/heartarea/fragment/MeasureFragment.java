package com.bupt.heartarea.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bupt.heartarea.R;
import com.bupt.heartarea.activity.MeasureActivity;
import com.bupt.heartarea.ui.RippleImageView;

/**
 * Created by yuqing on 2017/3/5.
 */
public class MeasureFragment extends Fragment {
    private View view;
//    private RoundImageView imageView;
//    private ImageView hintImageView;
//    private AnimationDrawable animationDrawable;
    private RippleImageView rippleImageView;
    private ImageView startImageView;
    private Context mContext;

    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        mContext=getActivity();
        view=inflater.inflate(R.layout.fragment_two,container,false);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        rippleImageView.startWaveAnimation();
        super.onResume();
    }

    @Override
    public void onPause() {
        rippleImageView.stopWaveAnimation();
        super.onPause();
    }

    void initView()
    {
//        hintImageView= (ImageView) view.findViewById(R.id.iv_hintanimation);

//        hintImageView.setImageResource(R.drawable.animation1);
//        animationDrawable = (AnimationDrawable) hintImageView.getDrawable();
//        animationDrawable.start();

        rippleImageView= (RippleImageView) view.findViewById(R.id.rippleImageView);

        startImageView=rippleImageView.getImageView();
        startImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MeasureActivity.class);
                startActivity(intent);
            }
        });
    }


}
