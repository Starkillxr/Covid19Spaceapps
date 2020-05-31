package com.example.purifytheairsupply.external;

public class URLBuilder {
    private String sensor_api = "http://dustmo.com/api/sensors"; //air quality data URL

    public String newURL(int reason, String inputA, String inputB){
        StringBuilder builtURL = new StringBuilder();
        if(reason == 1){
            builtURL.append(sensor_api);
        }

        return builtURL.toString();
    }

}
