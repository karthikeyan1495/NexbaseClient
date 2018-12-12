package com.spider.star.nexbaseclient.trackexecutive;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.spider.star.nexbaseclient.R;
import com.spider.star.nexbaseclient.api.APIConfiguration;
import com.spider.star.nexbaseclient.api.APIErrorHandler;
import com.spider.star.nexbaseclient.api.ApiCall;
import com.spider.star.nexbaseclient.api.GeneralResponse;
import com.spider.star.nexbaseclient.databinding.ActivityTrackexecutiveBinding;
import com.spider.star.nexbaseclient.otp.OtpActivity;
import com.spider.star.nexbaseclient.sharedpreference.MySession;
import com.spider.star.nexbaseclient.trackexecutive.modal.TrackLatlangModal;
import com.spider.star.nexbaseclient.trackexecutive.viewmodal.TrackexecetiveVm;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.spider.star.nexbaseclient.utils.InternetChecker;
import com.spider.star.nexbaseclient.utils.MyProgressDialog;
import com.spider.star.nexbaseclient.utils.MySnackBar;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class TrackexecutiveActivity extends AppCompatActivity implements OnMapReadyCallback {

    ActivityTrackexecutiveBinding binding;
    TrackexecetiveVm trackexecetiveVm;

    private GoogleMap map;

    private LocationManager locationManager;
    TrackLatlangModal trackLatlangModal;

    double latitude;
    double longitude;
    MyProgressDialog myProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trackexecutive);
        trackexecetiveVm = new TrackexecetiveVm(this, binding);
        trackLatlangModal=new TrackLatlangModal();
        myProgressDialog=new MyProgressDialog();
        binding.setTrackexecutiveVm(trackexecetiveVm);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        map = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        map.setMyLocationEnabled(true);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000000, 0, new LocationListener() {

                @Override
                public void onLocationChanged(Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    callAPI(latitude,longitude);
                    LatLng latLng = new LatLng(latitude, longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());

                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str = addressList.get(0).getLocality() + " , ";
                        str += addressList.get(0).getCountryName();

                        map.addMarker(new MarkerOptions().position(latLng).title(str));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                     latitude = location.getLatitude();
                     longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());

                    callAPI(latitude,longitude);

                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str = addressList.get(0).getLocality() + " , ";
                        str += addressList.get(0).getCountryName();

                        map.addMarker(new MarkerOptions().position(latLng).title(str));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }


    }

    private void callAPI(double latitude, double longitude) {


        String lati = Double.toString(latitude);
        String longti=Double.toString(longitude);
        trackLatlangModal.setLatitude(lati);
        trackLatlangModal.setLongitude(longti);
        trackLatlangModal.setMobile(MySession.getInstance(getApplicationContext()).getMobileNumber());

       // Toast.makeText(getApplicationContext(),"latitude"+latitude+"longitude:"+longitude,Toast.LENGTH_LONG).show();

        binding.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (InternetChecker.getInstance().isReachable()) {
                    myProgressDialog.showDialog(getApplicationContext());
                    ApiCall api = APIConfiguration.getInstance().createService(ApiCall.class);
                    Observable<Response<GeneralResponse>> observable = api.clientLocation(trackLatlangModal);
                    observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(responses -> {
                                myProgressDialog.dismissDialog();

                                GeneralResponse responsive=responses.body();
                                if (responsive.getCode() == 200) {

                                    Toast.makeText(getApplicationContext(),responsive.getMessage(),Toast.LENGTH_LONG).show();

                                } else {
                                    if (responses.body() != null) {
                                        APIErrorHandler.getInstance().errorHandler(TrackexecutiveActivity.this, responses.code(), responses.body().getMessage());
                                    } else {
                                        APIErrorHandler.getInstance().errorHandler(TrackexecutiveActivity.this, responses.code(), responses.errorBody().string());
                                    }
                                }
                            }, throwable -> {
                                myProgressDialog.dismissDialog();
                                MySnackBar.getInstance().showNagativeSnackBar(TrackexecutiveActivity.this, TrackexecutiveActivity.this.getString(R.string.something_went_wrong_while_retrieving_information));

                            });
                } else {
                    MySnackBar.getInstance().showNagativeSnackBar(TrackexecutiveActivity.this, TrackexecutiveActivity.this.getString(R.string.no_internet));
                }




            }
        });





    }
}
