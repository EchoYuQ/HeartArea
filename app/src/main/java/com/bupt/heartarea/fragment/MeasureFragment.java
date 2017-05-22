package com.bupt.heartarea.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bupt.heartarea.R;
import com.bupt.heartarea.activity.MainActivity;
import com.bupt.heartarea.activity.MeasureActivity;
import com.bupt.heartarea.ui.RippleImageView;
import com.bupt.heartarea.utils.GlobalData;

import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        view = inflater.inflate(R.layout.fragment_two, container, false);
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

    void initView() {

        rippleImageView = (RippleImageView) view.findViewById(R.id.rippleImageView);

        startImageView = rippleImageView.getImageView();
        startImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LemonHello.getInformationHello("选择当前状态", "请选择您当前的状态")
                        .addAction(new LemonHelloAction("静息状态", Color.parseColor("#a269af73"), new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                GlobalData.measure_state = 0;
                                helloView.hide();
                                Intent intent = new Intent(getContext(), MeasureActivity.class);
                                startActivity(intent);
                            }
                        }))
                        .addAction(new LemonHelloAction("运动过后", Color.parseColor("#FFF96650"), new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                GlobalData.measure_state = 1;
                                helloView.hide();
                                Intent intent = new Intent(getContext(), MeasureActivity.class);
                                startActivity(intent);
                            }
                        }))
                        .addAction(new LemonHelloAction("取消测量", Color.RED, new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                helloView.hide();
                            }
                        }))
                        .show(getActivity());
            }
        });
    }


}
