package org.me.myandroidstuff;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Created by Sylwester Zalewski  on 17/03/2015.
 * App Name: Glasgow Parking
 * Student ID: S1434548
 * Glasgow Caledonian University
 */
public class Distance {
    private final String API_KEY = "AIzaSyAXr_PuaAgEldtYRZzz6GWQBS7eb7uUj4w";
    private String Distance;
    private String Destination;
    private String Origin = "";
    private String Unit;
    private String Mode;
    private Boolean Driving;
    private Boolean Walking;
    private String Avoid;
    private String Steps;


    private double TimeValue;
    private double DistanceValue;
    private String Time;
    private String URL;
    private Boolean GPS;
    private String[] Tag;
    private double Lat;

    private double Long;

    public Distance() {
        Destination = "";
        Origin = "";
        Unit = "";
        Mode = "driving";
        Driving = true;
        Walking = false;
        Lat =-1;
        Long =-1;
        Avoid ="";
        Steps = "";
        Time="";
        URL="";
        GPS = false;
        Distance = "";
        DistanceValue = -1;
        TimeValue = -1;
        AddTags();
    }

    public void generateURL() {
        /**
         * https://maps.googleapis.com/maps/api/directions/xml?origin=g200lf=gb&destination=55.85988984843241,-4.282341367108816=gb &avoid=ferries&mode=driving&key=AIzaSyAXr_PuaAgEldtYRZzz6GWQBS7eb7uUj4w
         */
        URL = "https://maps.googleapis.com/maps/api/directions/xml?origin=";
        if(!GPS)
        {
            URL+=Origin + "=gb";
        }
        else
        {
            URL+=Origin;
        }
        URL+="&destination="+Destination;
        if(Walking) {
          Mode = "walking&";
        }
        else
        {
            URL +="&avoid=" + Avoid;
            Mode ="driving&";
        }
        URL+="&mode=" + Mode;
        URL+="key="+API_KEY;

        URL = URL.replace(" ","");
        //&mode=driving&ke
    }
    public void ParseDistance() {
        String data;
        try {
            ParseData parseData = new ParseData();
            data = parseData.download(URL);


            Document doc = parseData.getDocElement(data); // getting DOM element
            final String KEY_SITUATION = "distance";//???????????
            NodeList nl = doc.getElementsByTagName(KEY_SITUATION);

            for (int i = 0; i < nl.getLength(); i++) {
                Element e = (Element) nl.item(i);


                setDistanceValue((parseData.getValue(e, Tag[0])));
                Distance = parseData.getValue(e, Tag[1]);
            }
             doc = parseData.getDocElement(data); // getting DOM element
            final String KEY_SITUATION1 = "duration";//???????????
             nl = doc.getElementsByTagName(KEY_SITUATION1);

            for (int i = 0; i < nl.getLength(); i++) {
                Element e = (Element) nl.item(i);

                setTimeValue(parseData.getValue(e, Tag[0]));
                Time = parseData.getValue(e, Tag[1]);
            }
            doc = parseData.getDocElement(data); // getting DOM element
            final String KEY_SITUATION2 = "end_location";//???????????
            nl = doc.getElementsByTagName(KEY_SITUATION2);

            for (int i = 0; i < nl.getLength(); i++) {
                Element e = (Element) nl.item(i);

                setLat(parseData.getValue(e, Tag[4]));
                setLong(parseData.getValue(e, Tag[5]));
            }

        } catch (Exception e) {
        }
    }


    public Boolean getDriving() {
        return Driving;
    }

    public void setDriving(Boolean driving) {
        Driving = driving;
    }

    public Boolean getWalking() {
        return Walking;
    }

    public void setWalking(Boolean walking) {
        Walking = walking;
    }

    public String getURL() {generateURL();
        return URL;
    }

    public double getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        try {
            Lat= Double.parseDouble(aLong);
        }
        catch (Exception e){}
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        try {
          Lat= Double.parseDouble(lat);
        }
        catch (Exception e){}
    }

    public String getDistance() {
        return Distance;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Boolean getGPS() {
        return GPS;
    }

    public void setGPS(Boolean GPS) {
        this.GPS = GPS;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getTime() {
        return Time;
    }

    public String getSteps() {
        return Steps;
    }

    public void setSteps(String steps) {
        Steps = steps;
    }

    public String getAvoid() {
        return Avoid;
    }

    public void setAvoid(String avoid) {
        Avoid = avoid;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public void setDistanceValue(String distanceValue) {
        DistanceValue =Double.parseDouble(distanceValue);
    }

    public void setTimeValue(String timeValue) {
        TimeValue =Double.parseDouble(timeValue);
    }


    public double getTimeValue() {
        return TimeValue;
    }

    public double getDistanceValue() {
        return DistanceValue;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }
    private void AddTags()
    {

        Tag = new String[10];
        //////////////////////////////////////
        this.Tag[0] = "value";
        this.Tag[1] = "text";
        this.Tag[2] = "duration";
       this.Tag[3] = "distance";
       this.Tag[4] = "lat";
        this.Tag[5] = "lng";
//        this.Tag[6] = "";
//        this.Tag[7] = "";
//        this.Tag[8] = "";
//        this.Tag[9] = "";
    }

}
