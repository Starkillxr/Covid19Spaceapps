package com.example.purifytheairsupply.Settings;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.purifytheairsupply.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


// Idea behind the settings is to give the user as much customisability as possible. allowing
// them to be able to have regular updates on each or for updates when each variable goes above
// or below a certain limit, an idea might be that we can have it so they can have either above
// or below, or both.
    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            SwitchPreferenceCompat switchPreferenceCompat = findPreference("humidity");
            final SwitchPreferenceCompat aSwitch = findPreference("humiditySpecific");
            final ListPreference minHum = findPreference("PREF_MIN_HUM");
            final ListPreference maxHum = findPreference("PREF_MAX_HUM");
            final ListPreference humFrq = findPreference("humFreq");

            switchPreferenceCompat.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(newValue.equals(true)){
                        aSwitch.setVisible(true);
                        humFrq.setVisible(true);
                    }
                    else if(newValue.equals(false))
                    {
                        aSwitch.setVisible(false);
                        humFrq.setVisible(false);
                    }
                    return true;
                }
            });

            aSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(newValue.equals(true)){
                        maxHum.setVisible(true);
                        minHum.setVisible(true);
                        humFrq.setVisible(false);
                    }
                    else if(newValue.equals(false)){
                        maxHum.setVisible(false);
                        minHum.setVisible(false);
                        humFrq.setVisible(true);
                    }
                    return true;
                }
            });

            SwitchPreferenceCompat tempSwitch = findPreference("temp");
            final SwitchPreferenceCompat tempSwitch2 = findPreference("tempSpecific");
            final ListPreference minTemp = findPreference("PREF_MIN_TEMP");
            final ListPreference maxTemp = findPreference("PREF_MAX_TEMP)");
            final ListPreference tempFreq = findPreference("tempFreq");

            tempSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (newValue.equals(true)) {
                        tempSwitch2.setVisible(true);
                        tempFreq.setVisible(true);
                    }
                    else if(newValue.equals(false)){
                        tempSwitch2.setVisible(false);
                        tempFreq.setVisible(false);
                    }
                    return true;
                }
            });

            tempSwitch2.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (newValue.equals(true)){
                        minTemp.setVisible(true);
                        maxTemp.setVisible(true);
                        tempFreq.setVisible(false);
                    }else if(newValue.equals(false)){
                        maxTemp.setVisible(false);
                        minTemp.setVisible(false);
                        tempFreq.setVisible(true);
                    }
                    return true;
                }
            });




        }
    }
}