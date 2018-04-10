
package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by rajas on 3/29/2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);
        SharedPreferences sharedPreferences=getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen=getPreferenceScreen();
        int count=preferenceScreen.getPreferenceCount();
        for(int i=0;i<count;i++){
            Preference p= preferenceScreen.getPreference(i);
            if(!(p instanceof CheckBoxPreference)){
                String value=sharedPreferences.getString(p.getKey(),"");
                setPreferenceSummary(p, value);
            }
        }
    }

    public void setPreferenceSummary(Preference pref, Object value){
        String stringValue=value.toString();
        String key=pref.getKey();

        if(pref instanceof ListPreference){
            ListPreference listPreference= (ListPreference) pref;
            int prefIndex=listPreference.findIndexOfValue(stringValue);
            if(prefIndex>=0){
                pref.setSummary(listPreference.getEntries()[prefIndex]);
            }
            else{
                pref.setSummary(stringValue);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference=findPreference(key);
        if(null!=preference){
            if(!(preference instanceof CheckBoxPreference)){
                setPreferenceSummary(preference, sharedPreferences.getString(key,""));
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
