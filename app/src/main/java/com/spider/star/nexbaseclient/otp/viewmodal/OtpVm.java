package com.spider.star.nexbaseclient.otp.viewmodal;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.spider.star.nexbaseclient.R;
import com.spider.star.nexbaseclient.api.APIConfiguration;
import com.spider.star.nexbaseclient.api.APIErrorHandler;
import com.spider.star.nexbaseclient.api.ApiCall;
import com.spider.star.nexbaseclient.api.GeneralResponse;
import com.spider.star.nexbaseclient.databinding.OtpBinding;
import com.spider.star.nexbaseclient.otp.modal.OtpModal;
import com.spider.star.nexbaseclient.sharedpreference.MySession;
import com.spider.star.nexbaseclient.ticketcreation.CreationTicketActivity;
import com.spider.star.nexbaseclient.utils.InternetChecker;
import com.spider.star.nexbaseclient.utils.MyProgressDialog;
import com.spider.star.nexbaseclient.utils.MySnackBar;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class OtpVm {

    Activity activity;
    OtpBinding binding;
    MyProgressDialog myProgressDialog;
    OtpModal otpModal;


    public OtpVm(Activity activity, OtpBinding binding, OtpModal otpModal) {

        this.activity = activity;
        this.binding = binding;
        this.otpModal =otpModal;
        myProgressDialog=new MyProgressDialog();

    }

    public void OnClickLogin(View view){


        Validation();


    }

    private void Validation() {


        if(binding.otp.getText().toString().trim().length() == 0){

            MySnackBar.getInstance().showNagativeSnackBar(activity,"Please enter the OTP");

        }else {

            callOTPApi();
        }



    }

    private void callOTPApi() {

        otpModal.setMobile(MySession.getInstance(activity).getMobileNumber());

        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            ApiCall api = APIConfiguration.getInstance().createService(ApiCall.class);
            Observable<Response<GeneralResponse>> observable = api.otpcheck(otpModal);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();

                        GeneralResponse response=responses.body();
                        if (response.getCode() == 200) {

                            Toast.makeText(activity,""+response.getMessage(),Toast.LENGTH_LONG).show();
                            activity.finishAffinity();
                            MySession.getInstance(activity).saveLoginStatus(true);
                            activity.startActivity(new Intent(activity,CreationTicketActivity.class));

                        } else {
                            if (responses.body() != null) {
                                   APIErrorHandler.getInstance().errorHandler(activity, response.getCode(), response.getMessage());
                            } else {
                                APIErrorHandler.getInstance().errorHandler(activity,response.getCode(), responses.errorBody().string());
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
