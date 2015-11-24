package org.me.myandroidstuff;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Sylwester Zalewski  on 17/03/2015.
 * App Name: Glasgow Parking
 * Student ID: S1434548
 * Glasgow Caledonian University
 */

public class MainActivity extends FragmentActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout mDrawerRelativeLayout;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mTitles;
    private boolean autoRefresh = true;
    private TextView response;
    private TextView errorText;
    private String result;
    private String sourceListingURL = "http://open.glasgow.gov.uk/api/live/parking.php?type=xml";
    private List<ParkingData> ParkData;
    private List<Distance> DistanceData;
    private ProgressBar PBar;
    private static String TimeRem;
    private TextView ParkingProcen;
    private String Destination;
    private static boolean CanRefresh = true;
    public static final String PREFS_NAME = "MyPrefsFile";
    private Boolean SortByTotalASC;
    private Boolean SortByTotalDESC;
    private Boolean SortByPercentageASC;
    private Boolean SortByPercentageDESC;
    private Boolean SortByDistanceASC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//////////////////////////////////////////
        readData();
        //updateData();
        new RefreshTask().execute();
        DistanceData = null;
        //////////////////////////////////
        mDrawerRelativeLayout = (RelativeLayout) findViewById(R.id.left_drawerR);
        mTitle = mDrawerTitle = getTitle();
        mTitles = getResources().getStringArray(R.array.menu_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                mDrawerList.bringToFront();
                mDrawerLayout.requestLayout();
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            //selectItem(0);
        }
    }
