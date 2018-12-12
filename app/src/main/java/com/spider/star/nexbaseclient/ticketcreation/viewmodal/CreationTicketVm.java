package com.spider.star.nexbaseclient.ticketcreation.viewmodal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.spider.star.nexbaseclient.R;
import com.spider.star.nexbaseclient.api.APIConfiguration;
import com.spider.star.nexbaseclient.api.APIErrorHandler;
import com.spider.star.nexbaseclient.api.ApiCall;
import com.spider.star.nexbaseclient.api.GeneralResponse;
import com.spider.star.nexbaseclient.conformation.ConformationActivity;
import com.spider.star.nexbaseclient.databinding.TicketcreationBinding;
import com.spider.star.nexbaseclient.sharedpreference.MySession;
import com.spider.star.nexbaseclient.ticketcreation.modal.Data;
import com.spider.star.nexbaseclient.ticketcreation.modal.SelectProblem;
import com.spider.star.nexbaseclient.ticketcreation.modal.TicketCreation;
import com.spider.star.nexbaseclient.ticketcreation.modal.TicketCreationResponse;
import com.spider.star.nexbaseclient.utils.InternetChecker;
import com.spider.star.nexbaseclient.utils.MyProgressDialog;
import com.spider.star.nexbaseclient.utils.MySnackBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CreationTicketVm {

    TicketcreationBinding binding;
    Activity activity;
    MyProgressDialog myProgressDialog;
    ArrayList<String> problemlist;
    TicketCreation ticketCreation;
    String select_Problem = "";
    String date = "";
    String problem_type="";



    public CreationTicketVm(Activity activity, TicketcreationBinding binding) {

        this.activity = activity;
        this.binding = binding;
        ticketCreation = new TicketCreation();
        problemlist = new ArrayList<>();
        myProgressDialog = new MyProgressDialog();

        radioclickAction();
        CallApi();

    }


    public void onClickBackpress(View view) {

        activity.finish();
    }

    private void spinnerClickaction(List<SelectProblem> list) {

        binding.road.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                select_Problem = parent.getItemAtPosition(pos).toString();
                SelectProblem selectProblem=list.get(pos);
                problem_type=selectProblem.getId();

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void radioclickAction() {

        //int selectedId = binding.radioGroup.getCheckedRadioButtonId();

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId) {

                    case R.id.radioButton_one: {

                        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                        month = month + 1;


                        int second = calendar.get(Calendar.SECOND);
                        int minute = calendar.get(Calendar.MINUTE);
                        int hourofday = calendar.get(Calendar.HOUR_OF_DAY);

                        date = dayOfMonth + "-" + month + "-" + year+" "+hourofday+":"+minute+":"+second;

                        break;

                    }
                    case R.id.radioButton_two: {

                        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                        month = month + 1;


                                        int second = calendar.get(Calendar.SECOND);
                                        int minute = calendar.get(Calendar.MINUTE);
                                        int hourofday = calendar.get(Calendar.HOUR_OF_DAY);


                                        date = day + "-" + month + "-" + year+" "+hourofday+":"+minute+":"+second;



                                    }
                                }, year, month, dayOfMonth);

                        datePickerDialog.show();

                        break;
                    }

                }
            }
        });
    }


    public void OnClickticketsubmit(View view) {

        if(binding.description.getText().toString().trim().length()==0){

            MySnackBar.getInstance().showNagativeSnackBar(activity, "Please enter the description");
        }

       else if (date.equals("")) {

            MySnackBar.getInstance().showNagativeSnackBar(activity, "Please select RequestType");

        } else {

            CallticketCreation();
        }


    }

    private void CallticketCreation() {


        ticketCreation.setBooked_date(date);
        ticketCreation.setMobile(MySession.getInstance(activity).getMobileNumber());
        ticketCreation.setProblem_type(select_Problem);
        ticketCreation.setProblem_reported(binding.description.getText().toString());

        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            ApiCall api = APIConfiguration.getInstance().createService(ApiCall.class);
            Observable<Response<TicketCreationResponse>> observable = api.ticketCreation(ticketCreation);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();

                        TicketCreationResponse creationResponse = responses.body();

                        if (creationResponse.getStatus() == 200) {

                            Toast.makeText(activity, "" + creationResponse.getMessage(), Toast.LENGTH_LONG).show();

                            MySession.getInstance(activity).saveTicketId(creationResponse.getTicket_number());

                            activity.startActivity(new Intent(activity, ConformationActivity.class).putExtra("ticket_id",creationResponse.getTicket_id()));

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

    private void CallApi() {

        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            ApiCall api = APIConfiguration.getInstance().createService(ApiCall.class);
            Observable<Response<Data>> observable = api.getProblemList();
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {

                            List<SelectProblem> list = responses.body().getSelectProblems();

                            spinnerClickaction(list);

                            for (SelectProblem da : list) {

                                problemlist.add(da.getName());

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                        activity.getApplicationContext(), R.layout.custom_spinner, problemlist);

                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                binding.road.setAdapter(adapter);
                            }

                        } else {
                            if (responses.body() != null) {
                                // APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
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
