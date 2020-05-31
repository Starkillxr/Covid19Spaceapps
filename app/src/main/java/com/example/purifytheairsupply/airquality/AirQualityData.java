package com.example.purifytheairsupply.airquality;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.purifytheairsupply.R;
import com.example.purifytheairsupply.external.URLBuilder;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AirQualityData extends AppCompatActivity {
    ConstraintLayout loadingView, dataView, errorView;
    TextView temperature, humidity, airPressure, altitude,co2,dust;
    Button refreshButton;
    LineChart tempGraph, humidityGraph, airGraph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_quality_data);
        loadingView = findViewById(R.id.airqualityloading);
        dataView = findViewById(R.id.airqualdisplay);
        errorView = findViewById(R.id.air_quality_error);
        temperature = findViewById(R.id.sensor_temp_output);
        humidity = findViewById(R.id.sensor_humidity_output);
        airPressure = findViewById(R.id.sensor_pressure_output);
        altitude = findViewById(R.id.sensor_altitude_output);
        refreshButton=findViewById(R.id.sensor_refresh);
        co2 = findViewById(R.id.sensor_co2_output);
        dust = findViewById(R.id.dustOutput);
        sensorDataRequest(); //sensor data request
        refreshButton.setOnClickListener(new View.OnClickListener() {//sets on click listener for the refresh button
            @Override
            public void onClick(View v) {
                loadingView.setVisibility(View.VISIBLE);//when refresh is pressed, the loading bar is made visible
                dataView.setVisibility(View.GONE);      //the data is hidden if displayed
                errorView.setVisibility(View.GONE);     //the error is hidden if displayed
                sensorDataRequest(); //requests new sensor data
            }
        });

    }

    private void sensorDataRequest() {
        final URLBuilder buildURL = new URLBuilder();//constructs new URLBuilder variable
        String URLstring = buildURL.newURL(1, null, null);//requests url from URLBuilder with
        // reason 3, inputs set to null
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring, //StringRequest requests new data from the constructed URL
                new com.android.volley.Response.Listener<String>() {//response listener waits for reply from server
                    @Override
                    public void onResponse(String response) {//if response is received store in String
                        try {
                            int i;//variable used for counting array size
                            int arrayLength = 0;//used for storing array size
                            JSONObject obj = new JSONObject(response);//creates JSONObject from String "response"
                            JSONArray sensorObj = obj.getJSONArray("employees"); //JSON Arrays need to be extracted to JSONArray
                            for (i = 0; i < sensorObj.length(); i++) {//finds size of JSONArray
                                arrayLength = i - 1;//i-1 = final position (-1 for 0 counting) used to find latest update from sensor
                            }
                            JSONObject sensorArr = sensorObj.getJSONObject(arrayLength); //Converts the JSONArray index equal to arrayLength variable to JSONObject
                            temperature.setText(new StringBuilder().append(sensorArr.getString("temperature")).append("\u2103").toString());//sets the temperature output from JSON and appends with degrees C symbol
                            humidity.setText(new StringBuilder().append(sensorArr.getString("humidity")).append("%").toString());//sets the humidity output from JSON and appends with % symbol
                            airPressure.setText(new StringBuilder().append(sensorArr.getString("pressure")).append("hPa").toString());//sets the air pressure output from JSON and appends with hPa to indicate units
                            altitude.setText(new StringBuilder().append(sensorArr.getString("altitude")).append("m").toString());//sets the altitude output from JSON and appends with m to indicate units

                            if(sensorArr.getString("c02").equals("n/a"))
                            {
                                co2.setText("0ppm"); //means there is no carbon dioxide or it is
                                // not applicable.
                            }else {
                                co2.setText(new StringBuilder().append(sensorArr.getString("c02")).append("ppm").toString());
                            }

                            if (sensorArr.getString("dustDensity").equals("n/a")){
                                dust.setText("0ppm");
                            }else{
                                dust.setText(new StringBuilder().append(sensorArr.getString(
                                        "dustDensity")).append("ppm"));
                            }

                            loadingView.setVisibility(View.GONE);//loading is hidden
                            dataView.setVisibility(View.VISIBLE);//data is displayed
                            errorView.setVisibility(View.GONE);//error is hidden



                        } catch (JSONException e) {//catches error parsing JSON
                            e.printStackTrace();
                            loadingView.setVisibility(View.GONE);   //if error parsing JSON, loading is hidden
                            dataView.setVisibility(View.GONE);      //data is hidden
                            errorView.setVisibility(View.VISIBLE);  //error message is displayed
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {//listens for error in volley request
                        error.printStackTrace();
                        loadingView.setVisibility(View.GONE);   //if error retrieving JSON, loading is hidden
                        dataView.setVisibility(View.GONE);      //data is hidden
                        errorView.setVisibility(View.VISIBLE);  //error message is displayed
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this); //creates new volley request queue
        requestQueue.add(stringRequest);//adds new request to queue

    }
}

