package org.me.myandroidstuff.tests;

import android.test.ActivityTestCase;

import org.junit.Assert;
import org.me.myandroidstuff.ParkingData;
import org.me.myandroidstuff.ParseData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

/**
 * Created by Sylwester Zalewski  on 17/03/2015.
 * App Name: Glasgow Parking
 * Student ID: S1434548
 * Glasgow Caledonian University
 */
public class testParseData extends ActivityTestCase {

    private ParkingData parkingData;
    public testParseData(){}


    public void testDownloadData()
    {
        try {
            ParseData parseData = new ParseData();
            String actual = parseData.download("https://maps.googleapis.com/maps/api/directions/xml?originbath+street+glasgow&destination=55.85977254007017,-4.239331652385187&mode=walking&key=AIzaSyAXr_PuaAgEldtYRZzz6GWQBS7eb7uUj4w");
            String file = "res/raw/test.xml";
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);
            String line = getStringFromInputStream(in);
//            line = line.replaceAll(" ","");
//            line = line.replaceAll("\\r|\\n", "");
            int test1 = line.length();
            //"\n", ""
//            actual = actual.replaceAll(" ","");
//            actual = actual.replaceAll("\\r|\\n", "");
            int test2 = line.length();
            Assert.assertEquals("Download Test- ",test1,test2);
        }
        catch (Exception e){Assert.fail(e.getMessage());}
    }

    public static String getStringFromInputStream(InputStream stream) throws IOException
    {
        int n = 0;
        char[] buffer = new char[1024 * 4];
        InputStreamReader reader = new InputStreamReader(stream, "UTF8");
        StringWriter writer = new StringWriter();
        while (-1 != (n = reader.read(buffer))) writer.write(buffer, 0, n);
        return writer.toString();
    }
}
