package org.me.myandroidstuff.tests;

import android.test.ActivityTestCase;

import junit.framework.Assert;

import org.me.myandroidstuff.ParkingData;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Sylwester Zalewski  on 17/03/2015.
 * App Name: Glasgow Parking
 * Student ID: S1434548
 * Glasgow Caledonian University
 */
public class testParkingData extends ActivityTestCase {
    List<ParkingData> temp;

    public void testSortByTotalSpacesDESC() {
        try {
            populateDataList();
            List<ParkingData> result = ParkingData.SortByTotalSpacesDESC(temp);
            int[] expected = {1500, 1200, 500, 120};
            for (int i = 0; i < temp.size(); i++) {
                ParkingData data = result.get(i);
                Assert.assertEquals(expected[i], data.GetTotalCapacity());
            }
        } catch (Exception e) {
        }
    }
   
    public void testSortByPerASC() {
        try {
            populateDataList();
            List<ParkingData> result = ParkingData.SortByTotalPercentageASC(temp);
            double[] expected = {Math.round((100 - (1/120*100))),40,75, 80 };
            for (int i = 0; i < temp.size(); i++) {
                ParkingData data = result.get(i);
                Assert.assertEquals(i,expected[i], data.getSpacesPercentageDouble());
            }
        } catch (Exception e) {
        }
    }
    public void testSortByPerDESC() {
        try {
            populateDataList();
            List<ParkingData> result = ParkingData.SortByTotalPercentageDESC(temp);
            double[] expected = { 80,75,40, Math.round((100 - (1/120*100)))};
            for (int i = 0; i < temp.size(); i++) {
                ParkingData data = result.get(i);
                Assert.assertEquals(i,expected[i], data.getSpacesPercentageDouble());
            }
        } catch (Exception e) {
        }
    }
    public void testSortByTotalSpacesASC() {
        try {
            populateDataList();
            List<ParkingData> result = ParkingData.SortByTotalSpacesASC(temp);
            int[] expected = {120, 500, 1200, 1500};
            for (int i = 0; i < temp.size(); i++) {
                ParkingData data = result.get(i);
                Assert.assertEquals(expected[i], data.GetTotalCapacity());
            }

        } catch (Exception e) {
        }
    }
    public void testSortByNameASC() {
        try {
            populateDataList();
            List<ParkingData> result = ParkingData.SortByNameASC(temp);
            String[] expected = {"Caledonian","Hope Street","SECC","St. Enoch"};
            for (int i = 0; i < temp.size(); i++) {
                ParkingData data = result.get(i);
                Assert.assertEquals(expected[i], data.GetName());
            }

        } catch (Exception e) {
        }
    }
    public void testSortByNameDESC() {
        try {
            populateDataList();
            List<ParkingData> result = ParkingData.SortByNameDESC(temp);
            String[] expected = {"St. Enoch","SECC","Hope Street","Caledonian"};
            for (int i = 0; i < temp.size(); i++) {
                ParkingData data = result.get(i);
                Assert.assertEquals(expected[i], data.GetName());
            }

        } catch (Exception e) {
        }
    }
    public void testSplitName(){
        populateDataList();
        String[] expected1 = {"1","2","3","4"};
        String[] expected2 = {"SECC","Hope Street","Caledonian","St. Enoch"};
        String[] Inputs ={"SECC:1","Hope Street:2","Caledonian:3","St. Enoch:4"};
        for (int i = 0; i < temp.size(); i++) {
            ParkingData data = new ParkingData();
            data.SetName(Inputs[i]);
            Assert.assertEquals(expected1[i], data.GetParkingID());
            Assert.assertEquals(expected2[i], data.GetName());
        }
    }

    private void populateDataList()
    {
        temp = new ArrayList<>();
        ParkingData data = new ParkingData();
        data.SetName("SECC:1");
        data.SetTotalCapacity(1200);
        data.SetOccupiedSpaces(300);

        temp.add(data);
        //////////
        data = new ParkingData();
        data.SetName("Hope Street:6");
        data.SetTotalCapacity(500);
        data.SetOccupiedSpaces(100);

        temp.add(data);
        //////////
        data = new ParkingData();
        data.SetName("Caledonian:1");
        data.SetTotalCapacity(1500);
        data.SetOccupiedSpaces(900);

        temp.add(data);
        //////////
        data = new ParkingData();
        data.SetName("St. Enoch:4");
        data.SetTotalCapacity(120);
        data.SetOccupiedSpaces(1);

        temp.add(data);
    }
}
