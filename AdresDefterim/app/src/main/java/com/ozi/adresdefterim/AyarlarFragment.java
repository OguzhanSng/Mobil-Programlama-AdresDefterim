package com.ozi.adresdefterim;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class AyarlarFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.ayarlar);
    }
}
