package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.smatech.ay5edma.Adapters.CatgryAdapter;
import com.example.smatech.ay5edma.Adapters.RequestAdapter;
import com.example.smatech.ay5edma.Dialoge.LanguageDialoge;
import com.example.smatech.ay5edma.Models.DummyModel;
import com.example.smatech.ay5edma.Models.Modelss.CategoryModel;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModelSatus;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.example.smatech.ay5edma.Utils.CustomSliderView;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //Navigation Views' Views
    LinearLayout ProfileLayout, RequestsLayout, Go, nav_home, nav_Notifications, nav_ContactUs, nav_Language,
            nav_Fav, nav_BE_Serv_provider, nav_Setting, aboutUs, nav_BE_Serv_Client, nav_Buy_Poins, changePassword, logOut;
    //
    LinearLayout reviewLayout, info_linear;
    String T;
    PagerIndicator pagerIndicator, pagerIndicator1;
    HashMap<String, String> url_maps;
    private com.daimajia.slider.library.SliderLayout mDemoSlider, mDemoSlider1;
    DrawerLayout drawer;
    LinearLayout myPoints, myReje, myAcce;
    ImageView menu;
    ScrollView client_view;
    NestedScrollView Server_view;
    RecyclerView RV, RV2;
    ArrayList<CategoryModel> DM;
    CatgryAdapter catgryAdapter;
    View parentLayout;
    EditText Search;
    int temp = 0;
    RequestAdapter requestAdapter;
    ArrayList<com.example.smatech.ay5edma.Models.Modelss.RequestModel> DM1;
    UserModel userModel;
    ProgressDialog progressDialog;
    TextView name, rate, points, requestesApproved, requestRej, favTo, occupation, notification_counter;
    me.zhanghai.android.materialratingbar.MaterialRatingBar Stars;
    CategoryModel categoryModel, categoryModel2;
    CircleImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);
        profile_image = findViewById(R.id.profile_image);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));
        name = findViewById(R.id.name);
        Stars = findViewById(R.id.Stars);
        rate = findViewById(R.id.rate);
        points = findViewById(R.id.points);
        requestesApproved = findViewById(R.id.requestesApproved);
        requestRej = findViewById(R.id.requestRej);
        favTo = findViewById(R.id.favTo);
        occupation = findViewById(R.id.occupation);
        notification_counter = findViewById(R.id.notification_counter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        parentLayout = findViewById(android.R.id.content);
        if (Hawk.contains(Constants.userData)) {
            Log.d("TTTT", "onCreate:ffsafsfsafasf ");
            login(Hawk.get(Constants.username) + "", Hawk.get(Constants.password) + "", Hawk.get(Constants.TOKEN) + "");
            userModel = Hawk.get(Constants.userData);
            if (userModel.getRole().equals("1")) {
                Hawk.put(Constants.UserType, "0");
            } else {
                Hawk.put(Constants.UserType, "1");
            }
        }
        categoryModel2=new CategoryModel();
        categoryModel = Hawk.get(Constants.extraauserData2);
        if (Hawk.contains(Constants.extraauserData3)) {
            categoryModel2 = Hawk.get(Constants.extraauserData3);
        }
        // bottom nav
        LinearLayout homeLayout, requestLayout, notificationLayout, settingLayout;
        homeLayout = findViewById(R.id.homeLayout);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ClientHomeActivity.this, ClientHomeActivity.class));
            }
        });
        requestLayout = findViewById(R.id.requestLayout);
        requestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .setAction(getString(R.string.login), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();

                                }
                            })
                            .show();
                } else {
                    startActivity(new Intent(ClientHomeActivity.this, RequestsActivity.class));

                }
            }
        });
        notificationLayout = findViewById(R.id.notificationLayout);
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .setAction(getString(R.string.login), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();

                                }
                            })
                            .show();
                } else {
                    startActivity(new Intent(ClientHomeActivity.this, NotificationsActivity.class));

                }

            }
        });
        settingLayout = findViewById(R.id.settingLayout);
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .setAction(getString(R.string.login), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();

                                }
                            })
                            .show();
                } else {
                    startActivity(new Intent(ClientHomeActivity.this, SettingActiviy.class));

                }

            }
        });
        //
        menu = findViewById(R.id.menu);
        client_view = findViewById(R.id.client_view);
        Server_view = findViewById(R.id.Server_view);
        Search = findViewById(R.id.Search);
        Search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (!Search.getText().toString().equals("")) {
                        //DM.clear();
                        //temp = 1;
                        Hawk.put(Constants.mCatgrID, "");
                        startActivity(new Intent(ClientHomeActivity.this, SpecificCatgryActivity.class)
                                .putExtra("search", Search.getText().toString()));
                        Search.setText("");
                        //catgryAdapter.notifyDataSetChanged();
                        //getCategries(Search.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            private Timer timer = new Timer();
            private final long DELAY = 1000; // milliseconds

            @Override
            public void afterTextChanged(Editable s) {
                timer.cancel();
                timer = new Timer();
                if (s.toString().equals("") && temp == 1) {
                    timer.schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    // TODO: do what you need here (refresh list)
                                    ClientHomeActivity.this.runOnUiThread(new Runnable() {

                                        public void run() {
                                            DM.clear();
                                            catgryAdapter.notifyDataSetChanged();
                                            getCategries("");
                                            temp = 0;
                                        }
                                    });
                                }


                            },
                            DELAY
                    );
                } else {

                }

            }
        });
        Go = findViewById(R.id.Go);
        if (!Hawk.contains(Constants.Language)) {
            Hawk.put(Constants.Language, "en");
        }
        if (Hawk.get(Constants.UserType).equals("0")) {

            client_view.setVisibility(View.VISIBLE);
            Server_view.setVisibility(View.GONE);
            DM = new ArrayList<>();
            RV = findViewById(R.id.Rv);
            catgryAdapter = new CatgryAdapter(DM, this, new CatgryAdapter.OnItemClick() {
                @Override
                public void setOnItemClick(int position) {
                    Intent intent = new Intent(ClientHomeActivity.this, SpecificCatgryActivity.class);
                    startActivity(intent);
                    if (Hawk.get(Constants.Language).equals("en")) {
                        Hawk.put(Constants.SubCat1, DM.get(position).getName());

                    } else {
                        Hawk.put(Constants.SubCat1, DM.get(position).getNameAr());

                    }
                    Hawk.put(Constants.mCatgrID, DM.get(position).getId());
                    Log.d("TTTT", "setOnItemClick: " + Hawk.get(Constants.mCatgrID));

                }
            });
            RV.setAdapter(catgryAdapter);
            RV.setLayoutManager(new GridLayoutManager(this, 3));
            catgryAdapter.notifyDataSetChanged();
            getCategries("");
            Go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!Search.getText().toString().equals("")) {
                        //DM.clear();
                        //temp = 1;
                        Hawk.put(Constants.mCatgrID, "");
                        startActivity(new Intent(ClientHomeActivity.this, SpecificCatgryActivity.class).putExtra("search", Search.getText().toString()));
                        Search.setText("");

                        //catgryAdapter.notifyDataSetChanged();
                        //getCategries(Search.getText().toString());
                    }
                }
            });
        } else {
            /* Drawable drawable = ratingBar.getProgressDrawable();
    drawable.setColorFilter(Color.parseColor("#FFFDEC00"), PorterDuff.Mode.SRC_ATOP);*/
            reviewLayout = findViewById(R.id.reviewLayout);
            reviewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ClientHomeActivity.this, ClientReviewsActivity.class)
                            .putExtra("userID", userModel.getId()));
                }
            });
            info_linear = findViewById(R.id.info_linear);
            info_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ClientHomeActivity.this, SettingActiviy.class));
                }
            });
            profile_image = findViewById(R.id.profile_image);
            if (!userModel.getImage().equals("")) {
                Log.d("yyyy", "onCreate: " + userModel.getImage());
                Picasso.with(this).load("http://www.anyservice-ksa.com/mobile/prod_img/" + userModel.getImage()).into(profile_image);
            }
            profile_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ClientHomeActivity.this, SettingActiviy.class));

                }
            });
            client_view.setVisibility(View.GONE);
            Server_view.setVisibility(View.VISIBLE);
            myPoints = findViewById(R.id.myPoints);
            myPoints.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ClientHomeActivity.this, PackageActivity.class));

                }
            });
            myReje = findViewById(R.id.myReje);
            myReje.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ClientHomeActivity.this, RequestsActivity.class)
                            .putExtra("stat", "0"));


                }
            });
            myAcce = findViewById(R.id.myAcce);
            myAcce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ClientHomeActivity.this, RequestsActivity.class)
                            .putExtra("stat", "1"));

                }
            });
            RV2 = findViewById(R.id.RV2);
            DM1 = new ArrayList<>();
            requestAdapter = new RequestAdapter(DM1, "3", this, ClientHomeActivity.this, new RequestAdapter.OnItemClick() {
                @Override
                public void setOnItemClick(int position) {
                    Intent intent = new Intent(ClientHomeActivity.this, ClientRequestDetailsActivity.class);
                    Hawk.put(Constants.mRequestID, DM1.get(position).getId());
                    Hawk.put(Constants.mRequestFrom, DM1.get(position).getUser());
                    Hawk.put(Constants.mRequestDesc, DM1.get(position).getBody());
                    Hawk.put(Constants.mRequest, DM1.get(position));
                    startActivity(intent);

                }
            });
            RV2.setAdapter(requestAdapter);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            RV2.setLayoutManager(mLayoutManager);
            requestAdapter.notifyDataSetChanged();
