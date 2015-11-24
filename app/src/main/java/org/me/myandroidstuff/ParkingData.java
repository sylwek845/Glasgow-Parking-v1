package org.me.myandroidstuff;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Sylwester Zalewski  on 17/03/2015.
 * App Name: Glasgow Parking
 * Student ID: S1434548
 * Glasgow Caledonian University
 */
public class ParkingData {

    private String Name;
    private int TotalCapacity;
    private int OccupiedSpaces;
    private String ParkingStatus;
    private Distance distance;
    private float Latitude;
    private float Longitude;
    private String ParkingID;
    private String Confidentiality;
    private String InformationStatus;
    private String carParkStatus;
    private String validityStatus;
    private String carParkOccupancy;
    private int IconID;
    private double SpacesPercentage;
    public ParkingData()//constructor
    {
        Name = "";
        TotalCapacity = -1;
        OccupiedSpaces = -1;
        ParkingStatus = "";
        Latitude = -1;
        Longitude = -1;
        ParkingID = "";
        Confidentiality = "";
        InformationStatus = "";
        IconID = R.drawable.parking_35;
        this.SpacesPercentage = 0;
    }
    //Getters
    public String GetName (){return this.Name;}
    public int GetTotalCapacity (){return this.TotalCapacity;}
    public String GetParkingStatus (){return this.ParkingStatus;}
    public String GetParkingID (){return this.ParkingID;}
    public String GetInformationStatus (){return this.InformationStatus;}
    public String GetConfidentiality (){return this.Confidentiality;}
    public int GetOccupiedSpaces (){return this.OccupiedSpaces;}
    public float GetLatitude (){return this.Latitude;}
    public float GetLongitude (){return this.Longitude;}

    //Setters
    public void SetName (String Name){this.Name = Name; splitNameAndID();}
    public void SetTotalCapacity (int TotalCapacity){this.TotalCapacity = TotalCapacity;}
    public void SetParkingStatus (String ParkingStatus){this.ParkingStatus = ParkingStatus;}
    public void SetParkingID (String ParkingID){this.ParkingID = ParkingID;}
    public void SetInformationStatus (String InformationStatus){this.InformationStatus = InformationStatus;}
    public void SetConfidentiality (String Confidentiality){this.Confidentiality = Confidentiality;}
    public void SetOccupiedSpaces (int OccupiedSpaces){this.OccupiedSpaces = OccupiedSpaces;}
    public void SetLatitude (float Latitude){this.Latitude = Latitude;}
    public void SetLongitude (float Longitude){this.Longitude = Longitude;}
    public Distance getDistance() {
        return distance;
    }
    public void setDistance(Distance distance) {
        this.distance = distance;
    }
    public void SetLatitude (String Latitude){try{this.Latitude = Float.parseFloat(Latitude);}catch(Exception e){};}
    public void SetLongitude (String Longitude){try{this.Longitude = Float.parseFloat(Longitude);}catch(Exception e){};}
    public void SetOccupiedSpaces (String OccupiedSpaces){try{this.OccupiedSpaces = Integer.parseInt(OccupiedSpaces);}catch(Exception e){};}
    public void SetTotalCapacity (String TotalCapacity){try{this.TotalCapacity = Integer.parseInt(TotalCapacity);}catch(Exception e){};}
    public String getSpacesPercentage(){return String.valueOf(Math.round(100 - this.SpacesPercentage));}
    public double getSpacesPercentageDouble(){return Math.round(100 - this.SpacesPercentage);}
    public int getIconID() {
        return IconID;
    }

