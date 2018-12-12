package com.spider.star.nexbaseclient.initalscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.spider.star.nexbaseclient.R;
import com.spider.star.nexbaseclient.login.MainActivity;
import com.spider.star.nexbaseclient.sharedpreference.MySession;
import com.spider.star.nexbaseclient.ticketcreation.CreationTicketActivity;

public class SplashActivity extends AppCompatActivity {


    private static int SPLASH_TIME_OUT = 500;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        textView=(TextView)findViewById(R.id.service_engineer_app_view);
        slideToTop(textView);


    }

    private void waitingTime() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveToNextScreen();
            }
        }, SPLASH_TIME_OUT);
    }

    private void moveToNextScreen() {


        if(MySession.getInstance(this).isLogin()){

            startActivity(new Intent(this,CreationTicketActivity.class));
            finish();
        }else {

            startActivity(new Intent(this,MainActivity.class));
            finish();
        }


    }

    private void slideToTop(View view) {


        Animation bottomUp = AnimationUtils.loadAnimation(this,
                R.anim.anim);
        bottomUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                waitingTime();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(bottomUp);
        view.setVisibility(View.VISIBLE);
    }

}
