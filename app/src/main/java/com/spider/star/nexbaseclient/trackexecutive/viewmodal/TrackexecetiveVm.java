package com.spider.star.nexbaseclient.trackexecutive.viewmodal;


import android.app.Activity;
import android.location.LocationManager;
import com.spider.star.nexbaseclient.databinding.ActivityTrackexecutiveBinding;
import com.google.android.gms.maps.GoogleMap;


public class TrackexecetiveVm  {
    Activity activity;
    ActivityTrackexecutiveBinding binding;
    private GoogleMap map;

    private LocationManager locationManager;

    public TrackexecetiveVm(Activity activity, ActivityTrackexecutiveBinding binding) {

        this.activity=activity;
        this.binding=binding;
    }



}
