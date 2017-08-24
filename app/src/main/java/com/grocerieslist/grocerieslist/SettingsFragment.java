package com.grocerieslist.grocerieslist;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.view.Menu;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by vwillot on 8/10/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements OnSharedPreferenceChangeListener{


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_fragment);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();

        for(int index = 0; index <count; index++){
            Preference preference = preferenceScreen.getPreference(index);
            String value = sharedPreferences.getString(preference.getKey(),"");
            if(preference instanceof EditTextPreference || preference instanceof ListPreference){
                preference.setSummary(value);
            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference preference = findPreference(key);
        if(preference != null){
            String value = sharedPreferences.getString(preference.getKey(), "");
            preference.setSummary(value
            );
        }
    }
}
