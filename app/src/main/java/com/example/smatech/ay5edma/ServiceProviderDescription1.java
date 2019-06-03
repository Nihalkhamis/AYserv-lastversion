
package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.smatech.ay5edma.Dialoge.ContactOptionDialoge;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.edituser.Example;
import com.example.smatech.ay5edma.Models.offerModel.example.Offer;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.example.smatech.ay5edma.Utils.CustomSliderView;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceProviderDescription1 extends AppCompatActivity {

    //Details of service provider

    Button Accept;
    LinearLayout Reveiws, Location_layout;
    com.example.smatech.ay5edma.Models.Modelss.RequestModel reqModel;
    TextView name, gender, Occupaion, Birthday, Location, About;
    me.zhanghai.android.materialratingbar.MaterialRatingBar Stars;
    View parentLayout;
    ProgressDialog progressDialog;
    String T;
    PagerIndicator pagerIndicator;
    UserModel userModel;
    HashMap<String, String> url_maps;
    private SliderLayout mDemoSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_description);
        parentLayout = findViewById(android.R.id.content);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        pagerIndicator = findViewById(R.id.custom_indicator);
        url_maps = new HashMap<String, String>();
        ImageView back;
        userModel = Hawk.get(Constants.userData);
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(this.getString(R.string.Service_Provider_Details) + "");
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reqModel = Hawk.get("Req");
        getImages(reqModel.getShopId());


        ///
        // bottom nav
        LinearLayout homeLayout, requestLayout, notificationLayout, settingLayout;
        homeLayout = findViewById(R.id.homeLayout);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        requestLayout = findViewById(R.id.requestLayout);
        requestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ServiceProviderDescription1.this, RequestsActivity.class));
            }
        });
        notificationLayout = findViewById(R.id.notificationLayout);
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ServiceProviderDescription1.this, NotificationsActivity.class));
            }
        });
        settingLayout = findViewById(R.id.settingLayout);
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ServiceProviderDescription1.this, SettingActiviy.class));

            }
        });
        TextView notification_counter = findViewById(R.id.notification_counter);
        if (Hawk.contains(Constants.notification_count)) {
            if (Hawk.get(Constants.notification_count).equals("0")) {
                notification_counter.setVisibility(View.GONE);
            } else {
                notification_counter.setVisibility(View.VISIBLE);
                notification_counter.setText(Hawk.get(Constants.notification_count));
            }
        }
        //
        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        Occupaion = findViewById(R.id.Occupaion);
        Birthday = findViewById(R.id.Birthday);
        Location = findViewById(R.id.Location);
        About = findViewById(R.id.About);
        Stars = findViewById(R.id.Stars);
        //
        name.setText(" " + reqModel.getShop().getName());
        if (reqModel.getShop().getGender().equals("1")) {
            gender.setText(" " + getString(R.string.male));
        } else {
            gender.setText(" " + getString(R.string.female));

        }
        if (Hawk.get(Constants.Language).equals("ar")) {
            Occupaion.setText(" " + reqModel.getSubNameAr());

        } else {
            Occupaion.setText(" " + reqModel.getSubName());

        }
        Birthday.setText(" " + reqModel.getShop().getBirthday() + "");
        //Location.setText(" " + reqModel.getFrom().getAddress() + "  ");
        //
        LatLng latLngA = new LatLng(Double.parseDouble(reqModel.getLatitude()),Double.parseDouble(reqModel.getLongitude()));
        LatLng latLngB = new LatLng(Double.parseDouble(reqModel.getShop().getLatitude()),Double.parseDouble(reqModel.getShop().getLongitude()));

        android.location.Location locationA = new Location("point A");
        locationA.setLatitude(latLngA.latitude);
        locationA.setLongitude(latLngA.longitude);
        Location locationB = new Location("point B");
        locationB.setLatitude(latLngB.latitude);
        locationB.setLongitude(latLngB.longitude);

        Log.d("TTT", "A: "+latLngA.latitude+"--"+latLngA.longitude);
        Log.d("TTT", "B: "+latLngB.latitude+"--"+latLngB.longitude);
        double distance = locationA.distanceTo(locationB);
        Location.setText(getString(R.string.away_from_you)+Math.floor(distance/1000)+"km");
        //
        //Location.setText(" " + getString(R.string.Show_Location_on_Map));
        Stars.setRating(Float.parseFloat(reqModel.getShop().getRate()));
        Location_layout = findViewById(R.id.Location_layout);
        Location_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(reqModel.getFrom().getLatitude())
                        , Double.parseDouble(reqModel.getFrom().getLongitude()));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
