package com.spider.star.nexbaseclient.ticketcreation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.spider.star.nexbaseclient.R;
import com.spider.star.nexbaseclient.databinding.TicketcreationBinding;
import com.spider.star.nexbaseclient.login.MainActivity;
import com.spider.star.nexbaseclient.sharedpreference.MySession;
import com.spider.star.nexbaseclient.ticketcreation.viewmodal.CreationTicketVm;

public class CreationTicketActivity extends AppCompatActivity {

    TicketcreationBinding binding;
    CreationTicketVm creationTicketVm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindview();
    }

    private void bindview() {

        binding=DataBindingUtil.setContentView(this,R.layout.ticketcreation);
        creationTicketVm=new CreationTicketVm(this,binding);
        binding.setCreationTicketVm(creationTicketVm);

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.logout, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        TextView logout=dialogView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                MySession.getInstance(CreationTicketActivity.this).clearUserdata();
                startActivity(new Intent(CreationTicketActivity.this,MainActivity.class));
                alertDialog.dismiss();
            }
        });

        TextView no=dialogView.findViewById(R.id.no);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


        alertDialog.show();
    }
}
