package com.spider.star.nexbaseclient.otp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.spider.star.nexbaseclient.R;
import com.spider.star.nexbaseclient.databinding.OtpBinding;
import com.spider.star.nexbaseclient.otp.modal.OtpModal;
import com.spider.star.nexbaseclient.otp.viewmodal.OtpVm;


public class OtpActivity extends AppCompatActivity {

    OtpBinding binding;
    OtpVm otpVm;
    OtpModal otpModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       bindview();

    }

    private void bindview() {

        binding=DataBindingUtil.setContentView(this,R.layout.otp);
        otpModal=new OtpModal();
        otpVm=new OtpVm(this,binding,otpModal);
        binding.setOtpVm(otpVm);
        binding.setOtpModal(otpModal);

    }
}
