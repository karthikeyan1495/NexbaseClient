package com.spider.star.nexbaseclient.conformation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import android.widget.Toast;

import com.spider.star.nexbaseclient.R;
import com.spider.star.nexbaseclient.conformation.viewmodal.ConformationVm;
import com.spider.star.nexbaseclient.databinding.ConformationBinding;

public class ConformationActivity extends AppCompatActivity {

    ConformationBinding binding;
    ConformationVm conformationVm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},0);
                return;
            }
        }

        bindview();
    }

    private void bindview() {

        binding=DataBindingUtil.setContentView(this,R.layout.conformation);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        Bundle bundle=getIntent().getExtras();

        String ticket_id=bundle.getString("ticket_id");

        String white = "Ticket id is ";
        SpannableString whiteSpannable= new SpannableString(white);
        whiteSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, white.length(), 0);
        builder.append(whiteSpannable);

        SpannableString redSpannable= new SpannableString(ticket_id);
        redSpannable.setSpan(new ForegroundColorSpan(Color.rgb(57,133,165)), 0, ticket_id.length(), 0);
        builder.append(redSpannable);
        binding.ticketId.setText(builder,TextView.BufferType.SPANNABLE);

        conformationVm=new ConformationVm(this,binding);
        binding.setConformationVm(conformationVm);
    }
}
