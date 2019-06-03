package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.smatech.ay5edma.Adapters.CatgryAdapter;
import com.example.smatech.ay5edma.Models.Modelss.CategoryModel;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.SubCategoryModel;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpecificCatgryActivity extends AppCompatActivity {


    RecyclerView RV;
    ArrayList<CategoryModel> DM;
    CatgryAdapter catgryAdapter;
    View parentLayout;
    ProgressDialog progressDialog;
    LinearLayout Go;
    int temp = 0;
    EditText Search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_catgry);
        ImageView back;
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(Hawk.get(Constants.SubCat1) + "");
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        parentLayout = findViewById(android.R.id.content);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));

        Search = findViewById(R.id.Search);
        Search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (!Search.getText().toString().equals("")) {
                        DM.clear();
                        temp = 1;
                        catgryAdapter.notifyDataSetChanged();
                        getSubCatg(Hawk.get(Constants.mCatgrID) + "", Search.getText().toString());
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
                                    SpecificCatgryActivity.this.runOnUiThread(new Runnable() {

                                        public void run() {
                                            DM.clear();
                                            catgryAdapter.notifyDataSetChanged();
                                            getSubCatg(Hawk.get(Constants.mCatgrID) + "");
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

        //initialization
        Hawk.put((Constants.Addlocationdlong), "");
        Hawk.put((Constants.Addlocationdlat), "");

        // bottom nav
        LinearLayout homeLayout, requestLayout, notificationLayout, settingLayout;
        homeLayout = findViewById(R.id.homeLayout);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                                    finishAffinity();
                                    startActivity(new Intent(SpecificCatgryActivity.this, LoginActiivty.class));

                                }
                            })
                            .show();
                } else {
                    startActivity(new Intent(SpecificCatgryActivity.this, RequestsActivity.class));

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
                                    finishAffinity();
                                    startActivity(new Intent(SpecificCatgryActivity.this, LoginActiivty.class));

                                }
                            })
                            .show();
                } else {
                    startActivity(new Intent(SpecificCatgryActivity.this, NotificationsActivity.class));

                }

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
                                    finishAffinity();
                                    startActivity(new Intent(SpecificCatgryActivity.this, LoginActiivty.class));

                                }
                            })
                            .show();
                } else {
                    startActivity(new Intent(SpecificCatgryActivity.this, SettingActiviy.class));

                }

            }
        });
        //

        DM = new ArrayList<>();

        RV = findViewById(R.id.RV);
        catgryAdapter = new CatgryAdapter(DM, this, new CatgryAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                if (Hawk.get(Constants.Language).equals("en")) {

                    Hawk.put(Constants.SubCat2, DM.get(position).getName());
                } else {
                    Hawk.put(Constants.SubCat2, DM.get(position).getNameAr());

                }
                Hawk.put(Constants.mSubCatgrID, DM.get(position).getId());
                ////catgry Data
                if (Hawk.get(Constants.Language).equals("en")) {
                    Hawk.put(Constants.SubCat1, DM.get(position).getCategoryName());

                } else {
                    Hawk.put(Constants.SubCat1, DM.get(position).getCategoryNameAr());

                }
                Hawk.put(Constants.mCatgrID, DM.get(position).getCategoryId());

                // Log.d("TTT", "setOnItemClick: "+DM.get(position).getDesc()+"----"+Hawk.get(Constants.SubCat2)+"");
                Intent intent = new Intent(SpecificCatgryActivity.this, RequestDescriptionActivity.class);
                startActivity(intent);
            }
        });
        RV.setAdapter(catgryAdapter);
        RV.setLayoutManager(new GridLayoutManager(this, 3));
        catgryAdapter.notifyDataSetChanged();
        getSubCatg(Hawk.get(Constants.mCatgrID) + "");
        progressDialog.show();
        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Search.getText().toString().equals("")) {
                    DM.clear();
                    temp = 1;
                    catgryAdapter.notifyDataSetChanged();
                    getSubCatg(Hawk.get(Constants.mCatgrID) + "", Search.getText().toString());
                }
            }
        });

        if(getIntent().hasExtra("search")){
            Search.setText(getIntent().getStringExtra("search"));
            DM.clear();
            temp = 1;
            catgryAdapter.notifyDataSetChanged();
            getSubCatg( "", Search.getText().toString());
            toolbar_title.setText( "");


        }
        if (Hawk.contains(Constants.skipe)) {
            if (Hawk.get(Constants.skipe).equals(true)) {
                notification_counter.setVisibility(View.GONE);
            }
        }
    }

    private void getSubCatg(String category_id, String search) {
        Log.d("TTTT", "getSubCatg: "+category_id);
        Log.d("TTTT", "getSubCatg: "+search);
        Log.d("TTTT", "getSubCatg: ");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_SubCategory(category_id, ""  +search,"True").enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                Log.d("TTTT", "onResponse: "+response.toString());
                progressDialog.dismiss();
                StatusModel statusModel = response.body();

                if (statusModel.getStatus() == true) {
                    ArrayList<SubCategoryModel> subCategoryModels = statusModel.getSubCategoryModels();

                    for (int i = 0; i < subCategoryModels.size(); i++) {
                        CategoryModel categoryModel = new CategoryModel();
                        categoryModel.setName(subCategoryModels.get(i).getName() + "");
                        categoryModel.setNameAr(subCategoryModels.get(i).getNameAr() + "");
                        categoryModel.setId(subCategoryModels.get(i).getId());
                        categoryModel.setImage(subCategoryModels.get(i).getImage());
                        categoryModel.setCategoryId(subCategoryModels.get(i).getCategoryId());
                        categoryModel.setCategoryName(subCategoryModels.get(i).getCategoryName());
                        categoryModel.setCategoryNameAr(subCategoryModels.get(i).getCategoryNameAr());
                        DM.add(categoryModel);
                    }
                    Log.d("TTTT", "onResponse: "+DM.size());
                    catgryAdapter.notifyDataSetChanged();
                } else {

                }

            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
                progressDialog.dismiss();


            }
        });

    }
    private void getSubCatg(String category_id) {
        Log.d("TTTT", "getSubCatg: "+category_id);
        Log.d("TTTT", "getSubCatg: ");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_SubCategory(category_id,"True").enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                Log.d("TTTT", "onResponse: "+response.toString());
                progressDialog.dismiss();
                StatusModel statusModel = response.body();

                if (statusModel.getStatus() == true) {
                    ArrayList<SubCategoryModel> subCategoryModels = statusModel.getSubCategoryModels();

                    for (int i = 0; i < subCategoryModels.size(); i++) {
                        CategoryModel categoryModel = new CategoryModel();
                        categoryModel.setName(subCategoryModels.get(i).getName() + "");
                        categoryModel.setNameAr(subCategoryModels.get(i).getNameAr() + "");
                        categoryModel.setId(subCategoryModels.get(i).getId());
                        categoryModel.setImage(subCategoryModels.get(i).getImage());
                        categoryModel.setCategoryId(subCategoryModels.get(i).getCategoryId());
                        categoryModel.setCategoryName(subCategoryModels.get(i).getCategoryName());
                        categoryModel.setCategoryNameAr(subCategoryModels.get(i).getCategoryNameAr());
                        DM.add(categoryModel);
                    }
                    Log.d("TTTT", "onResponse: "+DM.size());
                    catgryAdapter.notifyDataSetChanged();
                } else {

                }

            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
                progressDialog.dismiss();


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



//// Accepted offer --> is not removed