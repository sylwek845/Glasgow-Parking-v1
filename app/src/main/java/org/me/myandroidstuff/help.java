package org.me.myandroidstuff;

import android.app.Activity;
import android.os.Bundle;



import static org.me.myandroidstuff.R.layout.help;

/**
 * Created by Sylwester Zalewski  on 17/03/2015.
 * App Name: Glasgow Parking
 * Student ID: S1434548
 * Glasgow Caledonian University
 */
public class help extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(help);


    }
    @Override
    public void onBackPressed()
    {
        ParkingDataHold.setOtherActivityActive(false);
        super.onBackPressed();  // optional depending on your needs
    }
}