//            getRequestes("", "0", "", "" + userModel.getSubcategoryId()
//                    , "" + userModel.getCategoryId(), "" + userModel.getId());


            name.setText(userModel.getName());
            Stars.setRating(Float.parseFloat(userModel.getRate()));
            rate.setText(userModel.getRate());
            points.setText(userModel.getPoints());
            requestesApproved.setText(userModel.getAccepted());
            requestRej.setText(userModel.getRejected());
            favTo.setText(userModel.getPeople());
//            if (Hawk.get(Constants.Language).equals("ar")) {
//                if (categoryModel2.getNameAr() != null) {
//                    occupation.setText(categoryModel.getNameAr() + " - " + categoryModel2.getNameAr());
//                }else {
//                    occupation.setText(categoryModel.getNameAr() );
//
//                }
//            } else {
//                if(categoryModel2.getName()!=null){
//                    occupation.setText(categoryModel.getName()+" - "+categoryModel2.getName());
//
//                }else{
//                    occupation.setText(categoryModel.getName());
//
//                }
//
//            }


        }


        mDemoSlider1 = (SliderLayout) findViewById(R.id.slider1);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        pagerIndicator = findViewById(R.id.custom_indicator);
        pagerIndicator1 = findViewById(R.id.custom_indicator1);
        url_maps = new HashMap<String, String>();
        get_home_sliders();

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(null);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }


            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ProfileLayout = navigationView.findViewById(R.id.nav_Profile);
        ProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .setAction(getString(R.string.login), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();

                                }
                            })
                            .show();
                } else {
                    Intent intent = new Intent(ClientHomeActivity.this, EditProfileActivity.class);
                    startActivity(intent);
                }
            }
        });
        RequestsLayout = navigationView.findViewById(R.id.nav_Requests);
        RequestsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .setAction(getString(R.string.login), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();

                                }
                            })
                            .show();
                } else {
                    Intent intent = new Intent(ClientHomeActivity.this, RequestsActivity.class);
                    startActivity(intent);

                }
            }
        });
        nav_Notifications = navigationView.findViewById(R.id.nav_Notifications);
        nav_Notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .setAction(getString(R.string.login), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();

                                }
                            })
                            .show();
                } else {
                    Intent intent = new Intent(ClientHomeActivity.this, NotificationsActivity.class);
                    startActivity(intent);
                }
            }
        });
        nav_home = navigationView.findViewById(R.id.nav_home);
        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });
        nav_ContactUs = navigationView.findViewById(R.id.nav_ContactUs);
        nav_ContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .setAction(getString(R.string.login), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();

                                }
                            })
                            .show();
                } else {
                    Intent intent = new Intent(ClientHomeActivity.this, ContactUsActivity.class);
                    startActivity(intent);
                }
            }
        });
        nav_Language = navigationView.findViewById(R.id.nav_Language);
        nav_Language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                LanguageDialoge cdd = new LanguageDialoge(ClientHomeActivity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });
        nav_Fav = navigationView.findViewById(R.id.nav_Fav);
        nav_Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .setAction(getString(R.string.login), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();

                                }
                            })
                            .show();
                } else {
                    Intent intent = new Intent(ClientHomeActivity.this, FavouritsActivity.class);
                    startActivity(intent);
                }
            }
        });
        nav_BE_Serv_provider = navigationView.findViewById(R.id.nav_BE_Serv_provider);
        nav_BE_Serv_provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .setAction(getString(R.string.login), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();

                                }
                            })
                            .show();
                } else {
                    if (userModel.getCategoryId() == null || userModel.getCategoryId().equals("")) {
                        // finish();
                        //ClientHomeActivity.this.finishActivity(1);
                        Intent intent = new Intent(ClientHomeActivity.this, BeServiceProvier.class);
                        startActivity(intent);

                    } else {
                        edit("" + userModel.getMobile()
                                , "" + userModel.getImage()
                                , "" + userModel.getId()
                                , "" + userModel.getName()
                                , "" + userModel.getGender()
                                , ""
                                , ""
                                , "2");
                    }
                }
            }
        });
        nav_Setting = navigationView.findViewById(R.id.nav_Setting);
        nav_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .setAction(getString(R.string.login), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();

                                }
                            })
                            .show();
                } else {
                    Intent intent = new Intent(ClientHomeActivity.this, SettingActiviy.class);
                    startActivity(intent);
                }
            }
        });
        aboutUs = navigationView.findViewById(R.id.aboutUs);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .setAction(getString(R.string.login), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();

                                }
                            })
                            .show();
                } else {

                    if (Hawk.contains(Constants.Language)) {
                        if (Hawk.get(Constants.Language).equals("en")) {
                            startActivity(new Intent(ClientHomeActivity.this, WebViewActivity.class)
                                    .putExtra("url", "http://www.anyservice-ksa.com/mobile/api/webview?type=about")
                                    .putExtra("text", getString(R.string.Aboutus)));
                        } else {
                            startActivity(new Intent(ClientHomeActivity.this, WebViewActivity.class)
                                    .putExtra("url", "http://www.anyservice-ksa.com/mobile/api/webview?type=about_ar")
                                    .putExtra("text", getString(R.string.Aboutus)));
                        }
                    } else {
                        startActivity(new Intent(ClientHomeActivity.this, WebViewActivity.class)
                                .putExtra("url", "http://www.anyservice-ksa.com/mobile/api/webview?type=about_ar")
                                .putExtra("text", getString(R.string.Aboutus)));
                    }
                }
            }
        });
        nav_Buy_Poins = navigationView.findViewById(R.id.nav_Buy_Poins);
        nav_Buy_Poins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .setAction(getString(R.string.login), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();

                                }
                            })
                            .show();
                } else {
                    Intent intent = new Intent(ClientHomeActivity.this, PackageActivity.class);
                    startActivity(intent);
                }
            }
        });
        logOut = navigationView.findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(userModel.getId());

            }
        });

        changePassword = navigationView.findViewById(R.id.changePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .setAction(getString(R.string.login), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();

                                }
                            })
                            .show();
                } else {
                    startActivity(new Intent(ClientHomeActivity.this, ChangePasswordActivity.class));
                }
            }
        });
        nav_BE_Serv_Client = navigationView.findViewById(R.id.nav_BE_Serv_Client);
        nav_BE_Serv_Client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.login), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    edit("" + userModel.getMobile()
                            , "" + userModel.getImage()
                            , "" + userModel.getId()
                            , "" + userModel.getName()
                            , "" + userModel.getGender()
                            , ""
                            , "", "1");

                }
            }
        });
        if (Hawk.get(Constants.UserType).equals("0")) {
            nav_BE_Serv_Client.setVisibility(View.GONE);
            nav_Buy_Poins.setVisibility(View.GONE);
            nav_BE_Serv_provider.setVisibility(View.VISIBLE);
        } else {
            nav_BE_Serv_Client.setVisibility(View.VISIBLE);
            nav_BE_Serv_provider.setVisibility(View.GONE);
            nav_Buy_Poins.setVisibility(View.VISIBLE);
            nav_Fav.setVisibility(View.GONE);
        }
        /* */
        ////////
        navigationView.setNavigationItemSelectedListener(this);
        if (Hawk.contains(Constants.userData)) {
            login(Hawk.get(Constants.username) + "", Hawk.get(Constants.password) + "", Hawk.get(Constants.TOKEN) + "");
        }
        if (Hawk.contains(Constants.skipe)) {
            if (Hawk.get(Constants.skipe).equals(true)) {
                notification_counter.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getCategries(String search) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_Category(search,"True").enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                Log.d("TTTT", "onResponse: "+call.request().url());
                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                if (statusModel.getStatus() == true) {
                    ArrayList<CategoryModel> categoryModels = statusModel.getCategories();
                    DM.addAll(categoryModels);
                    catgryAdapter.notifyDataSetChanged();
                } else {
                    Snackbar.make(parentLayout, "" + getString(R.string.Couldnt_find) + " " + search + " " + getString(R.string.try_another_word), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                    getCategries("");
                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();

            }
        });
    }

    {


    }


    private void getRequestes(String user_id, final String status, String shop_id, String subcatgry, String Catgry, String filter_id, String sub2) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_Requests(user_id, status, shop_id, "" + subcatgry, "" + Catgry, filter_id, sub2 + "")
                .enqueue(new Callback<StatusModel>() {
                    @Override
                    public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                        Log.d("TTT", "Call: " + response.toString());
                        progressDialog.dismiss();
                        StatusModel statusModel = response.body();
                        if (statusModel.getStatus()) {
                            DM1.clear();
                            DM1.addAll(statusModel.getRequests());
                            requestAdapter.notifyDataSetChanged();
                        } else {

                            DM1.clear();
                            requestAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onFailure(Call<StatusModel> call, Throwable t) {
                        Log.d("TTTT", "onFailure: " + t.getMessage());
                        Log.d("TTTT", "onFailure: " + call.request().toString());
                        progressDialog.dismiss();
                        Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();

                    }
                });

    }

    //Slider

    private void get_home_sliders() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.connectionServices.class);
        getRegistrationsConnectionServices.get_Sliders().enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                Log.d("TTT", "onResponse: " + response.toString());
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {

                    ArrayList<String> Title = new ArrayList<>();
                    for (int i = 0; i < statusModel.getSliders().size(); i++) {
                        T = statusModel.getSliders().get(i).getId();

                        url_maps.put("http://www.anyservice-ksa.com/mobile/prod_img/" + statusModel.getSliders().get(i).getName(), T);
                        Title.add("" + statusModel.getSliders().get(i).getName());

                    }
                    ///////////////
                    Hawk.put(Constants.images, Title);
                    Hawk.put("y333", "0");
                    ///////////////
                    for (String name : url_maps.keySet()) {
                        CustomSliderView textSliderView = new CustomSliderView(ClientHomeActivity.this);
                        // initialize a SliderLayout
                        textSliderView
                                .description(url_maps.get(name))
                                .image(name)
                                .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                    @Override
                                    public void onSliderClick(BaseSliderView slider) {
                                        startActivity(new Intent(ClientHomeActivity.this, SliderAcivity1.class));

                                    }
                                })
                                .setScaleType(BaseSliderView.ScaleType.Fit);
                        //add your extra information
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra", name);

                        mDemoSlider.addSlider(textSliderView);
                        mDemoSlider1.addSlider(textSliderView);
                    }
                    mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
                    mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
                    mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                    mDemoSlider.setDuration(3000);
                    mDemoSlider.setCustomIndicator(pagerIndicator);
                    mDemoSlider1.setPresetTransformer(SliderLayout.Transformer.Default);
                    mDemoSlider1.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    mDemoSlider1.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
                    mDemoSlider1.setCustomAnimation(new DescriptionAnimation());
                    mDemoSlider1.setDuration(3000);
                    mDemoSlider1.setCustomIndicator(pagerIndicator1);


                    //setSliderViews(IMGs, Descs);

                }


            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });

    }

    private void login(String mobile, String password, String token) {
        Log.d("TTT", "login: 22" + mobile + password + token);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.login(password, mobile, Hawk.get(Constants.TOKEN), Hawk.get(Constants.loginLat), Hawk.get(Constants.loginLong)).enqueue(new Callback<UserModelSatus>() {
            @Override
            public void onResponse(Call<UserModelSatus> call, Response<UserModelSatus> response) {
                Hawk.put(Constants.password, password);
                Hawk.put(Constants.username, mobile);
                Log.d("TTTT", "onResponse: Response00"+call.request().url());
                progressDialog.dismiss();
                UserModelSatus statusModel = response.body();
                UserModel userModel = statusModel.getUser();

                if (statusModel.getStatus() == true && userModel.getActivate().equals("1")) {
                    Picasso.with(ClientHomeActivity.this)
                            .load("http://www.anyservice-ksa.com/mobile/prod_img/" + userModel.getImage())
                            .into(profile_image);

                    Log.d("TTTT", "onResponse: Response11");
                    userModel.setAccepted(statusModel.getAccepted() + "");
                    userModel.setPoints(statusModel.getPoints() + "");
                    userModel.setPeople(statusModel.getPeople() + "");
                    userModel.setRejected(statusModel.getRejected() + "");
                    // Log.d("TTTTTT", "onResponse: "+userModel.getAccepted()+userModel.getPoints()+userModel.getPeople()+userModel.getRejected());
                    Hawk.put(Constants.userData, userModel);
                    Hawk.put(Constants.extraauserData1, statusModel.getCategory());
                    Hawk.put(Constants.extraauserData2, statusModel.getSubcategory());
                    Hawk.put(Constants.extraauserData3, statusModel.getSubcategory2());

                    name.setText(userModel.getName());
                    Hawk.put(Constants.notification_count, statusModel.getNotification_count() + "");
                    if (statusModel.getNotification_count() == 0) {
                        notification_counter.setVisibility(View.GONE);

                    } else {
                        notification_counter.setText(statusModel.getNotification_count() + "");
                        notification_counter.setVisibility(View.VISIBLE);


                    }
                    Stars.setRating(Float.parseFloat(userModel.getRate()));
                    if (userModel.getRate().length() == 1) {
                        rate.setText(userModel.getRate() + ".0");

                    } else {
                        rate.setText(userModel.getRate());

                    }
                    points.setText(userModel.getPoints());
                    requestesApproved.setText(userModel.getAccepted());
                    requestRej.setText(userModel.getRejected());
                    favTo.setText(userModel.getPeople());
                    if (userModel.getRole().equals("2")) {
                        Log.d("TTTTTTTTT", "onResponse: " + statusModel.getSubcategory2().getCategoryNameAr());

                        if (Hawk.get(Constants.Language).equals("ar")) {
                            if (statusModel.getSubcategory2().getNameAr() != null) {
                                Log.d("bbbb", "onResponse: ar"+statusModel.getSubcategory().getNameAr() + " - " + statusModel.getSubcategory2().getNameAr());

                                occupation.setText(statusModel.getSubcategory().getNameAr() + " - " + statusModel.getSubcategory2().getNameAr());
                            } else {
                                occupation.setText(statusModel.getSubcategory().getNameAr());
                            }
                        } else {
                            if (statusModel.getSubcategory2().getCategoryName() != null) {
                                Log.d("bbbb", "onResponse:en "+statusModel.getSubcategory().getName() + " - " + statusModel.getSubcategory2().getName());
                                occupation.setText(statusModel.getSubcategory().getName() + " - " + statusModel.getSubcategory2().getName());
                            } else {
                                occupation.setText(statusModel.getSubcategory().getName());

                            }
                        }
                    }


                } else {
                    userModel = Hawk.get(Constants.userData);
                    logout(userModel.getId());
                }
            }

            @Override
            public void onFailure(Call<UserModelSatus> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
                Log.d("TTTT", "onResponse: fail" + t.getMessage());


            }
        });
    }

    private void edit(String mobile
            , String img
            , String id
            , String name
            , String gender
            , String catgryid
            , String subcategory_id
            , String role) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.edit(mobile, img, id, name, gender, catgryid, subcategory_id, "", "", "" + role, "").enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {

                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    //login(mobile, Hawk.get(Constants.password), Hawk.get(Constants.TOKEN));
                    if (role.equals("1")) {
                        userModel.setRole("1");
                        Hawk.put(Constants.userData, userModel);
                        Hawk.put(Constants.UserType, "0");
                        Intent intent = new Intent(ClientHomeActivity.this, ClientHomeActivity.class);
                        finishAffinity();
                        startActivity(intent);

                    } else {
                        userModel.setRole("2");
                        Hawk.put(Constants.userData, userModel);
                        Hawk.put(Constants.UserType, "1");
                        Intent intent = new Intent(ClientHomeActivity.this, ClientHomeActivity.class);
                        finishAffinity();
                        startActivity(intent);
                    }

                } else {
                    Snackbar.make(parentLayout, "" + getString(R.string.somethingwentwrong), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                if (t.getMessage().intern().contains("BEGIN_ARRAY")) {
                    if (role.equals("1")) {
                        userModel.setRole("1");
                        Hawk.put(Constants.userData, userModel);
                        Hawk.put(Constants.UserType, "0");
                        Intent intent = new Intent(ClientHomeActivity.this, ClientHomeActivity.class);
                        finishAffinity();
                        startActivity(intent);

                    } else {
                        userModel.setRole("2");
                        Hawk.put(Constants.userData, userModel);
                        Hawk.put(Constants.UserType, "1");
                        Intent intent = new Intent(ClientHomeActivity.this, ClientHomeActivity.class);
                        finishAffinity();
                        startActivity(intent);
                    }
                }
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
                progressDialog.dismiss();

            }
        });
    }

    private void logout(String id) {
        //update_token
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.update_token(id, "0", "").enqueue(new Callback<DummyModel>() {
            @Override
            public void onResponse(Call<DummyModel> call, Response<DummyModel> response) {
                progressDialog.dismiss();
                finish();
                String token = Hawk.get(Constants.TOKEN);
                Hawk.deleteAll();
                Hawk.destroy();
                Hawk.init(ClientHomeActivity.this).build();
                Hawk.put(Constants.TOKEN, token);
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<DummyModel> call, Throwable t) {
                Log.d("TTTTTT", "onFailure: " + call.request().toString());
                Log.d("TTTTTT", "onFailure: " + call.request().url());
                Log.d("TTTTTT", "onFailure: " + t.getMessage());
                progressDialog.dismiss();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Hawk.contains(Constants.notification_count)) {
            if (Hawk.get(Constants.notification_count).equals("0")) {
                notification_counter.setVisibility(View.GONE);
            } else {
                notification_counter.setVisibility(View.VISIBLE);

                notification_counter.setText(Hawk.get(Constants.notification_count) + "");

            }

        }
        if (Hawk.contains(Constants.reload)) {

            if (Hawk.get(Constants.reload).equals("1")) {
                Hawk.put(Constants.reload, "0");

            }
        }
        if (Hawk.contains(Constants.userData)) {
            Log.d("TTTTTTTTTTTTT", "Heeeeereeeeee");
            login(Hawk.get(Constants.username) + "", Hawk.get(Constants.password) + "", Hawk.get(Constants.TOKEN) + "");
        }
        if (Hawk.contains(Constants.userData) && userModel.getRole().equals("2")) {
            if (userModel.getSubcategoryId() != null && (!Hawk.get(Constants.UserType).equals("0"))
            ) {
                if (userModel.getSubcategory_id2() != null && !userModel.getSubcategory_id2().equals(""))
                    getRequestes("", "0", "", "" + userModel.getSubcategoryId()
                            , "" + userModel.getCategoryId(), "" + userModel.getId(), userModel.getSubcategory_id2());
            } else {
                getRequestes("", "0", "", "" + userModel.getSubcategoryId()
                        , "" + userModel.getCategoryId(), "" + userModel.getId(), "");
            }
        }
    }
}

