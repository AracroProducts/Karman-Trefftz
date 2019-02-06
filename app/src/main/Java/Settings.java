package com.aracro.python.KarmanTrefftz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Settings extends AppCompatActivity {
    Switch switchToast;
    SharedPreferences.Editor editor = MainActivity.sharedpreferences.edit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.aracro.mg.python.KarmanTrefftz.R.layout.activity_settings);
        switchToast = findViewById(com.aracro.mg.python.KarmanTrefftz.R.id.switch1);
        switchToast.setChecked(MainActivity.sharedpreferences.getBoolean("toast", true));
        if (MainActivity.sharedpreferences.getBoolean("toast", true)) {
            switchToast.setText("Toasts are on");
        } else {
            switchToast.setText("Toasts are off");
        }
        switchToast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    switchToast.setText("Toasts are on");
                    editor.putBoolean("toast", true);
                } else {
                    switchToast.setText("Toasts are off");
                    editor.putBoolean("toast", false);
                }
                editor.commit();
            }
        });
    }
}
