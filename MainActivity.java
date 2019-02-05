package com.aracro.python.KarmanTrefftz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SeekBar simpleSeekBar1;
    SeekBar simpleSeekBar2;
    SeekBar simpleSeekBar3;
    SeekBar simpleSeekBar4;
    Button button;
    public static SharedPreferences sharedpreferences;
    public boolean toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.aracro.mg.python.KarmanTrefftz.R.layout.activity_main);
        button = findViewById(com.aracro.mg.python.KarmanTrefftz.R.id.button);
        button.setText("Graph");
        sharedpreferences = getSharedPreferences("com.aracro.mg.python.KarmanTrefftz", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        toast = sharedpreferences.getBoolean("toast", true);
        // initiate
        simpleSeekBar1 = findViewById(com.aracro.mg.python.KarmanTrefftz.R.id.seekBarAoA);
        simpleSeekBar1.setProgress(sharedpreferences.getInt("AoA", 5));
        simpleSeekBar2 = findViewById(com.aracro.mg.python.KarmanTrefftz.R.id.seekBarArch);
        simpleSeekBar2.setProgress(sharedpreferences.getInt("Arch", 2));
        simpleSeekBar3 = findViewById(com.aracro.mg.python.KarmanTrefftz.R.id.seekBarTE);
        simpleSeekBar3.setProgress(sharedpreferences.getInt("TE", 15));
        simpleSeekBar4 = findViewById(com.aracro.mg.python.KarmanTrefftz.R.id.seekBarMuX);
        simpleSeekBar4.setProgress(sharedpreferences.getInt("Mux", 45));
        simpleSeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (toast) Toast.makeText(MainActivity.this, "AoA progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
                editor.putInt("AoA", progressChangedValue);
                editor.commit();
            }
        });
        simpleSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (toast) Toast.makeText(MainActivity.this, "Arch progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
                editor.putInt("Arch", progressChangedValue);
                editor.commit();
            }
        });
        simpleSeekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (toast) Toast.makeText(MainActivity.this, "TE progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
                editor.putInt("TE", progressChangedValue);
                editor.commit();
            }
        });
        simpleSeekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (toast) Toast.makeText(MainActivity.this, "Mux progress is :" + (double) (50 - progressChangedValue) / -100,
                        Toast.LENGTH_SHORT).show();
                editor.putInt("Mux", progressChangedValue);
                editor.commit();
            }
        });
    }

    public void doStuff(View view) {
        button.setText("Caluclating...");
        if (toast) Toast.makeText(MainActivity.this, "Calculating...",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, KarmanTrefftz.GraphOutput.class);
        startActivity(intent);
    }

    public void startSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void onResume() {
        super.onResume();
        button.setText("Graph");
        toast = sharedpreferences.getBoolean("toast", true);
    }
}
