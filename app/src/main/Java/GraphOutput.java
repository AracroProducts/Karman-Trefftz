package com.aracro.python.KarmanTrefftz;

import com.chaquo.python.*;

import java.io.File;
import java.lang.reflect.*;
import static com.chaquo.python.PyObject._chaquopyCall;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class GraphOutput extends android.support.v7.app.AppCompatActivity implements StaticProxy {
    static {
        Python.getInstance().getModule("KarmanTrefftz").get("GraphOutput");
    }

    public static final int REQUEST_WRITE_STORAGE = 112;

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
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

    public GraphOutput() {
        PyObject result;
        result = _chaquopyCall(this, "__init__");
        if (result != null) result.toJava(void.class);
    }

    @Override public void onCreate(android.os.Bundle arg0) {
        PyObject result;
        result = _chaquopyCall(this, "onCreate", arg0);
        if (result != null) result.toJava(void.class);
    }

    public GraphOutput(PyCtorMarker pcm) {}
    private PyObject _chaquopyDict;
    public PyObject _chaquopyGetDict() { return _chaquopyDict; }
    public void _chaquopySetDict(PyObject dict) { _chaquopyDict = dict; }
}