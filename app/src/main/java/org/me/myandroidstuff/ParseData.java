package org.me.myandroidstuff;

/**
 * Created by Sylwester Zalewski  on 17/03/2015.
 * App Name: Glasgow Parking
 * Student ID: S1434548
 * Glasgow Caledonian University
 */
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ParseData {
    private List<ParkingData> Data;
    public String Msg;

    private String RawData;
    private final String[] Tag;
    private double Progress;



    ////////////////////////////////////////
    public ParseData()
    {
        //Constructor
        Data = new ArrayList<ParkingData>();
        Tag = new String[10];
        this.AddTags();
    }
    ///getters
    public String GetRawData(){return this.RawData;}
    public List<ParkingData> GetData(){return this.Data;}


    //Method to handle the reading of the data from the XML stream
public String download(String url)
{
    String result = "";
    InputStream anInStream = null;
    int response = -1;
    try {
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return XML
        return result;

    } catch (Exception e) {
    }
    return result;
}


    public boolean ParseData()
    {
        try{
            RawData = download("http://open.glasgow.gov.uk/api/live/parking.php?type=xml");
/////////////////////////////////////////////////////////
            Document doc = getDocElement(RawData); // getting DOM element
            final String KEY_SITUATION ="situation";
            NodeList nl = doc.getElementsByTagName(KEY_SITUATION);
            ParkingData tmp;
            // looping through all item nodes <item>
            for (int i = 0; i < nl.getLength(); i++) {
              tmp = new ParkingData();
                Element e = (Element) nl.item(i);
                tmp.SetConfidentiality(getValue(e, Tag[0]));
                tmp.SetInformationStatus(getValue(e, Tag[1]));
                tmp.setValidityStatus(getValue(e, Tag[2]));
                tmp.SetLatitude(getValue(e, Tag[3]));
                tmp.SetLongitude(getValue(e, Tag[4]));
                tmp.SetName(getValue(e, Tag[5]));
                tmp.setCarParkOccupancy(getValue(e, Tag[6]));
                tmp.setCarParkStatus(getValue(e, Tag[7]));
                tmp.SetOccupiedSpaces(getValue(e, Tag[8]));
                tmp.SetTotalCapacity(getValue(e, Tag[9]));
                tmp.setImage();
                Data.add(tmp);//add tmp to List
            }

////////////////////////////////////////////////////////////////////
        }
        catch(Exception e){}
        return true;
    }

    public Document getDocElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }

        return doc;
    }
    public final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }
    private void AddTags()
    {


        //////////////////////////////////////
        this.Tag[0] = "confidentiality";
        this.Tag[1] = "informationStatus";
        this.Tag[2] = "validityStatus";
        this.Tag[3] = "latitude";
        this.Tag[4] = "longitude";
        this.Tag[5] = "carParkIdentity";
        this.Tag[6] = "carParkOccupancy";
        this.Tag[7] = "carParkStatus";
        this.Tag[8] = "occupiedSpaces";
        this.Tag[9] = "totalCapacity";
    }}
