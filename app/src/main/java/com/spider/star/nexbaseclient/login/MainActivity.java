package com.spider.star.nexbaseclient.login;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.spider.star.nexbaseclient.R;
import com.spider.star.nexbaseclient.databinding.ActivityMainBinding;
import com.spider.star.nexbaseclient.login.modal.Login;
import com.spider.star.nexbaseclient.login.viewmodal.MainActivityVm;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MainActivityVm mainActivityVm;
    Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        bindview();
    }

    private void bindview() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        login=new Login();
        mainActivityVm=new MainActivityVm(this,binding,login);
        binding.setMainActivityVm(mainActivityVm);
        binding.setLogin(login);

    }
}