    public void setIconID(int iconID) {
        IconID = iconID;
    }
    /////////////////////////////Methods///////////////////////////////
    public void setImage() {

        SpacesPercentage = OccupiedSpaces / (double)TotalCapacity * 100 ;

        if(carParkStatus.endsWith("carParkFull"))
        {
            IconID = R.drawable.parking_full;
        }
        else if(carParkStatus.endsWith("enoughSpacesAvailable"))
        {

        if(this.SpacesPercentage >= 80)
        {
            IconID = R.drawable.parking_05;
        }
        else if(this.SpacesPercentage <= 80 && this.SpacesPercentage >= 55)
        {
            IconID = R.drawable.parking_15;
        }
        else if(this.SpacesPercentage <= 55 && this.SpacesPercentage >= 45)
        {
            IconID = R.drawable.parking_25;
        }
        else if(this.SpacesPercentage <= 45 && this.SpacesPercentage >= 35)
        {
            IconID = R.drawable.parking_35;
        }
        else if(this.SpacesPercentage <= 35 && this.SpacesPercentage >= 25)
        {
            IconID = R.drawable.parking_45;
        }
        else if(this.SpacesPercentage <= 25 && this.SpacesPercentage >= 15)
        {
            IconID = R.drawable.parking_55;
        }
        else if(this.SpacesPercentage <=  5)
        {
            IconID = R.drawable.parking_80;
        }
    }
        else
        {
            IconID = R.drawable.parking_closed;
        }
    }


    private void splitNameAndID() {
        try{
            String[] tmp = this.Name.split(":");
            this.Name = tmp[0];
            this.ParkingID = tmp[1];
        }
        catch(Exception e){}
    }

    public String getCarParkStatus() {
        return carParkStatus;
    }

    public void setCarParkStatus(String carParkStatus) {
        this.carParkStatus = carParkStatus;
    }

    public String getValidityStatus() {
        return validityStatus;
    }

    public void setValidityStatus(String validityStatus) {
        this.validityStatus = validityStatus;
    }

    public static List<ParkingData> SortByTotalSpacesASC(List<ParkingData> Data)
    {

            Collections.sort(Data, new Comparator<ParkingData>() {
                @Override
                public int compare(ParkingData p1, ParkingData p2) {
                    return p1.GetTotalCapacity() - p2.GetTotalCapacity(); // Ascending
                }

            });

        return Data;
    }
    public static List<ParkingData> SortByTotalSpacesDESC(List<ParkingData> Data)
    {


        Collections.sort(Data, new Comparator<ParkingData>() {
            @Override
            public int compare(ParkingData p1, ParkingData p2) {
                return p1.GetTotalCapacity() - p2.GetTotalCapacity(); // Ascending
            }

        });
        Collections.reverse(Data);
        return Data;
    }
    public static List<ParkingData> SortByNameASC(List<ParkingData> Data){return Data;}
    public static List<ParkingData> SortByNameDESC(List<ParkingData> Data){return Data;}
    public static List<ParkingData> SortByTotalPercentageDESC(List<ParkingData> Data)
    {


        Collections.sort(Data, new Comparator<ParkingData>() {
            @Override
            public int compare(ParkingData p1, ParkingData p2) {
                return (int)p1.getSpacesPercentageDouble() - (int)p2.getSpacesPercentageDouble(); // Ascending
            }

        });
        Collections.reverse(Data);
        return Data;
    }
    public static List<ParkingData> SortByTotalPercentageASC(List<ParkingData> Data)
    {


        Collections.sort(Data, new Comparator<ParkingData>() {
            @Override
            public int compare(ParkingData p1, ParkingData p2) {
                return (int)p1.getSpacesPercentageDouble() - (int)p2.getSpacesPercentageDouble(); // Ascending
            }

        });
        return Data;
    }
    public static List<ParkingData> SortByDistanceASC(List<ParkingData> Data)
    {


        Collections.sort(Data, new Comparator<ParkingData>() {
            @Override
            public int compare(ParkingData p1, ParkingData p2) {
                return (int)p1.getDistance().getDistanceValue() - (int)p2.getDistance().getDistanceValue(); // Ascending
            }

        });
        return Data;
    }
    public String getCarParkOccupancy() {
        return carParkOccupancy;
    }

    public void setCarParkOccupancy(String carParkOccupancy) {
        this.carParkOccupancy = carParkOccupancy;
    }
}
