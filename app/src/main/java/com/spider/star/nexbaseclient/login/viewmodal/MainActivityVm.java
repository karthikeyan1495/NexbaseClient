package com.spider.star.nexbaseclient.login.viewmodal;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import com.spider.star.nexbaseclient.R;
import com.spider.star.nexbaseclient.api.APIConfiguration;
import com.spider.star.nexbaseclient.api.APIErrorHandler;
import com.spider.star.nexbaseclient.api.ApiCall;
import com.spider.star.nexbaseclient.api.GeneralResponse;
import com.spider.star.nexbaseclient.databinding.ActivityMainBinding;
import com.spider.star.nexbaseclient.login.modal.Login;
import com.spider.star.nexbaseclient.otp.OtpActivity;
import com.spider.star.nexbaseclient.sharedpreference.MySession;
import com.spider.star.nexbaseclient.utils.InternetChecker;
import com.spider.star.nexbaseclient.utils.MyProgressDialog;
import com.spider.star.nexbaseclient.utils.MySnackBar;
import com.spider.star.nexbaseclient.utils.Util;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class MainActivityVm {
    Activity activity;
    ActivityMainBinding binding;
    MyProgressDialog myProgressDialog;
    Login login;

    public MainActivityVm(Activity activity, ActivityMainBinding binding, Login login) {

        this.activity = activity;
        this.binding = binding;
        this.login = login;
        myProgressDialog = new MyProgressDialog();

    }

    public void OnClicksubmit(View view) {

        if(binding.mobileNo.getText().toString().trim().length() == 0){

            MySnackBar.getInstance().showNagativeSnackBar(activity,"Please enter the mobile Number");
        }

        else if (! Util.getInstance().Mobile_No_Validator(login.getMobile())) {

            MySnackBar.getInstance().showNagativeSnackBar(activity, "Please enter correct mobile Number");

        } else {

            CallApi();
        }

    }

    private void CallApi() {

        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            ApiCall api = APIConfiguration.getInstance().createService(ApiCall.class);
            Observable<Response<GeneralResponse>> observable = api.login(login);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();

                        GeneralResponse responsive=responses.body();
                        if (responsive.getCode() == 200) {

                            Toast.makeText(activity,responsive.getMessage(),Toast.LENGTH_LONG).show();
                            MySession.getInstance(activity).saveMobileNo(login.getMobile());
                            activity.startActivity(new Intent(activity,OtpActivity.class));

                        } else {
                            if (responses.body() != null) {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
                            } else {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.errorBody().string());
                            }
                        }
                    }, throwable -> {
                        myProgressDialog.dismissDialog();
                        MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));

                    });
        } else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.no_internet));
        }
    }
}
