package com.spider.star.nexbaseclient.conformation.viewmodal;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.spider.star.nexbaseclient.databinding.ConformationBinding;
import com.spider.star.nexbaseclient.rateing.RateingActivity;
import com.spider.star.nexbaseclient.trackexecutive.TrackexecutiveActivity;

public class ConformationVm {

    Activity activity;
    ConformationBinding binding;

    public ConformationVm(Activity activity, ConformationBinding binding) {

        this.activity=activity;
        this.binding=binding;

    }

    public void OnClickBackpress(View view){
        activity.finish();
    }

    public void OnClicktrackexective(View view){

        activity.startActivity(new Intent(activity,TrackexecutiveActivity.class));
    }

    public void OnClickrateus(View view){

        activity.startActivity(new Intent(activity,RateingActivity.class));
    }
}
