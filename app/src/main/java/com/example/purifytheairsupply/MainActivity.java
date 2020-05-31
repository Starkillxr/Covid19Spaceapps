package com.example.purifytheairsupply;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.example.purifytheairsupply.Settings.SettingsActivity;
import com.example.purifytheairsupply.account.AccountActivity;
import com.example.purifytheairsupply.airquality.AirQualityData;

public class MainActivity extends AppCompatActivity {

    Button airQuality, settings, account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        airQuality = findViewById(R.id.air_quality_button);
        settings = findViewById(R.id.settings_button);
        account = findViewById(R.id.account_button);

        airQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),AirQualityData.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(i);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AccountActivity.class);
                startActivity(i);
            }
        });
    }
}