*/

                Log.d("TTTT", "onClick: "+ reqModel.getShop().getLatitude()+"-----"+reqModel.getShop().getLongitude());


                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Double.parseDouble(reqModel.getShop().getLatitude()) +
                        "," + Double.parseDouble(reqModel.getShop().getLongitude())+getString(R.string.Location));

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

               /* startActivity(new Intent(ServiceProviderDescription.this, MapAcvity.class)
                        .putExtra("address", reqModel.getFrom().getAddress())
                        .putExtra("lat", reqModel.getFrom().getLatitude())
                        .putExtra("long", reqModel.getFrom().getLongitude()));
                Log.d("TTTT", "onMapReady:lat--> " + reqModel.getFrom().getLatitude());
                Log.d("TTTT", "onMapReady:long--> " + reqModel.getFrom().getLongitude());
           */
            }
        });
        /*  */
        About.setText(" " + reqModel.getShop().getAbout());
        ///
        Accept = findViewById(R.id.Accept);
        Accept.setVisibility(View.INVISIBLE);
        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressDialog.show();
            }
        });

        //
        Reveiws = findViewById(R.id.Reveiws);
        Reveiws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceProviderDescription1.this, ClientReviewsActivity.class)
                        .putExtra("userID", reqModel.getShopId());
                startActivity(intent);
            }
        });

    }


    private void get_home_sliders(ArrayList<String> x) {
        Log.d("TTT", "get_home_sliders: " + reqModel.getShop().getName());
        if (x != null && x.size() > 0) {
            ArrayList<String> Title = new ArrayList<>();
            for (int i = 0; i < x.size(); i++) {
                if(x.get(i).equals("")){

                }else {
                    T = i + "";
                    url_maps.put("http://www.anyservice-ksa.com/mobile/prod_img/" + x.get(i), T);
                }
            }
            Log.d("TTT", "get_home_sliders: " + x.size());
            for (String name : url_maps.keySet()) {
                CustomSliderView textSliderView = new CustomSliderView(ServiceProviderDescription1.this);
                // initialize a SliderLayout
                textSliderView
                        .description(url_maps.get(name))
                        .image(name)
                        .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView slider) {
                                startActivity(new Intent(ServiceProviderDescription1.this, SliderAcivity.class));

                            }
                        })
                        .setScaleType(BaseSliderView.ScaleType.Fit);
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
private void getImages(String id) {
    ArrayList<String> x = new ArrayList<>();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Connectors.connectionServices.BaseURL)
            .addConverterFactory(GsonConverterFactory
                    .create(new Gson())).build();
    Connectors.connectionServices connectionService =
            retrofit.create(Connectors.connectionServices.class);

    connectionService.get_user1(id).enqueue(new Callback<Example>() {
        @Override
        public void onResponse(Call<Example> call, Response<Example> response) {
            Example example = response.body();
            if (example.getStatus()) {

                String y = example.getUser().getImages();
                String[] xx = y.split("___");
                for (int i = 0; i < xx.length; i++) {
                    x.add(xx[i]);
                }
                get_home_sliders(x);

            }
        }

        @Override
        public void onFailure(Call<Example> call, Throwable t) {

        }
    });

}
    @Override
    protected void onResume() {
        super.onResume();
        TextView notification_counter = findViewById(R.id.notification_counter);
        if (Hawk.contains(Constants.notification_count)) {
            if (Hawk.get(Constants.notification_count).equals("0")) {
                notification_counter.setVisibility(View.GONE);
            } else {
                notification_counter.setVisibility(View.VISIBLE);
                notification_counter.setText(Hawk.get(Constants.notification_count));
            }
        }
    }


}


