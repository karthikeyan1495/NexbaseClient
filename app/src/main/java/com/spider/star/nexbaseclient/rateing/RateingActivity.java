package com.spider.star.nexbaseclient.rateing;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.spider.star.nexbaseclient.R;
import com.spider.star.nexbaseclient.databinding.ActivityRateingBinding;
import com.spider.star.nexbaseclient.rateing.viewmodal.RatingVm;

public class RateingActivity extends AppCompatActivity {

    ActivityRateingBinding binding;
    RatingVm ratingVm;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding=DataBindingUtil.setContentView(this,R.layout.activity_rateing);
        ratingVm=new RatingVm(this,binding);
        binding.setRatingVm(ratingVm);

    }
}
