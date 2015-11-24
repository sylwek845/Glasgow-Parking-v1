package org.me.myandroidstuff;

/**
 * Created by Sylwester Zalewski  on 17/03/2015.
 * App Name: Glasgow Parking
 * Student ID: S1434548
 * Glasgow Caledonian University
 */
public class ParkingDataHold {
    private static ParkingData park;

    public static String getDestination() {
        return destination;
    }


    public static Boolean getOtherActivityActive() {
        return OtherActivityActive;
    }

    public static void setOtherActivityActive(Boolean otherActivityActive) {
        OtherActivityActive = otherActivityActive;
    }

    private static Boolean OtherActivityActive = false;
    public static void setDestination(String destination) {
        ParkingDataHold.destination = destination;
    }

    private static String destination;
    public static void setParkingData(ParkingData Data){park = Data;}
    public static ParkingData getParkingData(){return park;}
}
