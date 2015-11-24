package org.me.myandroidstuff;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Sylwester Zalewski  on 17/03/2015.
 * App Name: Glasgow Parking
 * Student ID: S1434548
 * Glasgow Caledonian University
 */

public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(setting);


        addPreferencesFromResource(R.xml.preferences);
    }
    @Override
    public void onBackPressed()
    {
        ParkingDataHold.setOtherActivityActive(false);
        super.onBackPressed();  // optional depending on your needs
    }

}
