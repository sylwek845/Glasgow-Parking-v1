package org.me.myandroidstuff.tests;

import android.test.ActivityTestCase;

import junit.framework.Assert;

import org.me.myandroidstuff.Distance;
/**
 * Created by Sylwester Zalewski  on 17/03/2015.
 * App Name: Glasgow Parking
 * Student ID: S1434548
 * Glasgow Caledonian University
 */
public class testDistance extends ActivityTestCase {
    String res1 = "https://maps.googleapis.com/maps/api/directions/xml?origin=Edinburgh=gb&destination=55.85988984843241,-4.282341367108816&avoid=ferries&mode=driving&key=AIzaSyAXr_PuaAgEldtYRZzz6GWQBS7eb7uUj4w";
    String res2 = "https://maps.googleapis.com/maps/api/directions/xml?origin=G208DL=gb&destination=55.85966175538049,-4.236528758151018&mode=walking&key=AIzaSyAXr_PuaAgEldtYRZzz6GWQBS7eb7uUj4w";
    String res3 = "https://maps.googleapis.com/maps/api/directions/xml?origin=Govan+Glasgow=gb&destination=55.869167760473324,-4.258813645522479&avoid=highways&mode=driving&key=AIzaSyAXr_PuaAgEldtYRZzz6GWQBS7eb7uUj4w";
    String res4 = "https://maps.googleapis.com/maps/api/directions/xml?origin=bath+street+glasgow=gb&destination=55.85977254007017,-4.239331652385187&mode=walking&key=AIzaSyAXr_PuaAgEldtYRZzz6GWQBS7eb7uUj4w";



    public void testGenerateURL() {

      //  Assert.fail("Not Implemented Test");

     //   String URL = "https://maps.googleapis.com/maps/api/directions/xml?origin=";
      //  String API_KEY = "AIzaSyAXr_PuaAgEldtYRZzz6GWQBS7eb7uUj4w";
        String[] destination ={"55.85988984843241,-4.282341367108816","55.85966175538049,-4.236528758151018","55.869167760473324,-4.258813645522479","55.85977254007017,-4.239331652385187"};
        String[] origin ={"Edinburgh","G208DL","Govan+Glasgow","bath+street+glasgow"};
        String[] avoid = {"ferries","ferries||highways","highways","ferries||highways||tolls"};
          String[] expected ={res1,res2,res3,res4};
        Distance distance ;

        for(int i = 0; i < destination.length;i++)
        {
            distance = new Distance();
            distance.setGPS(false);
            distance.setDestination(destination[i]);
            distance.setAvoid(avoid[i]);
            distance.setOrigin(origin[i]);
            if(i == 0 || i ==  2)
                 distance.setDriving(true);
            else
                 distance.setWalking(true);
            distance.generateURL();
         Assert.assertEquals(expected[i],distance.getURL());
        }


    }
    public void testParseDistanceData()
    {
        Distance distance = new Distance();
        String[] DistanceExp ={"76.7 km","5.7 km","6.4 km","1.8 km"};
        String[] TimeExp ={"1 hour 0 mins","1 hour 9 mins","13 mins","21 mins"};
        double[] DistanceValueExp = {76719,5676,6414,1811};
        double[] TimeValueExp = {3627,4139,802,1272};
        String[] urls ={res1,res2,res3,res4};

        for(int i = 0; i < 4;i++)
        {
            distance = new Distance();
            distance.setURL(urls[i]);

            distance.ParseDistance();

            Assert.assertEquals("Testing Distance " + i,DistanceExp[i],distance.getDistance());
            Assert.assertEquals("Testing Distance Values " + i,DistanceValueExp[i],distance.getDistanceValue());
            Assert.assertEquals("Testing Time " + i,TimeExp[i],distance.getTime());
            Assert.assertEquals("Testing Time Values " + i,TimeValueExp[i],distance.getTimeValue());
        }

    }

}