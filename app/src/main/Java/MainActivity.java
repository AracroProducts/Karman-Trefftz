package com.aracro.python.KarmanTrefftz;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;

import static com.aracro.python.KarmanTrefftz.GraphOutput.REQUEST_WRITE_STORAGE;

public class MainActivity extends AppCompatActivity {

    SeekBar simpleSeekBar1;
    SeekBar simpleSeekBar2;
    SeekBar simpleSeekBar3;
    SeekBar simpleSeekBar4;
    Button button;
    public static SharedPreferences sharedpreferences;
    public boolean toast;
    public static final int REQUEST_WRITE_STORAGE = 112;

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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(this);
        }
    }

    public void requestPermissions(Activity context) {
        boolean hasPermission = (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        } else {
            // You are allowed to write external storage:
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/new_folder";
            File storageDir = new File(path);
            if (!storageDir.exists() && !storageDir.mkdirs()) {
                // This should never happen - log handled exception!
            }
        }
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

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
