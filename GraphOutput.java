package com.aracro.python.KarmanTrefftz;

import com.chaquo.python.*;
import java.lang.reflect.*;
import static com.chaquo.python.PyObject._chaquopyCall;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class GraphOutput extends android.support.v7.app.AppCompatActivity implements StaticProxy {
    static {
        Python.getInstance().getModule("KarmanTrefftz").get("GraphOutput");
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