private void readData()
{
    try {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SortByTotalDESC = SP.getBoolean("sortByTotalDESC", false);
        SortByTotalASC = SP.getBoolean("sortByTotalASC", false);
        SortByPercentageASC = SP.getBoolean("sortByPercentageASC", false);
        SortByPercentageDESC = SP.getBoolean("sortByPercentageDESC", false);
        SortByDistanceASC = false;
    }
    catch (Exception e)
    {
        SortByTotalDESC = false;
        SortByTotalASC = false;
        SortByPercentageASC = false;
        SortByPercentageDESC = false;
        SortByDistanceASC = false;
    }
}


    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerRelativeLayout);
        return super.onPrepareOptionsMenu(menu);
    }
    private class RefreshTask extends AsyncTask<String, Void, List<ParkingData>> {
        List<ParkingData>  parkingData;

        protected List<ParkingData> doInBackground(String... urls) {
            ParseData g = new ParseData();
            // Get the data from the RSS stream as a string
            g.ParseData();
            return g.GetData();
        }
        protected void onPostExecute(List<ParkingData> results) {
if(!ParkingDataHold.getOtherActivityActive()) {
    if (!results.isEmpty()) {
        ParkData = results;
        if (DistanceData != null) {
            addDistanceToDataList();
        }
        sortBeforeDisplay();
        populateListViewPort();
        registerClickCallback();
        Toast.makeText(MainActivity.this, "Loading Successful!", Toast.LENGTH_LONG).show();
        if (autoRefresh) {
            CountDownTimer timer = new CountDownTimer(65000, 1000) {
                public void onTick(long millisUntilFinished) {
                    MainActivity.setTimeRem("Time remaining: " + millisUntilFinished / 1000 + " seconds");
                    // Toast.makeText(MainActivity.this, TimeRem, Toast.LENGTH_SHORT).show();
                }

                public void onFinish() {
                    MainActivity.setCanRefresh(true);
                    RefreshElements();
                }
            }.start();
        }
    }
}
        }
        }
    public void RefreshElements()
    {
    new RefreshTask().execute();
    }
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        autoRefresh = false;
    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        autoRefresh = true;
        RefreshElements();
    }
    private void addDistanceToDataList()
    {
       ParkingData parkingData;
        for(int i = 0; i < ParkData.size();i++) {
            parkingData = ParkData.get(i);
            parkingData.setDistance(DistanceData.get(i));
            ParkData.set(i, parkingData);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void sortBeforeDisplay() {
        if (SortByTotalASC) {
            ParkData = ParkingData.SortByTotalSpacesASC(ParkData);
        } else if (SortByTotalDESC) {
            ParkData = ParkingData.SortByTotalSpacesDESC(ParkData);
        } else if (SortByPercentageASC) {
            ParkData = ParkingData.SortByTotalPercentageASC(ParkData);
        } else if (SortByPercentageDESC) {
            ParkData = ParkingData.SortByTotalPercentageDESC(ParkData);
        }     else if (SortByDistanceASC) {
        ParkData = ParkingData.SortByDistanceASC(ParkData);

    }}

    private void selectItem(int position) {
        // update the main content by calling populateListView and Sort method
        Context context = MainActivity.this;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("sortByTotalDESC", false);
        editor.putBoolean("sortByTotalASC", false);
        editor.putBoolean("sortByPercentageDESC", false);
        editor.putBoolean("sortByPercentageASC", false);


        SortByTotalDESC = false;
        SortByTotalASC = false;
        SortByPercentageASC = false;
        SortByPercentageDESC = false;
        SortByDistanceASC =false;

        switch (position) {
            case 0: {
                SortByTotalASC = true;
                editor.putBoolean("sortByTotalASC", true);
                sortBeforeDisplay();
                populateListViewPort();
                registerClickCallback();
                mDrawerLayout.closeDrawers();
                break;
            }
            case 1: {
                SortByTotalDESC = true;
                editor.putBoolean("sortByTotalDESC", true);
                sortBeforeDisplay();
                populateListViewPort();
                registerClickCallback();
                mDrawerLayout.closeDrawers();
                break;
            }
            case 2: {
                editor.putBoolean("sortByPercentageASC", true);
                SortByPercentageASC = true;
                sortBeforeDisplay();
                populateListViewPort();
                registerClickCallback();
                mDrawerLayout.closeDrawers();
                break;
            }
            case 3: {

                SortByPercentageDESC = true;
                editor.putBoolean("sortByPercentageDESC", true);
                sortBeforeDisplay();
                populateListViewPort();
                registerClickCallback();
                mDrawerLayout.closeDrawers();
                break;
            }
            case 4: {
//                DistanceToDialogFragment distanceToDialogFragment = new DistanceToDialogFragment();
//                distanceToDialogFragment.show(getSupportFragmentManager(), "");
              //  Toast.makeText(MainActivity.this, ParkingDataHold.getDestination(), Toast.LENGTH_LONG).show();
                //String res = distanceToDialogFragment.
                //final EditText editText  = (EditText)  findViewById(R.id.dialog_address);
                mDrawerLayout.closeDrawers();
                LayoutInflater linf = LayoutInflater.from(this);
                final View inflator = linf.inflate(R.layout.distance_to_dialog, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                final EditText et1 = (EditText) inflator.findViewById(R.id.dialog_address);
              //  final EditText et2 = (EditText) inflator.findViewById(R.id.editText2);
                alert.setMessage("Please Enter You Target");
                alert.setTitle("Walking Distance from...");
                alert.setView(inflator);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {


                       // Toast.makeText(MainActivity.this, et1.getText().toString(), Toast.LENGTH_LONG).show();
                        Destination = et1.getText().toString();
                        dialog.dismiss();

//                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(et1.getWindowToken(), 0);
                       new DistanceTask().execute();

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

                alert.show();
                break;
            }
        }

        editor.commit();

    }
    private class DistanceTask extends AsyncTask<String, Integer, String> {
        ProgressDialog progress = new ProgressDialog(MainActivity.this);
        protected String doInBackground(String... urls) {
            DistanceData = new ArrayList<Distance>();
            for (int i = 0; i < ParkData.size(); i++) {
                ParkingData parkingData = ParkData.get(i);
                Distance distance = new Distance();
                distance.setGPS(false);
                distance.setDestination(parkingData.GetLatitude() + "," + parkingData.GetLongitude());
                distance.setAvoid("");
                distance.setOrigin(Destination);
                distance.setWalking(true);
                distance.generateURL();
                distance.ParseDistance();
                parkingData.setDistance(distance);
                DistanceData.add(distance);
                ParkData.set(i, parkingData);
                publishProgress(i);
            }
            return "";
        }
        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(values[0]);
        }
        protected void onPostExecute(String results) {
            try {
                if (progress.isShowing()) {
                    // To dismiss the dialog
                    progress.dismiss();

                }
                SortByDistanceASC = true;
                sortBeforeDisplay();
                populateListViewPort();
                registerClickCallback();
                ParkingDataHold.setOtherActivityActive(false);
            }
            catch (Exception e){}
        }

        @Override
        protected void onPreExecute() {
            progress.setTitle("Loading");
            progress.setMessage("Please Wait..");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setMax(ParkData.size());
            progress.show();
            ParkingDataHold.setOtherActivityActive(false);
        }

    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.parkingListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

//				ParkingData data = ParkData.get(position);
				//String message = "Parking Name is: " + String.valueOf(position);
				//Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                ParkingDataHold.setParkingData(ParkData.get(position));
                Intent intent = new Intent(getApplicationContext(), More_info.class);

               // autoRefresh = false;
                ParkingDataHold.setOtherActivityActive(true);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this,
                    SettingsActivity.class);
            startActivity(intent);
            // break;
        } else if (id == R.id.refresh) {
            RefreshTask refreshTask = new RefreshTask();
            refreshTask.execute();

        } else if (id == R.id.Help) {
            Intent intent = new Intent(getApplicationContext(), help.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



    private void populateListViewPort() {
        ArrayAdapter<ParkingData> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.parkingListView);

        list.setAdapter(adapter);
    }



    public static String getTimeRem() {
        return TimeRem;
    }

    public static void setTimeRem(String timeRem) {
        TimeRem = timeRem;
    }

    public static void setCanRefresh(boolean canRefresh) {
        CanRefresh = canRefresh;
    }



    private class MyListAdapter extends ArrayAdapter<ParkingData> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_view, ParkData);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View Itemview = convertView;
            if (Itemview == null) {
                Itemview = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            ParkingData data = ParkData.get(position);

            ImageView img = (ImageView) Itemview.findViewById(R.id.item_image);
            img.setImageResource(data.getIconID());
            TextView name = (TextView) Itemview.findViewById(R.id.item_txtName);
            name.setText(data.GetName());
            TextView total = (TextView) Itemview.findViewById(R.id.item_txtTotal);
            total.setText("Total:" + " " + String.valueOf(data.GetTotalCapacity()));
            TextView occu = (TextView) Itemview.findViewById(R.id.item_occupiedSpaces);
            occu.setText("Occupied: " + " " + String.valueOf(data.GetOccupiedSpaces()));
            TextView left = (TextView) Itemview.findViewById(R.id.item_txtSpacesLeft);
            left.setText("Spaces Available :" + " " + (String.valueOf(data.GetTotalCapacity() - data.GetOccupiedSpaces())));
            return Itemview;
        }

    }




}