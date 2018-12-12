package com.spider.star.nexbaseclient.rateing.viewmodal;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.spider.star.nexbaseclient.R;
import com.spider.star.nexbaseclient.api.APIConfiguration;
import com.spider.star.nexbaseclient.api.APIErrorHandler;
import com.spider.star.nexbaseclient.api.ApiCall;
import com.spider.star.nexbaseclient.api.GeneralResponse;
import com.spider.star.nexbaseclient.databinding.ActivityRateingBinding;
import com.spider.star.nexbaseclient.rateing.modal.RatingModal;
import com.spider.star.nexbaseclient.sharedpreference.MySession;
import com.spider.star.nexbaseclient.trackexecutive.TrackexecutiveActivity;
import com.spider.star.nexbaseclient.utils.InternetChecker;
import com.spider.star.nexbaseclient.utils.MyProgressDialog;
import com.spider.star.nexbaseclient.utils.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class RatingVm {

    Activity activity;
    ActivityRateingBinding binding;
    RatingModal ratingModal;
    MyProgressDialog myProgressDialog;



    public RatingVm(Activity activity, ActivityRateingBinding binding) {

        this.activity=activity;
        this.binding=binding;
        myProgressDialog=new MyProgressDialog();
        ratingModal=new RatingModal();

        view();

    }

    private void view() {

        binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                LayerDrawable stars = (LayerDrawable) binding.ratingBar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(Color.rgb(57,133,165),PorterDuff.Mode.SRC_ATOP);
            }
        });

    }

    public void OnClickbackpress(View view){

        activity.finish();

    }


    public void OnClickCloseTicket(View view){

        ratingModal.setCommit(binding.feedBack.getText().toString());
        ratingModal.setTicket_number(MySession.getInstance(activity).getTicketId());
        ratingModal.setRating("3");

        if(binding.feedBack.getText().toString().trim().length()==0){

            MySnackBar.getInstance().showNagativeSnackBar(activity,"Please enter the feedback");
        }else {

            CallAPI();
        }



    }

    private void CallAPI() {


        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            ApiCall api = APIConfiguration.getInstance().createService(ApiCall.class);
            Observable<Response<GeneralResponse>> observable = api.clientFeedback(ratingModal);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();

                        GeneralResponse responsive=responses.body();
                        if (responsive.getCode() == 200) {

                            Toast.makeText(activity,responsive.getMessage(),Toast.LENGTH_LONG).show();

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
