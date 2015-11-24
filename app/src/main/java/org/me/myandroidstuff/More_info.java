package org.me.myandroidstuff;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import static org.me.myandroidstuff.R.layout.more_info;

/**
 * Created by Sylwester Zalewski  on 17/03/2015.
 * App Name: Glasgow Parking
 * Student ID: S1434548
 * Glasgow Caledonian University
 */
public class More_info extends Activity {

    private TextView text_name;
    private TextView text_spaces_left;
    private TextView text_total_spaces;
    private TextView text_occupied_spaces;
    private TextView text_percentage;
    private TextView text_distance;
    private TextView text_time;
    private ImageView imageView;
    private GoogleMap map;
    private ParkingData parkingData = ParkingDataHold.getParkingData();
    private final LatLng PARKING_LOCATION = new LatLng(parkingData.GetLatitude(),parkingData.GetLongitude());
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(more_info);

        text_distance = (TextView) findViewById(R.id.more_distance);
        text_name  = (TextView) findViewById(R.id.more_txtName);
        text_total_spaces  = (TextView) findViewById(R.id.more_txtTotal);
        text_spaces_left = (TextView) findViewById(R.id.more_txtSpacesLeft);
        text_occupied_spaces = (TextView) findViewById(R.id.more_occupiedSpaces);
        text_percentage = (TextView) findViewById(R.id.more_perleft);
        text_time = (TextView) findViewById(R.id.more_time);
        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.more_map)).getMap();
        map.addMarker(new MarkerOptions().position(PARKING_LOCATION).title(parkingData.GetName()));
        CameraUpdate map_update = CameraUpdateFactory.newLatLngZoom(PARKING_LOCATION, 15);
        map.animateCamera(map_update);
        addData();
    }
    @Override
    public void onBackPressed()
    {
        ParkingDataHold.setOtherActivityActive(false);
        super.onBackPressed();  // optional depending on your needs
    }
    private void addData()
    {
      try{
      if(parkingData.getDistance() == null)
      {
          text_distance.setText("Distance: null");
          text_time.setText("Time: null");
      }
        else {
          Distance distance =parkingData.getDistance();
          text_distance.setText("Distance: " + distance.getDistance());
          text_time.setText("Time: "+ distance.getTime());
      }
        text_name.setText("Name: "+parkingData.GetName());
        text_total_spaces.setText("Total: "+String.valueOf(parkingData.GetTotalCapacity()));
        text_spaces_left.setText("Spaces Available :" + " " + (String.valueOf(parkingData.GetTotalCapacity() - parkingData.GetOccupiedSpaces())));
        text_occupied_spaces.setText("Occupied: "+String.valueOf(parkingData.GetOccupiedSpaces()));
        text_percentage.setText("Percentage: "+String.valueOf(parkingData.getSpacesPercentage()) + "%");
    }
      catch (Exception e){}
    }

}