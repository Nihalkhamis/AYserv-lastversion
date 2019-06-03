package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModelSatus;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import cn.iwgang.countdownview.CountdownView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NumberConfirmationForPasswordActivity extends AppCompatActivity {

    PinView firstPinView;
    Button Done;
    View parentLayout;
    TextView resend;
    String number;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_confirmation);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));
        Hawk.put(Constants.STUCK, "1");
        resend = findViewById(R.id.resend);
        resend.getBackground().setAlpha(0);
        Done = findViewById(R.id.Done);
        parentLayout = findViewById(android.R.id.content);
        number=getIntent().getStringExtra("mob");
        CountdownView mCvCountdownView = (CountdownView) findViewById(R.id.cv_countdownViewTest1);
        mCvCountdownView.start((60000 * 4 + 59000)); // Millisecond
        mCvCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                resend.getBackground().setAlpha(255);
                resend.setTextColor(Color.BLACK);
                resend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(NumberConfirmationForPasswordActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                        //resend.getBackground().setAlpha(0);
                        resendVertificationCode(number);

                    }
                });

            }
        });
        /*if(resend.getBackground().getAlpha()==255){

        }*/
        firstPinView = findViewById(R.id.firstPinView);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstPinView.getText().toString().equals("")) {
                } else {
                    verifyAccount(firstPinView.getText().toString() + "");
                }
            }
        });
    }

    private void resendVertificationCode( String number) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.resend_code(number+"").enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                Log.d("TTT", "onResponse: "+response.toString());
                if (statusModel.getStatus()) {

                } else {

                    if (Hawk.get(Constants.Language).equals("en")) {
                        Snackbar.make(parentLayout, "" + statusModel.getMessage(), Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    } else {
                        Snackbar.make(parentLayout, "" + statusModel.getMessage_ar(), Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    }
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

    private void verifyAccount(String code) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.validateAccount(code).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                Log.d("TTTT", "onResponse: " + response.raw());
                Log.d("TTTT", "onResponse: " + response.message());
                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    //login(Hawk.get(Constants.username), Hawk.get(Constants.password), Hawk.get(Constants.TOKEN));
                    Hawk.put(Constants.STUCK, "0");
                    startActivity(new Intent(NumberConfirmationForPasswordActivity.this
                            ,ChangePasswordFromForgetActivity.class).putExtra("code",code));

                } else {
                    if (Hawk.get(Constants.Language).equals("en")) {
                        Snackbar.make(parentLayout, "" + statusModel.getMessage(), Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    } else {
                        Snackbar.make(parentLayout, "" + statusModel.getMessage_ar(), Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    }
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

}
