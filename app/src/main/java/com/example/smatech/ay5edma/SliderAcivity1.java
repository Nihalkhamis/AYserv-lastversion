package com.example.smatech.ay5edma;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.offerModel.example.Offer;
import com.example.smatech.ay5edma.Utils.Constants;
import com.example.smatech.ay5edma.Utils.CustomSliderView;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SliderAcivity1 extends AppCompatActivity {
    HashMap<String, String> url_maps;
    PagerIndicator pagerIndicator;
    List<String> x;
    ImageView Remove;
    private SliderLayout mDemoSlider;
    String T;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_acivity);
        x = new ArrayList<>();
        x = Hawk.get(Constants.images);
        Log.d("TTTTT", "onCreate: "+x.size());
        Set<String> set = new HashSet<>(x);
        x.clear();
        x.addAll(set);
        Log.d("TTTTT", "onCreate: "+x.size());

        Remove = findViewById(R.id.Remove);
        if (Hawk.get("y333").equals("1")) {

        } else {
            //Remove.setVisibility(View.GONE);
        }
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        mDemoSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        pagerIndicator = findViewById(R.id.custom_indicator);
        url_maps = new HashMap<String, String>();
        Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x.size() > 0) {
                    Log.d("TTT", "onClick:Size "+x.size());
//                    Log.d("TTTTT", "onClick: " + mDemoSlider.getCurrentPosition());
                    x.remove(mDemoSlider.getCurrentPosition());
                    Hawk.put(Constants.images, x);
                    mDemoSlider.removeSliderAt(mDemoSlider.getCurrentPosition());
                    Hawk.put(Constants.images, x);
                    if (x.size() == 0) {
                        finish();
                    }
                }
            }
        });

        get_home_sliders();
    }

    private void get_home_sliders() {
        if (x.size() > 0) {
            ArrayList<String> Title = new ArrayList<>();
            for (int i = 0; i < x.size(); i++) {
                if(x.get(i).equals("")){

                }else {
                    T = i + "";
                    url_maps.put("http://www.anyservice-ksa.com/mobile/prod_img/" + x.get(i), T);
                }
            }
            for (String name : url_maps.keySet()) {
                CustomSliderView textSliderView = new CustomSliderView(SliderAcivity1.this);
                // initialize a SliderLayout
                Log.d("TTTT", "get_home_sliders: " + name);
                textSliderView
                        .description(url_maps.get(name))
                        .image(name)
                        .setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);

                mDemoSlider.addSlider(textSliderView);
            }

            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(3000);
            mDemoSlider.setCustomIndicator(pagerIndicator);


            //setSliderViews(IMGs, Descs);

        } else {

        }
    }

}
